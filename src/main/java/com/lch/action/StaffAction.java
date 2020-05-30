package com.lch.action;


import com.lch.domain.TaskDept;
import com.lch.domain.TaskInfo;
import com.lch.domain.TaskStaff;
import com.lch.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/staff")
public class StaffAction {



    public IStaffService staffService;


    public IStaffService getStaffService() {
        return staffService;
    }

    @Autowired
    public void setStaffService(IStaffService staffService) {
        this.staffService = staffService;
    }

    @RequestMapping("/login")
    @ResponseBody
    public TaskStaff login(HttpSession session, HttpServletRequest request) throws IOException {
        //1.获取请求传递的参数
        String staff_workcode = request.getParameter("staff_workcode");
        String staff_password = request.getParameter("staff_password");
        //2.交给service处理
        TaskStaff staff = getStaffService().checkUser(staff_workcode,staff_password);
        if(staff != null){
            //保存会话
            session.setAttribute("user",staff);
        }
        return staff;
    }

    @RequestMapping("/showTaskByDate")
    @ResponseBody
    public List<TaskInfo> showTaskByDate(HttpServletRequest request,HttpSession session) {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        //获取到当前登录的人员信息
        TaskStaff staff = (TaskStaff)session.getAttribute("user");
        if(staff != null) {
            //登录状态  就查
            List<TaskInfo> result = staffService.showTaskByDate(year,month,staff);
            return  result;
        } else {
            //没登录  就不查
            return null;
        }
    }


    @RequestMapping("/findAllDeptStaff")
    @ResponseBody
    public List<Map<String,Object>> findAllDeptStaff(HttpSession session, HttpServletRequest request)throws IOException {
        List<Map<String,Object>> result = new ArrayList<>();
        //查询所有部门以及部门下的人员
        List<TaskDept> deptList = getStaffService().findAllDeptStaff();
        //重新转换一下id,text文本值
        if (deptList != null) {
            for (TaskDept dept : deptList){//dept_id dept_name  id text
                Map<String,Object> map = new HashMap<>();
                map.put("id",dept.getDept_id());
                map.put("text",dept.getDept_name());
                map.put("state","open");
                //map.put("checkbox","false");
                List<Map<String,Object>> staffList = new ArrayList<>();
                map.put("children",staffList);
                for(TaskStaff staff : dept.getStaffList()){
                    Map<String,Object> childMap = new HashMap<>();
                    childMap.put("id",staff.getStaff_id());
                    childMap.put("text",staff.getStaff_name());
                    staffList.add(childMap);//部门里的人员
                }
                result.add(map);
            }
        }
        return result;
    }

}
