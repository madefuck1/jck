package com.soufang.model;

import java.util.Date;

public class Assess {
    private Long assessId;

    private Long shopId;

    private Long productId;

    private String orderNumber;

    private Integer assessType;

    private String assessContent;

    private Long assessUserId;

    private Date createTime;

    public Long getAssessId() {
        return assessId;
    }

    public void setAssessId(Long assessId) {
        this.assessId = assessId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public Integer getAssessType() {
        return assessType;
    }

    public void setAssessType(Integer assessType) {
        this.assessType = assessType;
    }

    public String getAssessContent() {
        return assessContent;
    }

    public void setAssessContent(String assessContent) {
        this.assessContent = assessContent == null ? null : assessContent.trim();
    }

    public Long getAssessUserId() {
        return assessUserId;
    }

    public void setAssessUserId(Long assessUserId) {
        this.assessUserId = assessUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}