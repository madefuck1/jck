package com.soufang.base.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: chen
 * @Date: 2019/4/25
 * @Description:
 */
@Setter
@Getter
public class ProductSpecDto {

    private Long productSpecId;

    private Long productId;

    private String specName;

    private Integer priceSecret;
    private Long min ;
    private Long minNumber;

    private Long maxNumber;

    private BigDecimal specNumber;

    private Date createTime;
}
