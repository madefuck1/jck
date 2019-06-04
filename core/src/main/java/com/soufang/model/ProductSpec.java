package com.soufang.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProductSpec {
    private Long productSpecId;

    private Long productId;

    private String specName;

    private Integer priceSecret;

    private Long minNumber;

    private Long maxNumber;

    private BigDecimal specNumber;

    private Date createTime;

    public Long getProductSpecId() {
        return productSpecId;
    }

    public void setProductSpecId(Long productSpecId) {
        this.productSpecId = productSpecId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

    public Integer getPriceSecret() {
        return priceSecret;
    }

    public void setPriceSecret(Integer priceSecret) {
        this.priceSecret = priceSecret;
    }

    public Long getMinNumber() {
        return minNumber;
    }

    public void setMinNumber(Long minNumber) {
        this.minNumber = minNumber;
    }

    public Long getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Long maxNumber) {
        this.maxNumber = maxNumber;
    }

    public BigDecimal getSpecNumber() {
        return specNumber;
    }

    public void setSpecNumber(BigDecimal specNumber) {
        this.specNumber = specNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}