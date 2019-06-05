package com.soufang.model;

import java.util.Date;
import java.util.List;

public class Enquiry {
    //询价单号
    private String enquiryNumber;

    //询价用户
    private Long userId;

    private Shop shop;
    private String loginName;

    //询价标题
    private String enquiryTitle;

    //采购类型  0 单次采购  1 长期合作
    private Integer enquiryType;

    //询盘状态
    private Integer enquiryStatus;

    //期待收货日期
    private Date takeDate;

    //收货地址
    private String takeAddress;

    //收货人
    private String takeName;

    //收货人联系方式
    private String takePhone;

    //报价截止日期
    private Date endTime;

    //创建时间
    private Date createTime;

    private String strCreateTime;

    //备注
    private String enquiryRemark;

    private String successMessage;
    private String statusMessage;

    private List<EnquiryProduct> enquiryProducts;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStrCreateTime() {
        return strCreateTime;
    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }


    public List<EnquiryProduct> getEnquiryProducts() {
        return enquiryProducts;
    }

    public void setEnquiryProducts(List<EnquiryProduct> enquiryProducts) {
        this.enquiryProducts = enquiryProducts;
    }

    public String getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(String enquiryNumber) {
        this.enquiryNumber = enquiryNumber == null ? null : enquiryNumber.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEnquiryTitle() {
        return enquiryTitle;
    }

    public void setEnquiryTitle(String enquiryTitle) {
        this.enquiryTitle = enquiryTitle == null ? null : enquiryTitle.trim();
    }

    public Integer getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(Integer enquiryType) {
        this.enquiryType = enquiryType;
    }

    public Integer getEnquiryStatus() {
        return enquiryStatus;
    }

    public void setEnquiryStatus(Integer enquiryStatus) {
        this.enquiryStatus = enquiryStatus;
    }

    public Date getTakeDate() {
        return takeDate;
    }

    public void setTakeDate(Date takeDate) {
        this.takeDate = takeDate;
    }

    public String getTakeAddress() {
        return takeAddress;
    }

    public void setTakeAddress(String takeAddress) {
        this.takeAddress = takeAddress == null ? null : takeAddress.trim();
    }

    public String getTakeName() {
        return takeName;
    }

    public void setTakeName(String takeName) {
        this.takeName = takeName == null ? null : takeName.trim();
    }

    public String getTakePhone() {
        return takePhone;
    }

    public void setTakePhone(String takePhone) {
        this.takePhone = takePhone == null ? null : takePhone.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEnquiryRemark() {
        return enquiryRemark;
    }

    public void setEnquiryRemark(String enquiryRemark) {
        this.enquiryRemark = enquiryRemark == null ? null : enquiryRemark.trim();
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}