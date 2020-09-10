package com.hxzy.entity.hr;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 
 */
public class Log implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 登录账户
     */
    private String username;

    /**
     * 操作数据
     */
    private String data;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作事件
     */
    private String affair;

    /**
     * 操作时间
     */
    private Date craetetime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAffair() {
        return affair;
    }

    public void setAffair(String affair) {
        this.affair = affair;
    }

    public Date getCraetetime() {
        return craetetime;
    }

    public void setCraetetime(Date craetetime) {
        this.craetetime = craetetime;
    }
}