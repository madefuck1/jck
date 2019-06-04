package com.soufang.vo.order;

import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderListVo extends BaseVo {

    List<OrderShopDto> list;

    // 页面回传参数
    private Integer flag;
    private Integer startPage;
    private Integer endPage;
    private Integer number;
    private Integer orderStatus;
    private Integer hitPage;
    private Integer orderType;
    private Integer totalPages;




}
