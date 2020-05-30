package com.lch.service;

import com.lch.dao.IStaffDao;
import com.lch.domain.TaskDept;
import com.lch.domain.TaskInfo;
import com.lch.domain.TaskStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StaffServiceImpl implements IStaffService {



    private IStaffDao staffDao;

    public IStaffDao getStaffDao() {
        return staffDao;
    }

    @Autowired
    public void setStaffDao(IStaffDao staffDao) {
        this.staffDao = staffDao;
    }

    @Override
    public TaskStaff checkUser(String staff_workcode, String staff_password) {
        try {
            //service层处理异常
            TaskStaff task_staff = staffDao.checkUser(staff_workcode,staff_password);
            return task_staff;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TaskInfo> showTaskByDate(String year, String month, TaskStaff staff) {
        try {
            //service层处理异常
            return staffDao.showTaskByDate(year,month,staff.getStaff_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TaskDept> findAllDeptStaff() {
        try {
            //service层处理异常
            return staffDao.findAllDeptStaff();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
