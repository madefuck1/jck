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

@Getter
@Setter
public class ProductColorDto {

    private Long productColorId;

    private Long productId;

    private Integer isSpot;

    private String productColor;

    private BigDecimal spock;

    private Date createTime;

    private String colorImage;
}
