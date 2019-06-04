package com.soufang.base.search.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class AddOrderSo {
    private Long buyerId;
    private String buyer;
    private String takeName;
    private String takeAddress;
    private String takePhone;
    private Integer orderStatus;
    private BigDecimal paidMoney;
    private List<AddOrderShopSo> orderShop;
}
