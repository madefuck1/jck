package com.soufang.base.dto.storeConstruction;

public class StoreViewDto {
    private Long storeViewId;

    private Long storeConstructionId;

    private Long shopId;

    private String viewurl;
    private String viewTitle;

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
    public String getViewTitle() {
        return viewTitle;
    }

    public void setViewTitle(String viewTitle) {
        this.viewTitle = viewTitle;
    }
}