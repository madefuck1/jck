package com.soufang.Vo.order;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.order.OrderShopDto;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/23
 * @Description:
 */
public class OrderShopVo extends AdminVo {

    private List<OrderShopDto> data ;

    public List<OrderShopDto> getData() {
        return data;
    }

    public void setData(List<OrderShopDto> data) {
        this.data = data;
    }
}
