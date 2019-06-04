package com.soufang.vo.purchase;

import java.math.BigDecimal;

public class UpdateUnitPrice {

    private String purchaseNumber;

    private BigDecimal unitPrice;

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
