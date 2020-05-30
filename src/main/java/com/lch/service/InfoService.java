package com.lch.service;

import com.lch.domain.TaskInfo;
import com.lch.domain.TaskLevel;
import com.lch.domain.TaskState;

import java.util.List;
import java.util.Map;

public interface InfoService {

    /**
     * 查询任务所有的等级
     * @return 返回任务所有的等级
     */
    List<TaskLevel> findAllLevel();

    /**
     * 查询所有的任务状态
     * @return 返回所有的任务状态
     */
    List<TaskState> findAllState();

    /**
     *
     * @param taskInfo 根据控制层请求传递的信息  组成一个task对象
     * @return 存储成功返回1   失败返回0
     */
    int saveTask(TaskInfo taskInfo);

    /**
     * 根据用户分页查询当天任务
     * @param page
     * @param rows
     * @param staff_id
     * @return
     */
    List<TaskInfo> showCurrentDayTask(Integer page, Integer rows, Integer staff_id);

    /**
     * 查询该用户当天任务总数
     * @param staff_id
     * @return total 总数
     */
    int showCurrentDayTaskCount(Integer staff_id);


    /**
     * 分页查询该用户当月的任务列表
     * @param page
     * @param rows
     * @param staff_id
     * @return
     */
    List<TaskInfo> showCurrentMonthTask(Integer page, Integer rows, Integer staff_id);

    /**
     * 查询该用户当月任务总数
     * @param staff_id
     * @return
     */
    int showCurrentMonthTaskCount(Integer staff_id);

    /**
     * 根据page rows startDate endDate user 等查询任务信息
     * @param param
     * @return
     */
    List<TaskInfo> showSingleTask(Map<String, Object> param);

    /**
     * 根据参数查找任务信息
     * @return
     */
    List<TaskInfo> showTaskForChart(Map<String, Object> param);
}
