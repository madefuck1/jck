package com.soufang.base.dto.shop;

import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.enums.ShopStatusEnum;
import com.soufang.base.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class ShopDto {
    private Long shopId;
    private Long userId;
    private String shopName;
    private Integer shopLevel;
    private String shopIntroduce;
    private String avatarUrl;
    private String businessScope;
    private String mainProducts;
    private Integer shopStatus;
    private String strShopStatus;
    private String refuseReason;
    private Date createTime;
    private String strCreateTime;
    private String url;
    // 店铺产品总数
    private Integer productCount;
    // 店铺统计信息
    private ShopStatisticsDto shopStatisticsDto;
    // 店铺logo图片
    private String logoUrl;
    // app店铺附加产品列表
    private List<ProductDto> productDtoList;
    // 公司信息
    private CompanyDto companyDto;
    // 是否被收藏
    private Boolean collection;

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    public String getStrCreateTime() {
        if (createTime != null) {
            return DateUtils.date2String(createTime, DateUtils.format1);
        } else {
            return null;
        }
    }

    public String getStrShopStatus() {
        if (shopStatus == null) {
            return null;
        } else {
            return ShopStatusEnum.getShopStatusByKey(shopStatus);
        }
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(Integer shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getShopIntroduce() {
        return shopIntroduce;
    }

    public void setShopIntroduce(String shopIntroduce) {
        this.shopIntroduce = shopIntroduce;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(String mainProducts) {
        this.mainProducts = mainProducts;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ProductDto> getProductDtoList() {
        return productDtoList;
    }

    public void setProductDtoList(List<ProductDto> productDtoList) {
        this.productDtoList = productDtoList;
    }

    public ShopStatisticsDto getShopStatisticsDto() {
        return shopStatisticsDto;
    }

    public void setShopStatisticsDto(ShopStatisticsDto shopStatisticsDto) {
        this.shopStatisticsDto = shopStatisticsDto;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


    public Boolean getCollection() {
        return collection;
    }

    public void setCollection(Boolean collection) {
        this.collection = collection;
    }
}

