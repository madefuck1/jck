package com.soufang.base.search.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddOrderShopSo {
    private Long shopId;
    private String shopName;
    private String shopIdAndName;
    private String sendName;
    private String sendAddress;
    private String sendPhone;
    private List<AddOrderProductSo> orderProducts;
    private Integer LAY_TABLE_INDEX;
}
