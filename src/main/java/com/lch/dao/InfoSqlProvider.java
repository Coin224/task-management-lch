package com.lch.dao;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * 动态sql
 */
public class InfoSqlProvider {
    public String showSingleTask(final Map<String, Object> param) {
        SQL sql = new SQL();
        sql.SELECT("info_id","info_name","sendStaff","receiveStaff","level_id","state_id","info_create_date");
        sql.FROM("task_info");
        if(param.get("startDate")!=null && "".equals(param.get("startDate"))){
            sql.WHERE("info_create_date>=#{startdate}");
        }else if(param.get("endDate")!=null && "".equals(param.get("endDate"))){
            sql.WHERE("info_create_date<=#{endDate}");
        }
        return sql.toString();
    }


    public String showTaskForChart(final Map<String, Object> param) {
        SQL sql = new SQL();
        sql.SELECT("info_id","info_name","sendStaff","receiveStaff","level_id","state_id","info_create_date");
        sql.FROM("task_info");
        if(param.get("startDate")!=null && "".equals(param.get("startDate"))){
            sql.WHERE("info_create_date>=#{startdate}");
        }else if(param.get("endDate")!=null && "".equals(param.get("endDate"))){
            sql.WHERE("info_create_date<=#{endDate}");
        }else if(param.get("staff_id")!=null && "".equals(param.get("staff_id"))) {
            sql.WHERE("receiveStaff=#{staff_id}");
        }
        return sql.toString();
    }
}
