package com.soufang.app.vo.shopCar;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/14
 * @Description:
 */
public class ToPayListReqVo {

    private List<ToPayReqVo> shopCarList ;

    private Long addressId ;
    private Integer orderType ;
    private String remark ;

    public List<ToPayReqVo> getShopCarList() {
        return shopCarList;
    }

    public void setShopCarList(List<ToPayReqVo> shopCarList) {
        this.shopCarList = shopCarList;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
