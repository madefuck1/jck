/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: EnquiryUpdateVo
 * Author:   小三
 * Date:     2019/5/20 10:15
 * Description: 修改求购单
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.vo.Enquiry;

import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈修改求购单〉
 *
 * @author 小三
 * @create 2019/5/20
 * @since 1.0.0
 */
public class EnquiryUpdateVo {

    //询价单号
    private String enquiryNumber;

    //询价用户
    private Long userId;

    //询价标题
    private String enquiryTitle;

    //采购类型
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

    //备注
    private String enquiryRemark;

    private List<EnquiryProductDto> enquiryProduct;

    public void setEnquiryProduct(List<EnquiryProductDto> enquiryProduct) {
        this.enquiryProduct = enquiryProduct;
    }

    public List<EnquiryProductDto> getEnquiryProduct() {

        return enquiryProduct;
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
}
