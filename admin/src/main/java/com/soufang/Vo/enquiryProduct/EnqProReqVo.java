package com.soufang.Vo.enquiryProduct;

public class EnqProReqVo {
    private String productName;
    private String assortId;
    private String assortName;
    private String productNumber;
    private String productUnit;
    private String productImg;
    private Integer LAY_TABLE_INDEX;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getAssortName() {
        return assortName;
    }

    public void setAssortName(String assortName) {
        this.assortName = assortName;
    }


    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Integer getLAY_TABLE_INDEX() {
        return LAY_TABLE_INDEX;
    }

    public void setLAY_TABLE_INDEX(Integer LAY_TABLE_INDEX) {
        this.LAY_TABLE_INDEX = LAY_TABLE_INDEX;
    }

    public String getAssortId() {
        return assortId;
    }

    public void setAssortId(String assortId) {
        this.assortId = assortId;
    }
}
