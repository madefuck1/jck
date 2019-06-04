package com.soufang.base.dto.order;

import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.product.ProductDto;

import java.math.BigDecimal;

/**
 * @Auther: chen
 * @Date: 2019/4/23
 * @Description:
 */
public class OrderProductDto {
    private Long orderProductId;

    private Long orderShopId;

    private Long productId;

    private String productName;

    private BigDecimal productNumber;

    private String productUnit;

    private BigDecimal productPrice;

    private String productSpec;

    private String productColor;
    private String productImage;
    private String image;

    private ProductDto productDto;

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Long getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Long orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Long getOrderShopId() {
        return orderShopId;
    }

    public void setOrderShopId(Long orderShopId) {
        this.orderShopId = orderShopId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
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

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
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

    public String getProductImage() {
        return PropertiesParam.file_pre + image;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
