package com.lch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(value = "handler")
public class TaskInfo  implements Serializable {

    //taskinfo自身的属性
    private Integer info_id;
    private String info_name;
    private String info_desc;
    private String info_create_date;
    private String info_no_end_date;
    private String info_end_date;

    //关联属性
    private TaskStaff sendStaff;
    private TaskStaff receiveStaff;
    private TaskState taskState;
    private TaskLevel taskLevel;

    public Integer getInfo_id() {
        return info_id;
    }

    public void setInfo_id(Integer info_id) {
        this.info_id = info_id;
    }

    public String getInfo_name() {
        return info_name;
    }

    public void setInfo_name(String info_name) {
        this.info_name = info_name;
    }

    public String getInfo_desc() {
        return info_desc;
    }

    public void setInfo_desc(String info_desc) {
        this.info_desc = info_desc;
    }

    public String getInfo_create_date() {
        return info_create_date;
    }

    public void setInfo_create_date(String info_create_date) {
        this.info_create_date = info_create_date;
    }

    public String getInfo_no_end_date() {
        return info_no_end_date;
    }

    public void setInfo_no_end_date(String info_no_end_date) {
        this.info_no_end_date = info_no_end_date;
    }

    public String getInfo_end_date() {
        return info_end_date;
    }

    public void setInfo_end_date(String info_end_date) {
        this.info_end_date = info_end_date;
    }

    public TaskStaff getSendStaff() {
        return sendStaff;
    }

    public void setSendStaff(TaskStaff sendStaff) {
        this.sendStaff = sendStaff;
    }

    public TaskStaff getReceiveStaff() {
        return receiveStaff;
    }

    public void setReceiveStaff(TaskStaff receiveStaff) {
        this.receiveStaff = receiveStaff;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public TaskLevel getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(TaskLevel taskLevel) {
        this.taskLevel = taskLevel;
    }
}
