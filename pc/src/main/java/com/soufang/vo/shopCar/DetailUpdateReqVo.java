package com.soufang.vo.shopCar;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DetailUpdateReqVo {
    private Long shopCarProductId;
    private Long shopCarId;
    private Long productId ;
    private String productColor;
    private String productSpec;
    private BigDecimal productNum;
}
