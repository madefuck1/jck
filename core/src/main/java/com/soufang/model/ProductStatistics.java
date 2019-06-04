package com.soufang.model;

public class ProductStatistics {
    private Long productStatisticsId;

    private Long productId;

    private Long collectionNumber;

    private Long browseNumber;

    private Long dealNumber;

    private String productPrice;

    private String productStock;

    public Long getProductStatisticsId() {
        return productStatisticsId;
    }

    public void setProductStatisticsId(Long productStatisticsId) {
        this.productStatisticsId = productStatisticsId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCollectionNumber() {
        return collectionNumber;
    }

    public void setCollectionNumber(Long collectionNumber) {
        this.collectionNumber = collectionNumber;
    }

    public Long getBrowseNumber() {
        return browseNumber;
    }

    public void setBrowseNumber(Long browseNumber) {
        this.browseNumber = browseNumber;
    }

    public Long getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(Long dealNumber) {
        this.dealNumber = dealNumber;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice == null ? null : productPrice.trim();
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock == null ? null : productStock.trim();
    }
}