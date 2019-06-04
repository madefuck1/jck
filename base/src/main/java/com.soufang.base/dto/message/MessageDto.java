package com.soufang.base.dto.message;

import com.soufang.base.utils.DateUtils;

import java.util.Date;

public class MessageDto {
    private Long mesId;
    private String phone;
    private String content;
    private Integer mesStatus;
    private String messageStatus;
    private Date createTime;
    private String strCreateTime;
    private Integer mesType;

    public String getStrCreateTime() {
        return DateUtils.date2String(createTime,DateUtils.format1);
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMesStatus() {
        return mesStatus;
    }

    public void setMesStatus(Integer mesStatus) {
        this.mesStatus = mesStatus;
    }

    public String getMessageStatus() {
        if(mesStatus == null){
            messageStatus = null;
        }else if(mesStatus == 0) {
            messageStatus = "发送成功";
        }else {
            messageStatus = "发送失败";
        }
        return messageStatus;
    }

    public Integer getMesType() {
        return mesType;
    }

    public void setMesType(Integer mesType) {
        this.mesType = mesType;
    }

    public MessageDto() {
    }

    public MessageDto(String phone, String content, Integer mesStatus, Date createTime,  Integer mesType) {
        this.phone = phone;
        this.content = content;
        this.mesStatus = mesStatus;
        this.createTime = createTime;
        this.mesType = mesType;
    }
}
