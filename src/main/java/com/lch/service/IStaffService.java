package com.lch.service;

import com.lch.domain.TaskDept;
import com.lch.domain.TaskInfo;
import com.lch.domain.TaskStaff;

import java.util.List;

public interface IStaffService {


    /**
     *
     * @param staff_workcode staff的工号
     * @param staff_password staff的密码
     * @return TaskStaff   返回一个TaskStaff对象
     */
    TaskStaff checkUser(String staff_workcode, String staff_password);

    /**
     * 根据年 ，月，人员登录信息 查询当年当月任务
     * @param year
     * @param month
     * @param staff
     * @return 返回该用户当月所有的信息
     */
    List<TaskInfo> showTaskByDate(String year, String month, TaskStaff staff);

    /**
     * 查询所有部门及部门下所有人员
     * @return
     */
    List<TaskDept> findAllDeptStaff();
}
