/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: Exhibition
 * Author:   小三
 * Date:     2019/6/11 9:29
 * Description: 展会
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.model;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈展会〉
 *
 * @author 小三
 * @create 2019/6/11
 * @since 1.0.0
 */
public class Exhibition {

    //分类信息
    private Assort assort ;

    private int exhibitionId;
    //名称
    private String exhibitionName;

    // 标题
    private String exhibitionTitle;

    //分类
    private String assortid;
    private String exhibitionScope;
    //地址
    private String exhibitionArea;
    //图片
    private String exhibitionPhoto;

    private String exhibitionStatus;
    //开始时间
    private Date exhibitionStartTime;
    //结束时间
    private Date exhibitionEndTime;

    //联系人
    private String exhibitionContact;
    //组织机构
    private String exhibitionOrganization;
    // 邮箱
    private String exhibitionEmail;
    // 电话
    private String exhibitionPhone;
    //传真
    private String exhibitionFax;
    // 官网地址
    private String exhibitionIndexUrl;
    //展会介绍
    private String exhibitionIntroduce;

    public void setAssort(Assort assort) {
        this.assort = assort;
    }

    public Assort getAssort() {
        return assort;
    }

    public String getExhibitionTitle() {
        return exhibitionTitle;
    }

    public void setExhibitionTitle(String exhibitionTitle) {
        this.exhibitionTitle = exhibitionTitle;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    public String getExhibitionPhoto() {
        return exhibitionPhoto;
    }

    public String getExhibitionStatus() {
        return exhibitionStatus;
    }

    public Date getExhibitionStartTime() {
        return exhibitionStartTime;
    }

    public Date getExhibitionEndTime() {
        return exhibitionEndTime;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public void setExhibitionName(String exhibitionName) {
        this.exhibitionName = exhibitionName;
    }


    public void setExhibitionPhoto(String exhibitionPhoto) {
        this.exhibitionPhoto = exhibitionPhoto;
    }

    public void setExhibitionStatus(String exhibitionStatus) {
        this.exhibitionStatus = exhibitionStatus;
    }

    public void setExhibitionStartTime(Date exhibitionStartTime) {
        this.exhibitionStartTime = exhibitionStartTime;
    }

    public void setExhibitionEndTime(Date exhibitionEndTime) {
        this.exhibitionEndTime = exhibitionEndTime;
    }

    public String getAssortid() {
        return assortid;
    }

    public String getExhibitionArea() {
        return exhibitionArea;
    }

    public String getExhibitionContact() {
        return exhibitionContact;
    }

    public String getExhibitionOrganization() {
        return exhibitionOrganization;
    }

    public String getExhibitionEmail() {
        return exhibitionEmail;
    }

    public String getExhibitionPhone() {
        return exhibitionPhone;
    }

    public String getExhibitionFax() {
        return exhibitionFax;
    }

    public String getExhibitionIndexUrl() {
        return exhibitionIndexUrl;
    }

    public String getExhibitionIntroduce() {
        return exhibitionIntroduce;
    }

    public void setAssortid(String assortid) {
        this.assortid = assortid;
    }

    public void setExhibitionArea(String exhibitionArea) {
        this.exhibitionArea = exhibitionArea;
    }

    public void setExhibitionContact(String exhibitionContact) {
        this.exhibitionContact = exhibitionContact;
    }

    public void setExhibitionOrganization(String exhibitionOrganization) {
        this.exhibitionOrganization = exhibitionOrganization;
    }

    public void setExhibitionEmail(String exhibitionEmail) {
        this.exhibitionEmail = exhibitionEmail;
    }

    public void setExhibitionPhone(String exhibitionPhone) {
        this.exhibitionPhone = exhibitionPhone;
    }

    public void setExhibitionFax(String exhibitionFax) {
        this.exhibitionFax = exhibitionFax;
    }

    public void setExhibitionIndexUrl(String exhibitionIndexUrl) {
        this.exhibitionIndexUrl = exhibitionIndexUrl;
    }

    public void setExhibitionIntroduce(String exhibitionIntroduce) {
        this.exhibitionIntroduce = exhibitionIntroduce;
    }

    public String getExhibitionScope() {
        return exhibitionScope;
    }

    public void setExhibitionScope(String exhibitionScope) {
        this.exhibitionScope = exhibitionScope;
    }
}
