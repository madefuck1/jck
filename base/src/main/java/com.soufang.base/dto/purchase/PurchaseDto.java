package com.soufang.base.dto.purchase;

import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class PurchaseDto {


    private String purchaseNumber;

    private Long enquiryNumber;

    private Long shopId;
    private Long userId;

    private String shopName;

    private Long enquiryProductId;

    private BigDecimal unitPrice;

    private BigDecimal sumPrice;

    private Integer offerStatus;
    private String strOfferStatus;

    private Date offerTime;
    private String strOfferTime;

    private ShopDto shopDto;

    private String remark;

    public ShopDto getShopDto() {
        return shopDto;
    }

    public void setShopDto(ShopDto shopDto) {
        this.shopDto = shopDto;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStrOfferStatus() {
        if(offerStatus == null){
            return null;
        }else if(offerStatus == 1){
            return "能够提供";
        }else if(offerStatus == 0){
            return "不能够提供";
        }else {
            return "商家未提供此信息";
        }
    }

    public String getStrOfferTime() {
        if(offerTime == null){
            return null;
        }else {
            return DateUtils.date2String(offerTime,DateUtils.format1);
        }
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber == null ? null : purchaseNumber.trim();
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getEnquiryProductId() {
        return enquiryProductId;
    }

    public void setEnquiryProductId(Long enquiryProductId) {
        this.enquiryProductId = enquiryProductId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Integer getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(Integer offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Date getOfferTime() {
        return offerTime;
    }

    public void setOfferTime(Date offerTime) {
        this.offerTime = offerTime;
    }

    public String getShopName() {
        return shopName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(Long enquiryNumber) {
        this.enquiryNumber = enquiryNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
