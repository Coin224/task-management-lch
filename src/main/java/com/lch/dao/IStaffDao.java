package com.lch.dao;

import com.lch.domain.TaskDept;
import com.lch.domain.TaskInfo;
import com.lch.domain.TaskStaff;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStaffDao {




    //结果集
    @Results({
            @Result(column = "staff_id",property = "staff_id"),
            @Result(column = "staff_name",property = "staff_name"),
            @Result(column = "staff_workcode",property = "staff_workcode"),
            @Result(column = "staff_password",property = "staff_password"),
            //需要通过dept_id查找部门信息
            //fetchType = FetchType.EAGER 急加载,FetchType.LAZY 懒加载 DEFAULT
            @Result(column = "dept_id",property = "dept",one = @One(select = "com.lch.IStaffDao.findDeptById",fetchType = FetchType.LAZY))
    })
    //TODO 部门还没配  查不了
    @Select("SELECT STAFF_ID,STAFF_NAME,STAFF_WORKCODE,STAFF_PASSWORD FROM TASK_STAFF WHERE STAFF_WORKCODE = #{staff_workcode} AND STAFF_PASSWORD = #{staff_password}")
    TaskStaff checkUser(@Param("staff_workcode") String staff_workcode, @Param("staff_password") String staff_password);

    @Select("SELECT DEPT_ID,DEPT_NAME,DEPT_CODE FROM TASK_DEPT WHERE DEPT_ID = #{dept_id}")
    TaskDept findDeptById(int dept_id);

    //SELECT INFO_ID,SENDSTAFF,RECEIVESTAFF,STATE_ID,LEVEL_ID,INFO_NAME,INFO_DESC,INFO_CREATE_DATE,INFO_NO_END_DATE,INFO_END_DATE FROM TASK_INFO WHERE YEAR(STR_TO_DATE(INFO_CREATE_DATE,'%Y-%m-%d'))= YEAR(NOW()) AND MONTH(STR_TO_DATE(INFO_CREATE_DATE,'%Y-%m-%d'))=MONTH(NOW()) AND RECEIVESTAFF = #{}
    @Select("SELECT INFO_ID,SENDSTAFF,RECEIVESTAFF,STATE_ID,LEVEL_ID,INFO_NAME,INFO_DESC,INFO_CREATE_DATE,INFO_NO_END_DATE,INFO_END_DATE FROM TASK_INFO WHERE YEAR(INFO_CREATE_DATE) = #{year} AND MONTH(INFO_CREATE_DATE) = #{month} AND RECEIVESTAFF = #{staff_id}")
    List<TaskInfo> showTaskByDate(@Param("year") String year, @Param("month") String month,@Param("staff_id") Integer staff_id);

    @Results({
            @Result(column = "dept_id",property = "dept_id"),
            @Result(column = "dept_name",property = "dept_name"),
            @Result(column = "dept_code",property = "dept_code"),
            @Result(column = "dept_id",property = "staffList",many = @Many(select = "com.lch.dao.IStaffDao.findAllStaffByDeptId",fetchType = FetchType.LAZY),javaType = java.util.List.class),
    })
    @Select("SELECT DEPT_ID,DEPT_NAME,DEPT_CODE FROM TASK_DEPT")
    List<TaskDept> findAllDeptStaff();

    @Select("SELECT STAFF_ID,DEPT_ID,STAFF_NAME,STAFF_WORKCODE,STAFF_PASSWORD FROM TASK_STAFF WHERE DEPT_ID = #{dept_id}")
    List<TaskStaff> findAllStaffByDeptId(int dept_id);
}
