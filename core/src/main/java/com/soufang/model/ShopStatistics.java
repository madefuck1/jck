package com.soufang.model;

public class ShopStatistics {
    private Long shopStatisticsId;

    private Long shopId;

    private Long shopProductCount;

    private Long shopCollectionCount;

    private Long shopBrowseCount;

    public Long getShopStatisticsId() {
        return shopStatisticsId;
    }

    public void setShopStatisticsId(Long shopStatisticsId) {
        this.shopStatisticsId = shopStatisticsId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopProductCount() {
        return shopProductCount;
    }

    public void setShopProductCount(Long shopProductCount) {
        this.shopProductCount = shopProductCount;
    }

    public Long getShopCollectionCount() {
        return shopCollectionCount;
    }

    public void setShopCollectionCount(Long shopCollectionCount) {
        this.shopCollectionCount = shopCollectionCount;
    }

    public Long getShopBrowseCount() {
        return shopBrowseCount;
    }

    public void setShopBrowseCount(Long shopBrowseCount) {
        this.shopBrowseCount = shopBrowseCount;
    }
}