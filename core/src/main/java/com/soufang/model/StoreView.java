package com.soufang.model;

public class StoreView {
    private Long storeViewId;

    private Long storeConstructionId;

    private Long shopId;

    private String viewurl;

    public Long getStoreViewId() {
        return storeViewId;
    }

    public void setStoreViewId(Long storeViewId) {
        this.storeViewId = storeViewId;
    }

    public Long getStoreConstructionId() {
        return storeConstructionId;
    }

    public void setStoreConstructionId(Long storeConstructionId) {
        this.storeConstructionId = storeConstructionId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getViewurl() {
        return viewurl;
    }

    public void setViewurl(String viewurl) {
        this.viewurl = viewurl == null ? null : viewurl.trim();
    }
}