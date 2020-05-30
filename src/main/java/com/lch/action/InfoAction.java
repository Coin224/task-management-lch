package com.lch.action;

import com.lch.domain.TaskInfo;
import com.lch.domain.TaskLevel;
import com.lch.domain.TaskStaff;
import com.lch.domain.TaskState;
import com.lch.service.InfoService;
import com.lch.util.ExcelTool;
import com.lch.util.Mail;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/info")
public class InfoAction {


    private InfoService infoService;


    public InfoService getInfoService() {
        return infoService;
    }
    @Autowired
    public void setInfoService(InfoService infoService) {
        this.infoService = infoService;
    }



    //搞我心态  以后这种请求的参数直接复制  不然总是出错
    @RequestMapping("/findAllLevel")
    @ResponseBody
    public List<TaskLevel> findAllLevel() throws IOException {
        return infoService.findAllLevel();
    }

    //查找所有的状态
    @RequestMapping("/findAllState")
    @ResponseBody
    public List<TaskState> findAllState() throws IOException {
        return infoService.findAllState();
    }

    //保存任务
    @RequestMapping("/saveTask")
    @ResponseBody
    public int saveTask(HttpServletRequest request) throws IOException{

        //Map<String,Integer> result = new HashMap<>();
        TaskInfo taskInfo = new TaskInfo();
        String info_create_date = request.getParameter("info_create_date");
        String info_name = request.getParameter("info_name");
        String info_desc = request.getParameter("info_desc");
        String info_level = request.getParameter("info_level");
        String info_state = request.getParameter("info_state");

        //创建一个任务对象
        taskInfo.setInfo_create_date(info_create_date);
        taskInfo.setInfo_name(info_name);
        taskInfo.setInfo_desc(info_desc);

        //要给任务等级赋值  但是里面是一个任务等级对象
        // 所以先创建一个等级对象 给它的id赋值  再存到taskInfo中
        TaskLevel taskLevel = new TaskLevel();
        if(info_level != null) {
            taskLevel.setLevel_id(Integer.parseInt(info_level));
        }
        taskInfo.setTaskLevel(taskLevel);
        //同理 任务状态
        TaskState taskState = new TaskState();
        if(info_state != null) {
            taskState.setState_id(Integer.parseInt(info_state));
        }
        taskInfo.setTaskState(taskState);

        //那么任务发送人怎么来呢   存在session里  需要一个session对象
        TaskStaff sendStaff = (TaskStaff) request.getSession().getAttribute("user");
        taskInfo.setSendStaff(sendStaff);

        //任务接收人
        String receiveStaff = request.getParameter("receiveStaff");
        if(receiveStaff == null){
            //自建任务
            taskInfo.setReceiveStaff(sendStaff);
        }else{
            //我派发给别人的任务
            TaskStaff rStaff = new TaskStaff();
            rStaff.setStaff_id(Integer.parseInt(receiveStaff));
            taskInfo.setReceiveStaff(rStaff);
            //发送邮件
            Mail mail = new Mail();
            String content = "宁有新的任务,详情请访问：<a href='http://localhost:8080/task/login.html'>任务跟踪管理系统</a>";
            mail.sendHTMLMail("新任务提示！",content,"coinvv@163.com");
        }


        //所有参数都请求完毕  交给service处理
        //根据结果返回响应信息
        return infoService.saveTask(taskInfo);
    }


    @RequestMapping("/showCurrentDayTask")
    @ResponseBody
    public Map<String, Object> showCurrentDayTask(HttpSession session, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        //使用easyUI的datagrid分页需要接收两个参数
        // rows
        // total当天任务总数
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer rows = Integer.parseInt(request.getParameter("rows"));
        TaskStaff staff = (TaskStaff) session.getAttribute("user");
        List<TaskInfo> infoList = infoService.showCurrentDayTask(page,rows,staff.getStaff_id());
        int total = infoService.showCurrentDayTaskCount(staff.getStaff_id());
        result.put("rows",infoList);
        result.put("total",total);
        return result;
    }

