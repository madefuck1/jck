package com.soufang.base.search.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddOrderProductSo {
    private String productName;
    private String productNumber;
    private String productUnit;
    private String productPrice;
    private String productSpec;
    private String productColor;
    private Integer LAY_TABLE_INDEX;
}
