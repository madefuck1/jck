package com.soufang.Vo.enquiry;

import com.soufang.Vo.enquiryProduct.EnqProReqVo;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class EnquiryReqVo {
    private List<EnquiryProductDto> enquiryProductDto;

    private String enquiryNumber;

    private Long userId;

    private String enquiryTitle;

    private Integer enquiryType;
    private String strEnquiryType;

    private Integer enquiryStatus;

    //询盘状态-字符解释
    private String successMessage;

    private String strEnquiryStatus;

    private Date takeDate;
    private String strTakeDate;

    private String takeAddress;

    private String takeName;

    private String takePhone;

    private Date endTime;
    private String strEndTime;

    private Date createTime;
    private String strCreateTime;

    private String enquiryRemark;

    private String productUnit;

    private String product_detail;

    private String productSendCountry;

    private String productSendProvince;

    private String productSendCity;
    private List<EnqProReqVo> table;

    public String getStrEnquiryStatus() {
        if(enquiryStatus == null){
            return null;
        }else if(enquiryStatus == 0){
            return "已发布";
        }else if(enquiryStatus == 1){
            return "未发布";
        }else {
            return "禁止报价";
        }
    }

    public String getStrEnquiryType() {
    return strEnquiryType;
    }

    public String getStrTakeDate() {
        if(takeDate == null){
            return "采购人未填写";
        }else {
            return DateUtils.date2String(takeDate,DateUtils.format1);
        }
    }

    public String getStrEndTime() {
        if(endTime == null){
            return "采购人未填写";
        }else {
            return DateUtils.date2String(endTime,DateUtils.format1);
        }
    }

    public String getStrCreateTime() {
        if(createTime == null){
            return null;
        }else {
            return DateUtils.date2String(createTime,DateUtils.format1);
        }
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
        if(takeAddress == null){
            return "采购人未填写";
        }else {
            return takeAddress;
        }
    }

    public void setTakeAddress(String takeAddress) {
        this.takeAddress = takeAddress == null ? null : takeAddress.trim();
    }

    public String getTakeName() {
        if(takeName == null){
            return "采购人未填写";
        }else {
            return takeName;
        }
    }

    public void setTakeName(String takeName) {
        this.takeName = takeName == null ? null : takeName.trim();
    }

    public String getTakePhone() {
        if(takePhone == null){
            return "采购人未填写";
        }else {
            return takePhone;
        }
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
        if(enquiryRemark == null){
            return "没有备注信息";
        }else {
            return enquiryRemark;
        }
    }

    public void setEnquiryRemark(String enquiryRemark) {
        this.enquiryRemark = enquiryRemark == null ? null : enquiryRemark.trim();
    }

    public void setEnquiryProductDto(List<EnquiryProductDto> enquiryProductDto) {
        this.enquiryProductDto = enquiryProductDto;
    }

    public List<EnquiryProductDto> getEnquiryProductDto() {
        return enquiryProductDto;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public void setStrEnquiryType(String strEnquiryType) {
        this.strEnquiryType = strEnquiryType;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductSendCountry() {
        return productSendCountry;
    }

    public String getProductSendProvince() {
        return productSendProvince;
    }

    public String getProductSendCity() {
        return productSendCity;
    }

    public void setProductSendCountry(String productSendCountry) {
        this.productSendCountry = productSendCountry;
    }

    public void setProductSendProvince(String productSendProvince) {
        this.productSendProvince = productSendProvince;
    }

    public void setProductSendCity(String productSendCity) {
        this.productSendCity = productSendCity;
    }

    public String getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(String product_detail) {
        this.product_detail = product_detail;
    }

    public List<EnqProReqVo> getTable() {
        return table;
    }

    public void setTable(List<EnqProReqVo> table) {
        this.table = table;
    }
}
