package com.soufang.app.vo.order;

import com.soufang.app.vo.AppVo;

import java.math.BigDecimal;

public class UpActualPrice extends AppVo {
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
