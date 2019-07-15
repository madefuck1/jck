package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.address.AddressDto;
import com.soufang.base.dto.order.OrderDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.shopCar.ShopCarDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.enums.OrderTypeEnum;
import com.soufang.base.utils.DateUtils;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.OrderFeign;
import com.soufang.feign.PcAddressFeign;
import com.soufang.feign.ProductFeign;
import com.soufang.feign.ShopCarFeign;
import com.soufang.vo.BaseVo;
import com.soufang.vo.shopCar.DetailAddReqVo;
import com.soufang.vo.shopCar.DetailUpdateReqVo;
import com.soufang.vo.shopCar.ToPayListReqVo;
import com.soufang.vo.shopCar.ToPayReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/13
 * @Description:
 */
@Controller
@RequestMapping("shopCar")
public class ShopCarController extends BaseController {

    @Autowired
    ShopCarFeign shopCarFeign;

    @Autowired
    PcAddressFeign pcAddressFeign;

    @Autowired
    ProductFeign productFeign;

    @Autowired
    OrderFeign orderFeign;

    @MemberAccess
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public String toLogin(ModelMap map, HttpServletRequest request){
        map.put("shopCarList",shopCarFeign.getShopCarList(getUserInfo(request).getUserId()).getData());
        return "/shopCar/list";
    }

    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseVo delete(HttpServletRequest request, @RequestBody List<Long> shopCarProductIds) {
        BaseVo vo = new BaseVo();
        UserDto userInfo = pcUserFeign.getUserById(getUserInfo(request).getUserId());
        ShopCarDto dto = new ShopCarDto();
        dto.setShopCarProductIds(shopCarProductIds);
        dto.setUserId(userInfo.getUserId());
        // 删除用户选中的购车产品
        Result result = shopCarFeign.delete(dto);
        if (result.isSuccess()) {
            vo.setMessage("删除成功！");
        } else {
            vo.setSuccess(false);
            vo.setMessage("删除失败！");
        }
        return vo;
    }

    @MemberAccess
    @RequestMapping(value = "toPay", method = RequestMethod.POST)
    public String toPay( ModelMap map , @ModelAttribute ToPayListReqVo toPayListReqVo){
        map.put("list",init(toPayListReqVo));
        return "/shopCar/toPay";
    }

    private List<ShopCarDto> init(ToPayListReqVo toPayReqVoList){
        List<ShopCarDto> shopCarDtos = new ArrayList<>();
        for(ToPayReqVo toPayReqVo : toPayReqVoList.getList()){
            ShopCarDto shopCarDto = shopCarFeign.getShopCarDtoById(toPayReqVo.getShopCarId());
            List<ShopCarProductDto> shopCarProductDtos = new ArrayList<>();
            for(Long shopCarProductId : toPayReqVo.getShopCarProductIds()){
                ShopCarProductDto shopCarProductDto = shopCarFeign.getShopCarProductDtoById(shopCarProductId);
                shopCarProductDtos.add(shopCarProductDto);
            }
            shopCarDto.setShopCarProductDtoList(shopCarProductDtos);
            shopCarDtos.add(shopCarDto);
        }
        return shopCarDtos;
    }

