package com.soufang.app.vo.shopCar;

import com.soufang.base.dto.shopCar.ShopCarProductDto;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/14
 * @Description:
 */
public class ToPayReqVo {

    private Long shopCarId ;
    private List<ShopCarProductDto> shopCatProductList ;

    private List<Long> shopCarProductIds;


    public Long getShopCarId() {
        return shopCarId;
    }

    public void setShopCarId(Long shopCarId) {
        this.shopCarId = shopCarId;
    }

    public List<ShopCarProductDto> getShopCatProductList() {
        return shopCatProductList;
    }

    public void setShopCatProductList(List<ShopCarProductDto> shopCatProductList) {
        this.shopCatProductList = shopCatProductList;
    }

    public List<Long> getShopCarProductIds() {
        return shopCarProductIds;
    }

    public void setShopCarProductIds(List<Long> shopCarProductIds) {
        this.shopCarProductIds = shopCarProductIds;
    }
}
