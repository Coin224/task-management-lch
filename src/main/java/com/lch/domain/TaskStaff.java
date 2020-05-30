package com.lch.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;



@JsonIgnoreProperties(value="handler")
public class TaskStaff implements Serializable {


    private Integer staff_id;
    private String staff_name;
    private String staff_workcode;
    private String staff_password;

    //Staff ----- Dept的关系  一对一
    private TaskDept dept;

    public Integer getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Integer staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_workcode() {
        return staff_workcode;
    }

    public void setStaff_workcode(String staff_workcode) {
        this.staff_workcode = staff_workcode;
    }

    public String getStaff_password() {
        return staff_password;
    }

    public void setStaff_password(String staff_password) {
        this.staff_password = staff_password;
    }

    public TaskDept getDept() {
        return dept;
    }

    public void setDept(TaskDept dept) {
        this.dept = dept;
    }
}
