package com.soufang.model;

import java.util.Date;

public class Message {
    private Long mesId;
    private String phone;
    private String content;
    private Integer mesStatus;
    private Date createTime;
    private Integer mesType;

    public Long getMesId() {
        return mesId;
    }

    public void setMesId(Long mesId) {
        this.mesId = mesId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMesStatus() {
        return mesStatus;
    }

    public void setMesStatus(Integer mesStatus) {
        this.mesStatus = mesStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMesType() {
        return mesType;
    }

    public void setMesType(Integer mesType) {
        this.mesType = mesType;
    }

    public Message() {
    }

    public Message(Long mesId, String phone, String content, int mesStatus, Date createTime, int mesType) {
        this.mesId = mesId;
        this.phone = phone;
        this.content = content;
        this.mesStatus = mesStatus;
        this.createTime = createTime;
        this.mesType = mesType;
    }
}
