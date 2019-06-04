package com.soufang.app.vo.shopCar;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DetailAddReqVo {
    // 产品id
    private Long productId;
    // 店铺 id
    private Long shopId;
    // 数量
    private BigDecimal productNum;
    // 颜色
    private String productColor;
    // 规格
    private String productSpec;
    // 单位
    private String productUnit;
}
