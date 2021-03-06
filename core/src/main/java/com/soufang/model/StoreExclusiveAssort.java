package com.soufang.model;

import java.util.Date;

public class StoreExclusiveAssort {
    private Long exclusiveAssortId;

    private Long shopId;

    private String assortName;

    private String sortName;

    private Integer isShow;

    private Date createTime;

    public Long getExclusiveAssortId() {
        return exclusiveAssortId;
    }

    public void setExclusiveAssortId(Long exclusiveAssortId) {
        this.exclusiveAssortId = exclusiveAssortId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getAssortName() {
        return assortName;
    }

    public void setAssortName(String assortName) {
        this.assortName = assortName == null ? null : assortName.trim();
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName == null ? null : sortName.trim();
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}