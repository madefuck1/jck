package com.soufang.base.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Auther: chen
 * @Date: 2019/5/16
 * @Description:
 */
@Getter
@Setter
public class SpecDetailDto {

    private Long productSpecId;

    private Integer priceSecret;

    private Long minNumber;

    private Long maxNumber;

    private BigDecimal specNumber;

}
