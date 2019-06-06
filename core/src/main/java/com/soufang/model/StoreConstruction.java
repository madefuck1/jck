package com.soufang.model;

public class StoreConstruction {
    private Long storeConstructionId;

    private Long shopId;

    private String storeLogo;

    private String storeNavColor;

    private Integer storeStatus;

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

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo == null ? null : storeLogo.trim();
    }

    public String getStoreNavColor() {
        return storeNavColor;
    }

    public void setStoreNavColor(String storeNavColor) {
        this.storeNavColor = storeNavColor == null ? null : storeNavColor.trim();
    }

    public Integer getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(Integer storeStatus) {
        this.storeStatus = storeStatus;
    }
}