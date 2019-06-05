package com.soufang.model;

import java.util.List;

public class EnquiryProduct {
    private Long enquiryProductId;

    private String enquiryNumber;

    private String productName;

    private Long productAssort;

    private String assortName;

    private Long productNumber;

    private String productUnit;

    private String productImage;

    private List<Purchase> purchases ;

    private Assort assort;

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public String getAssortName() {
        return assortName;
    }

    public void setAssortName(String assortName) {
        this.assortName = assortName;
    }

    public Long getEnquiryProductId() {
        return enquiryProductId;
    }

    public void setEnquiryProductId(Long enquiryProductId) {
        this.enquiryProductId = enquiryProductId;
    }

    public String getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(String enquiryNumber) {
        this.enquiryNumber = enquiryNumber == null ? null : enquiryNumber.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Long getProductAssort() {
        return productAssort;
    }

    public void setProductAssort(Long productAssort) {
        this.productAssort = productAssort;
    }

    public Long getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Long productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit == null ? null : productUnit.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public Assort getAssort() {
        return assort;
    }

    public void setAssort(Assort assort) {
        this.assort = assort;
    }
}