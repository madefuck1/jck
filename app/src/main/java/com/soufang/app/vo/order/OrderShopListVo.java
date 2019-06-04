package com.soufang.app.vo.order;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.order.OrderShopDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/21
 * @Description:
 */
@Getter
@Setter
public class OrderShopListVo extends AppVo {

    private List<OrderShopDto> data;
    private int page ;
    private int limit ;

}
