package com.soufang.model;

import java.util.Date;

public class Product {
    private Long productId;

    private Long shopId;

    private String shopName;

    private String productName;

    private Integer isUpper;

    private Long assortId;

    private String productJson;

    private String productUnit;

    private String productDetail;

    private String productImage;

    private Integer productLevel;

    private String productSendCountry;

    private String productSendProvince;

    private String productSendCity;

    private Date createTime;

    private String key1;

    private String key1Value;

    private String key2;

    private String key2Value;

    private String key3;

    private String key3Value;

    private String key4;

    private String key4Value;

    private String key5;

    private String key5Value;

    private String repColorName;

    private String repSpecName;

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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getIsUpper() {
        return isUpper;
    }

    public void setIsUpper(Integer isUpper) {
        this.isUpper = isUpper;
    }

    public Long getAssortId() {
        return assortId;
    }

    public void setAssortId(Long assortId) {
        this.assortId = assortId;
    }

    public String getProductJson() {
        return productJson;
    }

    public void setProductJson(String productJson) {
        this.productJson = productJson == null ? null : productJson.trim();
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit == null ? null : productUnit.trim();
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail == null ? null : productDetail.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public Integer getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(Integer productLevel) {
        this.productLevel = productLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1 == null ? null : key1.trim();
    }

    public String getKey1Value() {
        return key1Value;
    }

    public void setKey1Value(String key1Value) {
        this.key1Value = key1Value == null ? null : key1Value.trim();
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2 == null ? null : key2.trim();
    }

    public String getKey2Value() {
        return key2Value;
    }

    public void setKey2Value(String key2Value) {
        this.key2Value = key2Value == null ? null : key2Value.trim();
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3 == null ? null : key3.trim();
    }

    public String getKey3Value() {
        return key3Value;
    }

    public void setKey3Value(String key3Value) {
        this.key3Value = key3Value == null ? null : key3Value.trim();
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4 == null ? null : key4.trim();
    }

    public String getKey4Value() {
        return key4Value;
    }

    public void setKey4Value(String key4Value) {
        this.key4Value = key4Value == null ? null : key4Value.trim();
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5 == null ? null : key5.trim();
    }

    public String getKey5Value() {
        return key5Value;
    }

    public void setKey5Value(String key5Value) {
        this.key5Value = key5Value == null ? null : key5Value.trim();
    }

    public String getProductSendCountry() {
        return productSendCountry;
    }

    public void setProductSendCountry(String productSendCountry) {
        this.productSendCountry = productSendCountry;
    }

    public String getProductSendProvince() {
        return productSendProvince;
    }

    public void setProductSendProvince(String productSendProvince) {
        this.productSendProvince = productSendProvince;
    }


    public String getProductSendCity() {
        return productSendCity;
    }

    public void setProductSendCity(String productSendCity) {
        this.productSendCity = productSendCity;
    }


    public String getRepColorName() {
        return repColorName;
    }

    public void setRepColorName(String repColorName) {
        this.repColorName = repColorName;
    }


    public String getRepSpecName() {
        return repSpecName;
    }

    public void setRepSpecName(String repSpecName) {
        this.repSpecName = repSpecName;
    }
}