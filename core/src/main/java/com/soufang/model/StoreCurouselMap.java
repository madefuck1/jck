package com.soufang.model;

public class StoreCurouselMap {
    private Long storeCurouselMapId;

    private Long storeConstructionId;

    private Long productId;

    private String curouselMapUrl;

    private String storeCurouselMapLink;

    public Long getStoreCurouselMapId() {
        return storeCurouselMapId;
    }

    public void setStoreCurouselMapId(Long storeCurouselMapId) {
        this.storeCurouselMapId = storeCurouselMapId;
    }

    public Long getStoreConstructionId() {
        return storeConstructionId;
    }

    public void setStoreConstructionId(Long storeConstructionId) {
        this.storeConstructionId = storeConstructionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCurouselMapUrl() {
        return curouselMapUrl;
    }

    public void setCurouselMapUrl(String curouselMapUrl) {
        this.curouselMapUrl = curouselMapUrl == null ? null : curouselMapUrl.trim();
    }

    public String getStoreCurouselMapLink() {
        return storeCurouselMapLink;
    }

    public void setStoreCurouselMapLink(String storeCurouselMapLink) {
        this.storeCurouselMapLink = storeCurouselMapLink == null ? null : storeCurouselMapLink.trim();
    }
}