package com.soufang.app.vo.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderStatusUpdateReqVo {
    // 订单店铺Id
    private Long orderShopId;
    // 订单状态
    private Integer orderShopStatus;
    // 发货类型（快递/物流名称）
    private String sendType;
    // 单号
    private String sendNumber;
}
