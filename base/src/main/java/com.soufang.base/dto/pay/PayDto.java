package com.soufang.base.dto.pay;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: chen
 * @Date: 2019/5/22
 * @Description:
 */
public class PayDto {
    private Long payId;

    private String payNumber;

    private String orderNumber;

    private Integer orderType;

    private String unitPayNumber;

    private Long userId;

    private BigDecimal payMoney;

    private Integer payStatus;

    private Integer payType;

    private Date createTime;

    private Date payTime;


    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber == null ? null : payNumber.trim();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getUnitPayNumber() {
        return unitPayNumber;
    }

    public void setUnitPayNumber(String unitPayNumber) {
        this.unitPayNumber = unitPayNumber == null ? null : unitPayNumber.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
