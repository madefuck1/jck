package com.soufang.app.vo.assess;

import java.util.List;

public class AddAssessVo {


    private String orderNumber ;
    private Long shopId;
    private List<AddAssessDetailVo> list;

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

    public List<AddAssessDetailVo> getList() {
        return list;
    }

    public void setList(List<AddAssessDetailVo> list) {
        this.list = list;
    }
}
