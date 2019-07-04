package com.soufang.vo.StoreConstruction;

import com.soufang.base.PageBase;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ListStoreProductReqVo extends PageBase {

    private Long exclusiveAssortId;

    private String productName;

    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private Integer assortType;
}
