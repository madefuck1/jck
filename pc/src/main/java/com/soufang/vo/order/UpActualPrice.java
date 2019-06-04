package com.soufang.vo.order;

import com.soufang.vo.BaseVo;

import java.math.BigDecimal;

public class UpActualPrice extends BaseVo {
    private String orderNumber;
    private BigDecimal actualPrice;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }
}
