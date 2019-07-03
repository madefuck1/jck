package com.soufang.base.dto.news;

import com.soufang.base.utils.DateUtils;

import java.util.Date;

public class NewsDto {

    private Long id;
    private String author;
    private String content;
    private String title;
    private String origin;
    private String picture;
    private Date createTime;
    private String strCreateTime;



    public NewsDto() {
    }

    public String getStrCreateTime() {
        if(createTime == null){
            return null;
        }else {
            return DateUtils.date2String(createTime,DateUtils.format1);
        }

    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }



}
