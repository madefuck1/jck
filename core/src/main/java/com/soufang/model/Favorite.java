package com.soufang.model;

import java.util.Date;

public class Favorite {
    private Long favoriteId;

    private Long userId;

    private Long favoriteTargetId;

    private String favoriteTargetName;

    private Integer favoriteTargetType;

    private Date createTime;

    private  ProductSpec productSpec;

    private  ProductStatistics productStatistics;

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFavoriteTargetId() {
        return favoriteTargetId;
    }

    public void setFavoriteTargetId(Long favoriteTargetId) {
        this.favoriteTargetId = favoriteTargetId;
    }

    public String getFavoriteTargetName() {
        return favoriteTargetName;
    }

    public void setFavoriteTargetName(String favoriteTargetName) {
        this.favoriteTargetName = favoriteTargetName;
    }

    public Integer getFavoriteTargetType() {
        return favoriteTargetType;
    }

    public void setFavoriteTargetType(Integer favoriteTargetType) {
        this.favoriteTargetType = favoriteTargetType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public ProductSpec getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(ProductSpec productSpec) {
        this.productSpec = productSpec;
    }

    public ProductStatistics getProductStatistics() {
        return productStatistics;
    }

    public void setProductStatistics(ProductStatistics productStatistics) {
        this.productStatistics = productStatistics;
    }
}