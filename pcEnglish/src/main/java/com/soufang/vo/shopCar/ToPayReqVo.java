package com.soufang.vo.shopCar;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/14
 * @Description:
 */
public class ToPayReqVo {

    private Long shopCarId ;
    private List<Long> shopCarProductIds ;


    public Long getShopCarId() {
        return shopCarId;
    }

    public void setShopCarId(Long shopCarId) {
        this.shopCarId = shopCarId;
    }

    public List<Long> getShopCarProductIds() {
        return shopCarProductIds;
    }

    public void setShopCarProductIds(List<Long> shopCarProductIds) {
        this.shopCarProductIds = shopCarProductIds;
    }

}