    @RequestMapping("/showCurrentMonthTask")
    @ResponseBody
    public Map<String, Object> showCurrentMonthTask(HttpSession session, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        //使用easyUI的datagrid分页需要接收两个参数
        // rows
        // total当天任务总数
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer rows = Integer.parseInt(request.getParameter("rows"));
        TaskStaff staff = (TaskStaff) session.getAttribute("user");
        List<TaskInfo> infoList = infoService.showCurrentMonthTask(page, rows, staff.getStaff_id());
        int total = infoService.showCurrentMonthTaskCount(staff.getStaff_id());
        result.put("rows", infoList);
        result.put("total", total);
        return result;
    }






    @RequestMapping("/showSingleTask")
    @ResponseBody
    public Map<String, Object> showSingleTask(HttpSession session, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        //使用easyUI的datagrid分页需要接收两个参数
        // rows
        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer rows = Integer.parseInt(request.getParameter("rows"));
        //开始日期
        String startDate = request.getParameter("startdate");
        //结束日期
        String endDate = request.getParameter("enddate");
        //获取当前的user
        TaskStaff staff = (TaskStaff) session.getAttribute("user");
        //把所有的参数封装到一个map中
        Map<String, Object> param = new HashMap<>();
        param.put("staff",staff);
        param.put("startDate",startDate);
        param.put("endDate",endDate);
        param.put("page",page);
        param.put("rows",rows);
        List<TaskInfo> infoList = infoService.showSingleTask(param);
        //todo 分页
        for(TaskInfo info:infoList){
            System.out.println(info.getInfo_id()+"=="+info.getInfo_name()+"=="+info.getSendStaff().getStaff_name()+"=="+info.getReceiveStaff().getStaff_name()+"=="+info.getTaskLevel().getLevel_name()+"=="+info.getTaskState().getState_name());
        }

        // total当天任务总数
        int total = infoService.showCurrentMonthTaskCount(staff.getStaff_id());
        result.put("rows", infoList);
        result.put("total", total);
        return result;
    }

    //导出任务到excel
    //不需要返回值
    @RequestMapping("/exportDataToExcel")
    public void exportDataToExcel(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //开始日期
        String startDate = request.getParameter("startdate");
        //结束日期
        String endDate = request.getParameter("enddate");
        //获取当前的user
        TaskStaff staff = (TaskStaff) session.getAttribute("user");
        //把所有的参数封装到一个map中
        Map<String, Object> param = new HashMap<>();
//        param.put("staff",staff);
//        param.put("startDate",startDate);
//        param.put("endDate",endDate);

        List<TaskInfo> infoList = infoService.showSingleTask(param);
        for(TaskInfo info:infoList){
            System.out.println(info.getInfo_id()+"=="+info.getInfo_name()+"=="+info.getSendStaff().getStaff_name()+"=="+info.getReceiveStaff().getStaff_name()+"=="+info.getTaskLevel().getLevel_name()+"=="+info.getTaskState().getState_name());
        }

        //excel workbook  excel操作: poi/jxl   创建一个工具类
        String sheetName = "个人任务";
        String[] title = {"编号" ,"任务名称","派发人","接收人","任务等级","任务状态"};
        String[][] data = new String[infoList.size()][];

        for (int i =0; i <data.length; i++) {
            TaskInfo info = infoList.get(i);
            data[i] = new String[title.length];
            data[i][0] = info.getInfo_id()+"";
            data[i][1] = info.getInfo_name();
            data[i][2] = info.getSendStaff().getStaff_name();
            data[i][3] = info.getReceiveStaff().getStaff_name();
            data[i][4] = info.getTaskLevel().getLevel_name();
            data[i][5] = info.getTaskState().getState_name();
        }
        HSSFWorkbook workbook = ExcelTool.createHSSFWorkbook(sheetName,title,data);

        String fileName = "个人任务报表.xls";
        setResponseHeader(response,fileName);
        //响应
        //输出流
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }


    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setContentType("aplication/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition","attachment;filename="+fileName);
            response.addHeader("Pargam","no-cache");
            response.addHeader("Cache-Control","no-cache");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/showTaskForChart")
    @ResponseBody
    public List<TaskInfo> showTaskForChart(HttpSession session, HttpServletRequest request) {
        //开始日期
        String startDate = request.getParameter("startdate");
        //结束日期
        String endDate = request.getParameter("enddate");
        String staff = request.getParameter("staff_id");
        Map<String, Object> param = new HashMap<>();
//        param.put("startDate",startDate);
//        param.put("endDate",endDate);
        param.put("staff",staff);
        List<TaskInfo> list = infoService.showTaskForChart(param);
        return list;
    }



}
