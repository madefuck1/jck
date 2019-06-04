package com.soufang.model;

import java.math.BigDecimal;

public class ShopCarProduct {
    private Long shopCarProductId;

    private Long shopCarId;

    private Long productId;

    private String productName ;

    private BigDecimal productNumber;

    private String productUnit;

    private String productSpec;

    private String productColor;
    private BigDecimal productPrice;

    private String productImage;

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getShopCarProductId() {
        return shopCarProductId;
    }

    public void setShopCarProductId(Long shopCarProductId) {
        this.shopCarProductId = shopCarProductId;
    }

    public Long getShopCarId() {
        return shopCarId;
    }

    public void setShopCarId(Long shopCarId) {
        this.shopCarId = shopCarId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(BigDecimal productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit == null ? null : productUnit.trim();
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec == null ? null : productSpec.trim();
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor == null ? null : productColor.trim();
    }
}