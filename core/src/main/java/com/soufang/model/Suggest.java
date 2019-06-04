package com.soufang.model;

import java.util.Date;

public class Suggest {
    private Long sugId;
    private Long userId;
    private String sugContent;
    private Date createTime;
    private String renContent;
    private Date returnTime;

    public Long getSugId() {
        return sugId;
    }

    public void setSugId(Long sugId) {
        this.sugId = sugId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSugContent() {
        return sugContent;
    }

    public void setSugContent(String sugContent) {
        this.sugContent = sugContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRenContent() {
        return renContent;
    }

    public void setRenContent(String renContent) {
        this.renContent = renContent;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Suggest() {
    }

    public Suggest(Long sugId, Long userId, String sugContent, Date createTime, String renContent, Date returnTime) {
        this.sugId = sugId;
        this.userId = userId;
        this.sugContent = sugContent;
        this.createTime = createTime;
        this.renContent = renContent;
        this.returnTime = returnTime;
    }
}
