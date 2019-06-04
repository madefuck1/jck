package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppAddressFeign;
import com.soufang.app.feign.AppOrderFeign;
import com.soufang.app.feign.AppProductManageFeign;
import com.soufang.app.feign.AppShopCarFeign;
import com.soufang.app.vo.AppVo;
import com.soufang.app.vo.order.OrderShopListVo;
import com.soufang.app.vo.order.OrderShopVo;
import com.soufang.app.vo.order.OrderStatusUpdateReqVo;
import com.soufang.app.vo.order.OrderSubmitReqVo;
import com.soufang.app.vo.shopCar.ToPayListReqVo;
import com.soufang.app.vo.shopCar.ToPayReqVo;
import com.soufang.base.Result;
import com.soufang.base.dto.address.AddressDto;
import com.soufang.base.dto.order.OrderDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shopCar.ShopCarDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.enums.OrderStatusEnum;
import com.soufang.base.enums.OrderTypeEnum;
import com.soufang.base.enums.PageOrderStatusEnum;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.order.OrderSo;
import com.soufang.base.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/21
 * @Description:
 */
@RestController
@RequestMapping("app/order/")
public class AppOrderController extends AppBaseController {

    @Autowired
    AppShopCarFeign appShopCarFeign;

    @Autowired
    AppOrderFeign appOrderFeign;

    @Autowired
    AppAddressFeign appAddressFeign;

    @Autowired
    AppProductManageFeign appProductManageFeign;

    @AppMemberAccess
    @RequestMapping(value = "submitOrder", method = RequestMethod.POST)
    public AppVo submitOrder(@RequestBody ToPayListReqVo toPayReqVoList, HttpServletRequest request) {
        AppVo appVo = new AppVo();
        UserDto userDto = getUserInfo(request);
        OrderDto orderDto = initOrder(toPayReqVoList, userDto);
        Result result = appOrderFeign.createOrder(orderDto);
        // 删除购物车信息
        List<ToPayReqVo> list = toPayReqVoList.getShopCarList();
        List<ShopCarDto> shopCarDtoList = new ArrayList<>();
        List<ShopCarProductDto> shopCarProductDtoList;
        ShopCarDto shopCarDto;
        ShopCarProductDto shopCarProductDto;
        // 封装入参
        for (ToPayReqVo temp : list) {
            shopCarDto = new ShopCarDto();
            shopCarDto.setShopCarId(temp.getShopCarId());
            shopCarDto.setUserId(userDto.getUserId());
            shopCarProductDtoList = new ArrayList<>();
            for (ShopCarProductDto tempProduct : temp.getShopCatProductList()) {
                shopCarProductDto = new ShopCarProductDto();
                shopCarProductDto.setShopCarProductId(tempProduct.getShopCarProductId());
                shopCarProductDtoList.add(shopCarProductDto);
            }
            shopCarDto.setShopCarProductDtoList(shopCarProductDtoList);
            shopCarDtoList.add(shopCarDto);
        }
        // 删除购物车信息
        appShopCarFeign.delShopCar(shopCarDtoList);
        appVo.setSuccess(true);
        return appVo;
    }

