package com.lch.service;

import com.lch.dao.InfoDao;
import com.lch.domain.TaskInfo;
import com.lch.domain.TaskLevel;
import com.lch.domain.TaskState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {


    private InfoDao infoDao;

    public InfoDao getInfoDao() {
        return infoDao;
    }

    @Autowired
    public void setInfoDao(InfoDao infoDao) {
        this.infoDao = infoDao;
    }


    @Override
    public List<TaskLevel> findAllLevel() {
        return infoDao.findAllLevel();
    }


    @Override
    public List<TaskState> findAllState() {
        try {
            return infoDao.findAllState();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public int saveTask(TaskInfo taskInfo) {
        try{
            //如果有任务接收人 发送邮件提醒
            return infoDao.saveTask(taskInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<TaskInfo> showCurrentDayTask(Integer page, Integer rows, Integer staff_id) {
        try{
            //由于数据库中limit不允许计算
            //在服务层计算分页起始值
            Integer startRow = (page - 1) * rows;
            return infoDao.showCurrentDayTask(startRow,rows,staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int showCurrentDayTaskCount(Integer staff_id) {
        try{
            return infoDao.showCurrentDayTaskCount(staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<TaskInfo> showCurrentMonthTask(Integer page, Integer rows, Integer staff_id) {
        try{
            //由于数据库中limit不允许计算
            //在服务层计算分页起始值
            Integer startRow = (page - 1) * rows;
            return infoDao.showCurrentMonthTask(startRow,rows,staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int showCurrentMonthTaskCount(Integer staff_id) {
        try{
            return infoDao.showCurrentMonthTaskCount(staff_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<TaskInfo> showSingleTask(Map<String, Object> param) {
        try{
            //由于数据库中limit不允许计算
            //在服务层计算分页起始值
            return infoDao.showSingleTask(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TaskInfo> showTaskForChart(Map<String, Object> param) {
        try{
            //由于数据库中limit不允许计算
            //在服务层计算分页起始值
            return infoDao.showTaskForChart(param);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
