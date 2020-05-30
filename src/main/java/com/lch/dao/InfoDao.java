package com.lch.dao;

import com.lch.domain.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InfoDao {



    @Select("SELECT LEVEL_ID,LEVEL_NAME FROM TASK_LEVEL")
    List<TaskLevel> findAllLevel();

    //每个单词  或者字段后面都要加空格  不然sql可能会报错
    @Select("SELECT STATE_ID,STATE_NAME FROM TASK_STATE")
    List<TaskState> findAllState();

//    @Insert("INSERT INTO TASK_INFO(SENDSTAFF,RECEIVESTAFF," +
//            "STATE_ID,LEVEL_ID,INFO_NAME,INFO_DESC,INFO_CREATE_DATE," +
//            "INFO_NO_END_DATE,INFO_END_DATE) VALUES (#{sendStaff.staff_id}," +
//            "#{receiveStaff.staff_id},#{taskState.state_id}," +
//            "#{taskLevel.level_id},#{info_name},#{info_desc}," +
//            "#{info_create_date},#{info_no_end_date},#{info_end_date})")
//    int saveTask(TaskInfo taskInfo);

    @Insert("INSERT INTO TASK_INFO(SENDSTAFF,RECEIVESTAFF," +
            "LEVEL_ID,STATE_ID,INFO_NAME,INFO_DESC," +
            "INFO_CREATE_DATE,INFO_NO_END_DATE,INFO_END_DATE) VALUES (" +
            "#{sendStaff.staff_id},#{receiveStaff.staff_id}," +
            "#{taskLevel.level_id},#{taskState.state_id},#{info_name},#{info_desc}," +
            "#{info_create_date},#{info_no_end_date},#{info_end_date})")
    int saveTask(TaskInfo taskInfo);


    @Select("SELECT INFO_ID,SENDSTAFF,RECEIVESTAFF,STATE_ID,LEVEL_ID,INFO_NAME,INFO_DESC,INFO_CREATE_DATE,INFO_NO_END_DATE,INFO_END_DATE FROM TASK_INFO WHERE STR_TO_DATE(INFO_CREATE_DATE,'%Y-%m-%d') = STR_TO_DATE(NOW(),'%Y-%m-%d') AND RECEIVESTAFF = #{staff_id} LIMIT #{startRow},#{rows}")
    List<TaskInfo> showCurrentDayTask(@Param("startRow") Integer startRow, @Param("rows") Integer rows, @Param("staff_id") Integer staff_id);

    @Select("SELECT COUNT(*) FROM TASK_INFO WHERE STR_TO_DATE(INFO_CREATE_DATE,'%Y-%m-%d')=STR_TO_DATE(NOW(),'%Y-%m-%d') AND RECEIVESTAFF=#{staff_id}")
    int showCurrentDayTaskCount(Integer staff_id);



    @Results({
            @Result(column = "sendStaff",property = "sendStaff",one = @One(select = "com.lch.dao.InfoDao.findStaffById",fetchType = FetchType.LAZY),javaType = TaskStaff.class),
            @Result(column = "receiveStaff",property = "receiveStaff",one = @One(select = "com.lch.dao.InfoDao.findStaffById",fetchType = FetchType.LAZY),javaType = TaskStaff.class),
            @Result(column = "state_id",property = "taskState",one = @One(select = "com.lch.dao.InfoDao.findStateById",fetchType = FetchType.LAZY),javaType = TaskState.class),
            @Result(column = "level_id",property = "taskLevel",one = @One(select = "com.lch.dao.InfoDao.findLevelById",fetchType = FetchType.LAZY),javaType = TaskLevel.class)
    })
    @Select("SELECT INFO_ID,SENDSTAFF,RECEIVESTAFF,STATE_ID,LEVEL_ID,INFO_NAME,INFO_DESC,INFO_CREATE_DATE,INFO_NO_END_DATE,INFO_END_DATE FROM TASK_INFO WHERE STR_TO_DATE(INFO_CREATE_DATE,'%Y-%m') = STR_TO_DATE(NOW(),'%Y-%m') AND RECEIVESTAFF = #{staff_id} LIMIT #{startRow},#{rows}")
    List<TaskInfo> showCurrentMonthTask(@Param("startRow") Integer startRow, @Param("rows") Integer rows, @Param("staff_id") Integer staff_id);

    @Select("SELECT COUNT(*) FROM TASK_INFO WHERE STR_TO_DATE(INFO_CREATE_DATE,'%Y-%m')=STR_TO_DATE(NOW(),'%Y-%m') AND RECEIVESTAFF=#{staff_id}")
    int showCurrentMonthTaskCount(Integer staff_id);

    //通过id查询员工
    @Results({
            @Result(column = "dept_id",property = "dept",one = @One(select = "com.lch.dao.IStaffDao.findDeptById",fetchType = FetchType.LAZY),javaType = TaskDept.class)
    })
    @Select("SELECT STAFF_ID,DEPT_ID,STAFF_NAME,STAFF_WORKCODE,STAFF_PASSWORD FROM TASK_STAFF WHERE STAFF_ID = #{staff_id}")
    TaskStaff findStaffById(Integer staff_id);


    @Select("SELECT STATE_ID,STATE_NAME FROM TASK_STATE WHERE STATE_ID = #{state_id}")
    TaskState findStateById(Integer state_id);

    @Select("SELECT LEVEL_ID,LEVEL_NAME FROM TASK_LEVEL WHERE LEVEL_ID = #{level_id}")
    TaskLevel findLevelById(Integer level_id);

    @Results({
            @Result(column = "sendStaff",property = "sendStaff",one = @One(select = "com.lch.dao.InfoDao.findStaffById",fetchType = FetchType.LAZY),javaType = TaskStaff.class),
            @Result(column = "receiveStaff",property = "receiveStaff",one = @One(select = "com.lch.dao.InfoDao.findStaffById",fetchType = FetchType.LAZY),javaType = TaskStaff.class),
            @Result(column = "state_id",property = "taskState",one = @One(select = "com.lch.dao.InfoDao.findStateById",fetchType = FetchType.LAZY),javaType = TaskState.class),
            @Result(column = "level_id",property = "taskLevel",one = @One(select = "com.lch.dao.InfoDao.findLevelById",fetchType = FetchType.LAZY),javaType = TaskLevel.class)
    })
    @SelectProvider(type = com.lch.dao.InfoSqlProvider.class,method = "showSingleTask")
    List<TaskInfo> showSingleTask(Map<String, Object> param);

    @Results({
            @Result(column = "sendStaff",property = "sendStaff",one = @One(select = "com.lch.dao.InfoDao.findStaffById",fetchType = FetchType.LAZY),javaType = TaskStaff.class),
            @Result(column = "receiveStaff",property = "receiveStaff",one = @One(select = "com.lch.dao.InfoDao.findStaffById",fetchType = FetchType.LAZY),javaType = TaskStaff.class),
            @Result(column = "state_id",property = "taskState",one = @One(select = "com.lch.dao.InfoDao.findStateById",fetchType = FetchType.LAZY),javaType = TaskState.class),
            @Result(column = "level_id",property = "taskLevel",one = @One(select = "com.lch.dao.InfoDao.findLevelById",fetchType = FetchType.LAZY),javaType = TaskLevel.class)
    })
    @SelectProvider(type = com.lch.dao.InfoSqlProvider.class,method = "showTaskForChart")
    List<TaskInfo> showTaskForChart(Map<String, Object> param);
}
