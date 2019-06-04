package com.soufang.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProductColor {
    private Long productColorId;

    private Long productId;

    private Integer isSpot;

    private String productColor;

    private BigDecimal spock;

    private Date createTime;

    private String colorImage;

    public Long getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(Long productColorId) {
        this.productColorId = productColorId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getIsSpot() {
        return isSpot;
    }

    public void setIsSpot(Integer isSpot) {
        this.isSpot = isSpot;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor == null ? null : productColor.trim();
    }

    public BigDecimal getSpock() {
        return spock;
    }

    public void setSpock(BigDecimal spock) {
        this.spock = spock;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getColorImage() {
        return colorImage;
    }

    public void setColorImage(String colorImage) {
        this.colorImage = colorImage == null ? null : colorImage.trim();
    }
}