package com.soufang.app.vo.assess;

import java.util.List;

public class AddAssessVo {


    private String orderNumber ;
    private Long shopId;
    private List<AddAssessDetailVo> data;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<AddAssessDetailVo> getData() {
        return data;
    }

    public void setData(List<AddAssessDetailVo> data) {
        this.data = data;
    }
}
