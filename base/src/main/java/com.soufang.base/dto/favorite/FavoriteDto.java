package com.soufang.base.dto.favorite;

import com.soufang.base.PageBase;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.product.ProductSpecDto;
import com.soufang.base.dto.product.ProductStatisticsDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class FavoriteDto extends PageBase implements Serializable {
    // 序号
    private Long favoriteId;
    // 用户id
    private Long userId;
    // 收藏对象id
    private Long favoriteTargetId;
    // 收藏对象名称
    private String favoriteTargetName;
    // 收藏对象类型（1：产品；2：店铺）
    private Integer favoriteTargetType;
    // 创建时间
    private Date createTime;

    //收藏对象
    private ProductDto productDto;
    private ProductStatisticsDto productStatisticsDto;
    private ProductSpecDto productSpecDto;
    private Integer page;

    private Integer limit;

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

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }


    public ProductStatisticsDto getProductStatisticsDto() {
        return productStatisticsDto;
    }


    public void setProductStatisticsDto(ProductStatisticsDto productStatisticsDto) {
        this.productStatisticsDto = productStatisticsDto;
    }

    public ProductSpecDto getProductSpecDto() {
        return productSpecDto;
    }

    public void setProductSpecDto(ProductSpecDto productSpecDto) {
        this.productSpecDto = productSpecDto;
    }
}

