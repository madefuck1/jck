package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppProductManageFeign;
import com.soufang.app.feign.AppShopCarFeign;
import com.soufang.app.vo.AppVo;
import com.soufang.app.vo.shopCar.*;
import com.soufang.base.Result;
import com.soufang.base.dto.shopCar.ShopCarDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/app/shopCar")
public class AppShopCarManageController extends AppBaseController {

    @Autowired
    AppShopCarFeign appShopCarFeign;

    @Autowired
    AppProductManageFeign appProductManageFeign;

    /**
     * 购物车列表接口
     *
     * @return
     */
    @AppMemberAccess
    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public ListShopCarVo getList(HttpServletRequest request) {
        ListShopCarVo vo = new ListShopCarVo();
        // 获取请求用户信息
        UserDto userInfo = getUserInfo(request);
        // 查询用户购物车列表信息
        PageHelp<ShopCarDto> pageHelp = appShopCarFeign.getShopCarList(userInfo.getUserId());
        vo.setMessage("查询成功！");
        vo.setData(pageHelp.getData());
        return vo;
    }

    /**
     * 删除购车产品
     *
     * @param request
     * @param reqVo
     * @return
     */
    @AppMemberAccess
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ListShopCarVo delete(HttpServletRequest request, @RequestBody DetailDelReqVo reqVo) {
        ListShopCarVo vo = new ListShopCarVo();
        // 获取请求用户信息
        UserDto userInfo = getUserInfo(request);
        // 封装
        ShopCarDto dto = new ShopCarDto();
        dto.setShopCarProductIds(reqVo.getShopCarProductIds());
        dto.setUserId(userInfo.getUserId());
        // 删除用户选中的购车产品
        Result result = appShopCarFeign.delete(dto);
        if (result.isSuccess()) {
            PageHelp<ShopCarDto> pageHelp = appShopCarFeign.getShopCarList(userInfo.getUserId());
            vo.setData(pageHelp.getData());
            vo.setMessage("删除成功！");
        } else {
            vo.setSuccess(false);
            vo.setMessage("删除失败！");
        }
        return vo;
    }

    /**
     * 购物车添加产品
     *
     * @param request
     * @param reqVo
     * @return
     */
    @AppMemberAccess
    @RequestMapping(value = "addToShopCar", method = RequestMethod.POST)
    public AppVo addToShopCar(HttpServletRequest request, @RequestBody DetailAddReqVo reqVo) {
        AppVo vo = new AppVo();
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
        Result result = appShopCarFeign.addToShopCar(dto);
        if (result.isSuccess()) {
            vo.setMessage("添加成功！");
        } else {
            vo.setSuccess(false);
            vo.setMessage("添加失败！");
        }
        return vo;
    }


    /**
     * 获取购物车总数量
     *
     * @param request
     * @return
     */
    @AppMemberAccess
    @RequestMapping(value = "getShopCarCountByUserId", method = RequestMethod.POST)
    public DetailCountVo getShopCarCountByUserId(HttpServletRequest request) {
        DetailCountVo vo = new DetailCountVo();
        // 获取请求用户信息
        UserDto userInfo = getUserInfo(request);
        // 购物车添加产品
        int result = appShopCarFeign.getShopCarCountByUserId(userInfo.getUserId());
        vo.setData(result);
        vo.setMessage("查询成功！");
        return vo;
    }


    /**
     * 修改购物车规格、颜色、数量
     *
     * @param reqVo
     * @return
     */
    @AppMemberAccess
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public DetailVo editShopCar(@RequestBody DetailUpdateReqVo reqVo) {
        DetailVo vo = new DetailVo();
        // 获取请求用户信息
        ShopCarProductDto shopCarProductDto = new ShopCarProductDto();
        shopCarProductDto.setShopCarProductId(reqVo.getShopCarProductId());
        shopCarProductDto.setProductId(reqVo.getProductId());
        shopCarProductDto.setProductNumber(reqVo.getProductNum());
        shopCarProductDto.setProductColor(reqVo.getProductColor());
        shopCarProductDto.setProductSpec(reqVo.getProductSpec());
        shopCarProductDto.setShopCarId(reqVo.getShopCarId());
        Result result = appShopCarFeign.update(shopCarProductDto);
        if (result.isSuccess()) {
            ShopCarDto shopCarDto = appShopCarFeign.getShopCarDtoById(reqVo.getShopCarId());
            vo.setSuccess(true);
            vo.setMessage("更新成功");
            vo.setData(shopCarDto);
        } else {
            vo.setData(null);
            vo.setSuccess(false);
            vo.setMessage("更新失败！");
        }
        return vo;
    }


    /**
     * 修改购物车数量
     *
     * @param reqVo
     * @return
     */
    @AppMemberAccess
    @RequestMapping(value = "updateNumber", method = RequestMethod.POST)
    public AppVo updateNumber(@RequestBody DetailUpdateReqVo reqVo) {
        AppVo vo = new AppVo();
        // 获取请求用户信息
        ShopCarProductDto shopCarProductDto = new ShopCarProductDto();
        shopCarProductDto.setShopCarProductId(reqVo.getShopCarProductId());
        shopCarProductDto.setProductNumber(reqVo.getProductNum());
        appShopCarFeign.update(shopCarProductDto);
        vo.setMessage("更新成功！");
        return vo;
    }

    @AppMemberAccess
    @RequestMapping(value = "selectPrice", method = RequestMethod.POST)
    public AppVo updateNumber(@RequestBody ShopCarProductDto shopCarProductDto) {
        AppVo vo = new AppVo();
        BigDecimal price = appProductManageFeign.selectPrice(shopCarProductDto);
        vo.setMessage(price.toString());
        return vo;
    }

    @AppMemberAccess
    @RequestMapping(value = "toPay", method = RequestMethod.POST)
    public List<ShopCarDto> toPay(@RequestBody ToPayListReqVo toPayListReqVo){
        List<ShopCarDto> shopCarDtos = new ArrayList<>();
        for(ToPayReqVo toPayReqVo : toPayListReqVo.getShopCarList()){
            ShopCarDto shopCarDto = appShopCarFeign.getShopCarDtoById(toPayReqVo.getShopCarId());
            List<ShopCarProductDto> shopCarProductDtos = new ArrayList<>();
            for(Long id : toPayReqVo.getShopCarProductIds()){
                ShopCarProductDto shopCarProductDto = appShopCarFeign.getShopCarProductDtoById(id);
                shopCarProductDtos.add(shopCarProductDto);
            }
            shopCarDto.setShopCarProductDtoList(shopCarProductDtos);
            shopCarDtos.add(shopCarDto);
        }
        return shopCarDtos;
    }


}
