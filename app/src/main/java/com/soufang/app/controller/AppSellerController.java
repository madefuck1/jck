package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppSellerFeign;
import com.soufang.app.vo.AppVo;
import com.soufang.app.vo.order.OrderShopListVo;
import com.soufang.app.vo.order.OrderShopVo;
import com.soufang.app.vo.order.SendProductVo;
import com.soufang.app.vo.order.UpActualPrice;
import com.soufang.base.Result;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.enums.OrderStatusEnum;
import com.soufang.base.enums.PageOrderStatusEnum;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.order.OrderSo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("app/order/")
public class AppSellerController extends AppBaseController {

    @Autowired
    AppSellerFeign appSellerFeign;

    //卖家修改价格
    @AppMemberAccess
    @RequestMapping(value = "changeMoney", method = RequestMethod.POST)
    public AppVo changeMoney( @RequestBody UpActualPrice upActualPrice) {
        AppVo vo =new AppVo();
        OrderShopDto shopDto = new OrderShopDto();
        if(StringUtils.isBlank(String.valueOf(upActualPrice.getActualPrice()))){
            vo.setMessage("请输入价格");
            vo.setSuccess(false);
            return vo;
        }
        BeanUtils.copyProperties(upActualPrice,shopDto);
        Result result = appSellerFeign.updateActualPrice(shopDto);
        vo.setMessage(result.getMessage());
        vo.setSuccess(result.isSuccess());
        return vo;
    }

    //获取卖家订单列表
    @AppMemberAccess
    @RequestMapping(value = "getSellList", method = RequestMethod.POST)
    public OrderShopListVo getSellList(HttpServletRequest request, @RequestBody OrderSo orderSo) {
        ShopDto shopInfo = this.getShopInfo(request);
        orderSo.setShopId(shopInfo.getShopId());
        orderSo.setOrderStatusList(PageOrderStatusEnum.getOrderStatusList(orderSo.getOrderStatus()));
        orderSo.setPage(orderSo.getPage());
        if (StringUtils.isBlank(orderSo.getOrderNumber())) {
            orderSo.setOrderNumber(null);
        }
        PageHelp<OrderShopDto> list = appSellerFeign.getList(orderSo);
        OrderShopListVo vo = new OrderShopListVo();
        if(list != null && list.getData().size() > 0){
            vo.setMessage("获取订单列表成功");
            vo.setData(list.getData());
            vo.setSuccess(true);
        }else {
            vo.setMessage("您没有订单信息");
            vo.setData(null);
            vo.setSuccess(false);
        }
        vo.setLimit(orderSo.getLimit());
        vo.setPage(orderSo.getPage());
        return vo;
    }

    //卖家订单详情
    @AppMemberAccess
    @RequestMapping(value = "sellOrderDetail/{orderShopId}", method = RequestMethod.POST)
    public OrderShopVo sellOrderDetail(@PathVariable Long orderShopId) {
        OrderShopDto orderShopDto = appSellerFeign.getDetail(orderShopId);
        for (OrderProductDto orderProductDto : orderShopDto.getOrderProducts()) {
            orderProductDto.setProductImage(orderProductDto.getProductImage());
        }
        OrderShopVo vo = new OrderShopVo();
        vo.setData(orderShopDto);
        if(orderShopDto == null){
            vo.setMessage("获取失败");
            vo.setSuccess(false);
        }else {
            vo.setMessage("获取成功");
            vo.setSuccess(true);
        }
        return vo;
    }

    //卖家发货   AppOrderController 已写
    @AppMemberAccess
    @RequestMapping(value = "sellerSendProduct", method = RequestMethod.POST)
    public AppVo sellerSendProduct(@RequestBody SendProductVo sendProduct) {
        OrderShopDto orderShopDto = new OrderShopDto();
        AppVo vo = new AppVo();
        orderShopDto.setOrderShopStatus(OrderStatusEnum.to_take.getValue());
        BeanUtils.copyProperties(sendProduct,orderShopDto);
        Result result = appSellerFeign.sendProduct(orderShopDto);
        vo.setMessage(result.getMessage());
        vo.setSuccess(result.isSuccess());
        return vo;
    }

}
