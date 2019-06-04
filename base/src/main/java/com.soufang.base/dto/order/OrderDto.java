package com.soufang.base.dto.order;

import com.soufang.base.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
public class OrderDto {

    //无
    private String orderNumber;

    private Long userId;

    private String buyerName;

    private String takeName;

    private String takeAddress;

    private String takePhone;
    //无
    private Date sendTime;
    private String strSendTime;
    //无
    private Integer orderStatus;

    private Integer orderType;

    private BigDecimal totalMoney;
    //无
    private BigDecimal paidMoney;

    private Date createTime;

    private String orderRemark;

    private List<OrderShopDto> orderShopDtoList ;

    public String getStrSendTime() {
        if(sendTime == null){
            return "空";
        }else {
            return DateUtils.date2String(sendTime,DateUtils.format2);
        }
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getTakeName() {
        return takeName;
    }

    public void setTakeName(String takeName) {
        this.takeName = takeName;
    }

    public String getTakeAddress() {
        return takeAddress;
    }

    public void setTakeAddress(String takeAddress) {
        this.takeAddress = takeAddress;
    }

    public String getTakePhone() {
        return takePhone;
    }

    public void setTakePhone(String takePhone) {
        this.takePhone = takePhone;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public List<OrderShopDto> getOrderShopDtoList() {
        return orderShopDtoList;
    }

    public void setOrderShopDtoList(List<OrderShopDto> orderShopDtoList) {
        this.orderShopDtoList = orderShopDtoList;
    }
}
