package com.soufang.model;

import java.util.Date;

public class FootPrint {
    private Long footprintId;

    private Long userId;

    private Long productId;

    private Long shopId;

    private Date createTime;

    private Date createTimes;

    private Product  product;
    private Shop shop;
    private ProductStatistics productStatistics;

    public void setProductStatistics(ProductStatistics productStatistics) {
        this.productStatistics = productStatistics;
    }

    public ProductStatistics getProductStatistics() {
        return productStatistics;
    }

    public Product getProduct() {
        return product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Long getFootprintId() {
        return footprintId;
    }

    public void setFootprintId(Long footprintId) {
        this.footprintId = footprintId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTimes() {
        return createTimes;
    }

    public void setCreateTimes(Date createTimes) {
        this.createTimes = createTimes;
    }
}