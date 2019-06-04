package com.soufang.base.search.order;

import java.util.Date;

/**
 * @Auther: chen
 * @Date: 2019/4/23
 * @Description:
 */
public class OrderSo {

    private Long userId ;
    private String orderNumber ;
    private Integer orderStatus ;
    private Integer orderType;
    private Long shopId ;
    private Date beginDate ;
    private Date endDate ;
    private Integer page ;
    private Integer limit ;
    private String orderStatusList ;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }


    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatusList() {
        return orderStatusList;
    }

    public void setOrderStatusList(String orderStatusList) {
        this.orderStatusList = orderStatusList;
    }
}