    /**
     * 修改购物车数量
     *
     * @param reqVo
     * @return
     */
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "updateNumber", method = RequestMethod.POST)
    public BaseVo updateNumber(@RequestBody DetailUpdateReqVo reqVo) {
        BaseVo vo = new BaseVo();
        // 获取请求用户信息
        ShopCarProductDto shopCarProductDto = new ShopCarProductDto();
        shopCarProductDto.setShopCarProductId(reqVo.getShopCarProductId());
        shopCarProductDto.setProductNumber(reqVo.getProductNum());
        shopCarFeign.update(shopCarProductDto);
        ShopCarProductDto shopCarProduct = shopCarFeign.getShopCarProductDtoById(reqVo.getShopCarProductId());
        BigDecimal price = shopCarFeign.selectPrice(shopCarProduct);
        vo.setMessage(price.toString());
        return vo;
    }

    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "selectPrice", method = RequestMethod.POST)
    public BaseVo updateNumber(@RequestBody ShopCarProductDto shopCarProductDto) {
        BaseVo vo = new BaseVo();
        BigDecimal price = shopCarFeign.selectPrice(shopCarProductDto);
        vo.setMessage(price.toString());
        return vo;
    }


    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "addToCar", method = RequestMethod.POST)
    public BaseVo addToCar(HttpServletRequest request, @RequestBody DetailAddReqVo reqVo){
        BaseVo vo = new BaseVo();
        // 获取请求用户信息
        UserDto userInfo = getUserInfo(request);
        ShopCarDto dto = new ShopCarDto();
        // 封装shopCar
        dto.setShopId(reqVo.getShopId());
        dto.setUserId(userInfo.getUserId());

        ShopCarProductDto shopCarProductDto = new ShopCarProductDto();
        // 封装 shopCarProduct
        shopCarProductDto.setProductId(reqVo.getProductId());
        shopCarProductDto.setProductNumber(reqVo.getProductNum());
        shopCarProductDto.setProductColor(reqVo.getProductColor());
        shopCarProductDto.setProductSpec(reqVo.getProductSpec());
        shopCarProductDto.setProductUnit(reqVo.getProductUnit());
        dto.setShopCarProductDto(shopCarProductDto);

        // 购物车添加产品
        Result result = shopCarFeign.addToShopCar(dto);
        if (result.isSuccess()) {
            vo.setMessage("添加成功！");
        } else {
            vo.setSuccess(false);
            vo.setMessage("添加失败！");
        }
        return vo;
    }


    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public BaseVo editShopCar(@RequestBody DetailUpdateReqVo reqVo) {
        BaseVo vo = new BaseVo();
        // 获取请求用户信息
        ShopCarProductDto shopCarProductDto = new ShopCarProductDto();
        shopCarProductDto.setProductId(reqVo.getProductId());
        shopCarProductDto.setShopCarId(reqVo.getShopCarId());
        shopCarProductDto.setShopCarProductId(reqVo.getShopCarProductId());
        shopCarProductDto.setProductNumber(reqVo.getProductNum());
        shopCarProductDto.setProductColor(reqVo.getProductColor());
        shopCarProductDto.setProductSpec(reqVo.getProductSpec());
        Result result = shopCarFeign.update(shopCarProductDto);
        if (result.isSuccess()) {
            vo.setSuccess(true);
            vo.setMessage("更新成功");
        } else {
            vo.setSuccess(false);
            vo.setMessage("更新失败！");
        }
        return vo;
    }


    /**
     * 订单提交审核  删除购物车，组装Order对象，重定向到订单详情接口
     * @param toPayReqVoList
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "submitOrder", method = RequestMethod.POST)
    public String submitOrder(@ModelAttribute ToPayListReqVo toPayReqVoList , HttpServletRequest request){
        UserDto userDto = getUserInfo(request);
        OrderDto orderDto = initOrder(toPayReqVoList,userDto);
        Result result = orderFeign.createOrder(orderDto);
        // 删除购物车信息
        List<ToPayReqVo> list = toPayReqVoList.getList();
        List<ShopCarDto> shopCarDtoList = new ArrayList<>();
        List<ShopCarProductDto> shopCarProductDtoList;
        ShopCarDto shopCarDto ;
        ShopCarProductDto shopCarProductDto;
        // 封装入参
        for (ToPayReqVo temp : list) {
            shopCarDto = new ShopCarDto();
            shopCarDto.setShopCarId( temp.getShopCarId());
            shopCarDto.setUserId(userDto.getUserId());
            shopCarProductDtoList= new ArrayList<>();
            for (Long tempId : temp.getShopCarProductIds()) {
                shopCarProductDto = new ShopCarProductDto();
                shopCarProductDto.setShopCarProductId(tempId);
                shopCarProductDtoList.add(shopCarProductDto);
            }
            shopCarDto.setShopCarProductDtoList(shopCarProductDtoList);
            shopCarDtoList.add(shopCarDto);
        }
        // 删除购物车信息
        shopCarFeign.delShopCar(shopCarDtoList);

        return "redirect:/orderManager/toOrderManager?page=1&number=1";//重定向
    }

    //shopCar封装成Order
    public OrderDto initOrder(ToPayListReqVo toPayReqVoList,UserDto userDto){
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userDto.getUserId());
        orderDto.setBuyerName(userDto.getUserName());
        AddressDto addressDto = pcAddressFeign.getAddressById(toPayReqVoList.getAddressId());
        orderDto.setTakeName(addressDto.getLinkName());
        orderDto.setTakeAddress(addressDto.getCountry()+addressDto.getDetailAddress());
        orderDto.setTakePhone(addressDto.getLinkPhone());
        orderDto.setOrderType(toPayReqVoList.getPayType()==0? OrderTypeEnum.OFF_LINE.getValue():OrderTypeEnum.ON_LINE.getValue());
        orderDto.setOrderRemark(toPayReqVoList.getRemark());
        orderDto.setCreateTime(DateUtils.getToday());
        BigDecimal totalMoney = BigDecimal.ZERO;
        List<OrderShopDto> orderShopDtoList = new ArrayList<>();
        for(ToPayReqVo toPayReqVo : toPayReqVoList.getList()){
            ShopCarDto shopCarDto = shopCarFeign.getShopCarDtoById(toPayReqVo.getShopCarId());
            OrderShopDto orderShopDto = new OrderShopDto();
            orderShopDto.setUserId(userDto.getUserId());
            orderShopDto.setShopId(shopCarDto.getShopId());
            orderShopDto.setShopName(shopCarDto.getShopName());
            BigDecimal sumMoney = BigDecimal.ZERO;
            List<OrderProductDto> orderProductDtos = new ArrayList<>();
            for(Long shopCarProductId : toPayReqVo.getShopCarProductIds()){
                ShopCarProductDto shopCarProductDto = shopCarFeign.getShopCarProductDtoById(shopCarProductId);
                OrderProductDto orderProductDto = new OrderProductDto();
                orderProductDto.setProductId(shopCarProductDto.getProductId());
                orderProductDto.setProductName(shopCarProductDto.getProductName());
                orderProductDto.setProductNumber(shopCarProductDto.getProductNumber());
                orderProductDto.setProductUnit(shopCarProductDto.getProductUnit());
                orderProductDto.setProductPrice(shopCarFeign.selectPrice(shopCarProductDto));
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
}
