package com.soufang.vo.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderUpdateStatusReqVo {
    // 页面订单状态
    private Integer status;
    // 订单店铺id
    private Long orderShopId;
    // 订单店铺实际状态
//    private Integer orderShopStatus;
    // 订单发货方式（具体快递/物流名称）
    private String sendType;
    // 单号
    private String sendNumber;


    // 页面回传参数
    private Integer flag;
    private Integer startPage;
    private Integer endPage;
    private Integer number;
    private Integer orderStatus;
    private Integer hitPage;
    private Integer orderType;



}
