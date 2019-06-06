package com.soufang.model;

public class StoreProductAssort {
    private Long storeProductAssortId;

    private Long exclusiveAssortId;

    private Long shopId;

    private Long productId;

    private Integer isShow;

    public Long getStoreProductAssortId() {
        return storeProductAssortId;
    }

    public void setStoreProductAssortId(Long storeProductAssortId) {
        this.storeProductAssortId = storeProductAssortId;
    }

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
}