    //shopCar封装成Order
    private OrderDto initOrder(ToPayListReqVo toPayReqVoList, UserDto userDto) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userDto.getUserId());
        orderDto.setBuyerName(userDto.getUserName());
        AddressDto addressDto = appAddressFeign.getAddressById(toPayReqVoList.getAddressId());
        orderDto.setTakeName(addressDto.getLinkName());
        orderDto.setTakeAddress(addressDto.getCountry()+addressDto.getDetailAddress());
        orderDto.setTakePhone(addressDto.getLinkPhone());
        orderDto.setOrderType(toPayReqVoList.getOrderType() == 0 ? OrderTypeEnum.OFF_LINE.getValue() : OrderTypeEnum.ON_LINE.getValue());
        orderDto.setOrderRemark(toPayReqVoList.getRemark());
        orderDto.setCreateTime(DateUtils.getToday());
        BigDecimal totalMoney = BigDecimal.ZERO;
        List<OrderShopDto> orderShopDtoList = new ArrayList<>();
        for (ToPayReqVo toPayReqVo : toPayReqVoList.getShopCarList()) {
            ShopCarDto shopCarDto = appShopCarFeign.getShopCarDtoById(toPayReqVo.getShopCarId());
            OrderShopDto orderShopDto = new OrderShopDto();
            orderShopDto.setUserId(userDto.getUserId());
            orderShopDto.setShopId(shopCarDto.getShopId());
            orderShopDto.setShopName(shopCarDto.getShopName());
            BigDecimal sumMoney = BigDecimal.ZERO;
            List<OrderProductDto> orderProductDtos = new ArrayList<>();
            for (ShopCarProductDto shopCarProduct : toPayReqVo.getShopCatProductList()) {
                ShopCarProductDto shopCarProductDto = appShopCarFeign.getShopCarProductDtoById(shopCarProduct.getShopCarProductId());
                OrderProductDto orderProductDto = new OrderProductDto();
                orderProductDto.setProductId(shopCarProductDto.getProductId());
                orderProductDto.setProductName(shopCarProductDto.getProductName());
                orderProductDto.setProductNumber(shopCarProductDto.getProductNumber());
                orderProductDto.setProductUnit(shopCarProductDto.getProductUnit());
                orderProductDto.setProductPrice(appShopCarFeign.selectPrice(shopCarProductDto));
                orderProductDto.setProductColor(shopCarProductDto.getProductColor());
                orderProductDto.setProductSpec(shopCarProductDto.getProductSpec());
                sumMoney = sumMoney.add(orderProductDto.getProductNumber().multiply(orderProductDto.getProductPrice()));
                orderProductDtos.add(orderProductDto);
            }
            orderShopDto.setOrderProducts(orderProductDtos);
            orderShopDto.setSumPrice(sumMoney);
            totalMoney = totalMoney.add(sumMoney);
            orderShopDto.setCreateTime(DateUtils.getToday());
            orderShopDtoList.add(orderShopDto);
        }
        orderDto.setOrderShopDtoList(orderShopDtoList);
        orderDto.setTotalMoney(totalMoney);
        return orderDto;
    }

    @AppMemberAccess
    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public OrderShopListVo toOrderManager(HttpServletRequest request, @RequestBody OrderSo orderSo) {
        OrderShopListVo orderShopListVo = new OrderShopListVo();
        UserDto userInfo = this.getUserInfo(request);
        orderSo.setUserId(userInfo.getUserId());
        orderSo.setOrderStatusList(PageOrderStatusEnum.getOrderStatusList(orderSo.getOrderStatus()));
        PageHelp<OrderShopDto> pageHelp = appOrderFeign.getList(orderSo);
        orderShopListVo.setData(pageHelp.getData());
        orderShopListVo.setPage(orderSo.getPage());
        orderShopListVo.setLimit(orderSo.getLimit());
        return orderShopListVo;
    }

    @AppMemberAccess
    @RequestMapping(value = "orderDetail/{orderShopId}", method = RequestMethod.POST)
    public OrderShopVo toDetail(HttpServletRequest request, ModelMap model, @PathVariable Long orderShopId) {
        OrderShopVo orderShopVo = new OrderShopVo();
        OrderShopDto orderShopDto = appOrderFeign.getDetail(orderShopId);
        for (OrderProductDto orderProductDto : orderShopDto.getOrderProducts()) {
            orderProductDto.setProductImage(orderProductDto.getProductImage());
        }
        orderShopVo.setData(orderShopDto);
        return orderShopVo;
    }

    /**
     * 从产品详情直接提交订单
     *
     * @param reqVo
     * @param request
     * @return
     */
    @AppMemberAccess
    @RequestMapping(value = "directBuy", method = RequestMethod.POST)
    public AppVo directBuy(@RequestBody OrderSubmitReqVo reqVo, HttpServletRequest request) {

        OrderDto orderDto = new OrderDto();
        UserDto userInfo = getUserInfo(request);

        // 封装订单店铺
        orderDto.setUserId(userInfo.getUserId());
        orderDto.setBuyerName(userInfo.getUserName());
        AddressDto addressDto = appAddressFeign.getAddressById(reqVo.getAddressId());
        orderDto.setTakeName(addressDto.getLinkName());
        orderDto.setTakeAddress(addressDto.getDetailAddress());
        orderDto.setTakePhone(addressDto.getLinkPhone());
        orderDto.setOrderType(reqVo.getPayType() == 0 ? OrderTypeEnum.OFF_LINE.getValue() : OrderTypeEnum.ON_LINE.getValue());
        orderDto.setOrderRemark(reqVo.getRemark());
        orderDto.setCreateTime(DateUtils.getToday());
        BigDecimal totalMoney = BigDecimal.ZERO;

        // 查询颜色规格对应的产品信息
        ProductDto so = new ProductDto();
        so.setProductId(reqVo.getProductId());
        so.setProductNumber(reqVo.getProductNumber());
        so.setProductSpec(reqVo.getProductSpecName());
        ProductDto productDto = appProductManageFeign.getProductDetailBySpec_Number(so);

        // 封装订单店铺信息
        List<OrderShopDto> orderShopDtoList = new ArrayList<>();
        OrderShopDto orderShopDto = new OrderShopDto();
        orderShopDto.setUserId(userInfo.getUserId());
        orderShopDto.setShopId(productDto.getShopId());
        orderShopDto.setShopName(productDto.getShopName());
        BigDecimal sumMoney = BigDecimal.ZERO;

        // 封装店铺产品信息
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setProductId(productDto.getProductId());
        orderProductDto.setProductName(productDto.getProductName());
        orderProductDto.setProductNumber(new BigDecimal(reqVo.getProductNumber()));
        orderProductDto.setProductUnit(productDto.getProductUnit());
        orderProductDto.setProductPrice(productDto.getProductSpecDto().getSpecNumber());
        orderProductDto.setProductColor(reqVo.getProductColor());
        orderProductDto.setProductSpec(reqVo.getProductSpecName());
        sumMoney = sumMoney.add(orderProductDto.getProductNumber().multiply(orderProductDto.getProductPrice()));
        orderProductDtos.add(orderProductDto);
        orderShopDto.setOrderProducts(orderProductDtos);
        orderShopDto.setSumPrice(sumMoney);
        totalMoney = totalMoney.add(sumMoney);
        orderShopDto.setCreateTime(DateUtils.getToday());
        orderShopDtoList.add(orderShopDto);

        orderDto.setOrderShopDtoList(orderShopDtoList);
        orderDto.setTotalMoney(totalMoney);

        //生成订单
        Result result = appOrderFeign.createOrder(orderDto);

        AppVo appVo = new AppVo();
        appVo.setSuccess(result.isSuccess());
        appVo.setMessage(result.getMessage());
        return appVo;
    }

    /**
     * 发货 (卖家）
     *
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "sendProduct", method = RequestMethod.POST)
    public AppVo sendProduct(@RequestBody OrderStatusUpdateReqVo reqVo) {
        // 封装数据
        OrderShopDto orderShopDto = new OrderShopDto();
        orderShopDto.setOrderShopId(reqVo.getOrderShopId());
        orderShopDto.setOrderShopStatus(OrderStatusEnum.to_take.getValue());
        orderShopDto.setSendType(reqVo.getSendType());
        orderShopDto.setSendNumber(reqVo.getSendNumber());
        return updateOrderProductStatus(orderShopDto);

    }

    /**
     * 取消订单
     *
     * @param orderShopId
     * @return
     */
    @RequestMapping(value = "cancelOrder/{orderShopId}", method = RequestMethod.POST)
    public AppVo cancelOrder(@PathVariable Long orderShopId) {
        // 封装数据
        OrderShopDto orderShopDto = new OrderShopDto();
        orderShopDto.setOrderShopId(orderShopId);
        orderShopDto.setOrderShopStatus(OrderStatusEnum.cancel.getValue());
        return updateOrderProductStatus(orderShopDto);

    }

    /**
     * 收货
     *
     * @param orderShopId
     * @return
     */
    @RequestMapping(value = "takeProduct", method = RequestMethod.POST)
    public AppVo takeProduct(@PathVariable Long orderShopId) {
        // 封装数据
        OrderShopDto orderShopDto = new OrderShopDto();
        orderShopDto.setOrderShopId(orderShopId);
        orderShopDto.setOrderShopStatus(OrderStatusEnum.access.getValue());
        return updateOrderProductStatus(orderShopDto);
    }

    /**
     * 删除订单
     *
     * @param orderShopId
     * @return
     */
    @RequestMapping(value = "delOrder", method = RequestMethod.POST)
    public AppVo delOrder(@PathVariable Long orderShopId) {
        // 封装数据
        OrderShopDto orderShopDto = new OrderShopDto();
        orderShopDto.setOrderShopId(orderShopId);
        orderShopDto.setOrderShopStatus(OrderStatusEnum.del.getValue());
        return updateOrderProductStatus(orderShopDto);
    }

    /**
     * 更新订单状态
     *
     * @param dto
     * @return
     */
    private AppVo updateOrderProductStatus(OrderShopDto dto) {
        AppVo appVo = new AppVo();
        // 更新订单状态
        Result result = appOrderFeign.updateProductStatus(dto);
        appVo.setSuccess(result.isSuccess());
        appVo.setMessage(result.getMessage());
        return appVo;
    }


}
