package com.soufang.base.search.enquiry;

public class EnquirySo {

    private String enquiryTitle;
    private Integer page ;
    private Integer limit ;
    private Long userId;
    private Long shopId;
    private Integer enquiryStatus;
    private Integer enquiryType;
    private String enquiryNumber;
    private String enquiryProductId;
    private long assortId;

    //截止时间排序判断字段
    private  String endTimeSort;
    //排序类型-1.正序、2.倒序
    private  String orderType;

    public String getEndTimeSort() {
        return endTimeSort;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setEndTimeSort(String endTimeSort) {
        this.endTimeSort = endTimeSort;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public long getAssortId() {
        return assortId;
    }

    public void setAssortId(long assortId) {
        this.assortId = assortId;
    }

    public String getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(String enquiryNumber) {
        this.enquiryNumber = enquiryNumber;
    }

    public Integer getEnquiryType() {
        return enquiryType;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public void setEnquiryType(Integer enquiryType) {
        this.enquiryType = enquiryType;
    }

    public String getEnquiryTitle() {
        return enquiryTitle;
    }

    public void setEnquiryTitle(String enquiryTitle) {
        this.enquiryTitle = enquiryTitle;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getEnquiryStatus() {
        return enquiryStatus;
    }

    public void setEnquiryStatus(Integer enquiryStatus) {
        this.enquiryStatus = enquiryStatus;
    }

    public String getEnquiryProductId() {
        return enquiryProductId;
    }

    public void setEnquiryProductId(String enquiryProductId) {
        this.enquiryProductId = enquiryProductId;
    }
}
