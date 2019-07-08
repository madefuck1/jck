package com.soufang.vo.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSubmitReqVo {
    // 产品
    private Long productId;
    // 颜色
    private String productColor;
    // 规格
    private String productSpecName;
    // 数量
    private Long productNumber;
    // 买家留言
    private String remark;
    // 地址id
    private Long addressId ;
    // 支付方式
    private Integer payType ;
}
