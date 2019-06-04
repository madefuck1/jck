package com.soufang.controller;

import com.soufang.base.BusinessException;
import com.soufang.base.Result;
import com.soufang.base.dto.shopCar.ShopCarDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.page.PageHelp;
import com.soufang.service.ShopCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "core/shopCar")
public class ShopCarController {

    @Autowired
    ShopCarService shopCarService;

    /**
     * 获取购物车列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "getShopCarList", method = RequestMethod.POST)
    public PageHelp<ShopCarDto> getShopCarList(@RequestBody Long userId) {
        PageHelp<ShopCarDto> pageHelp = shopCarService.getShopCarList(userId);
        return pageHelp;
    }

    /**
     * 添加到购物车
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "addToShopCar", method = RequestMethod.POST)
    public Result addToShopCar(@RequestBody ShopCarDto dto) {
        Result result = new Result();
        try {
            // 添加到购物车
            shopCarService.addToShopCar(dto);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } finally {
            result.setSuccess(true);
            return result;
        }
    }

    /**
     * 删除购物产品信息
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "delShopCar", method = RequestMethod.POST)
    public Result delShopCar(@RequestBody ShopCarDto dto) {
        Result result = new Result();
        try {
            shopCarService.delShopCar(dto);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } finally {
            result.setSuccess(true);
            return result;
        }
    }

    /**
     * 修改购物产品信息
     *
     * @param dto ShopCarProductDto
     * @return
     */
    @RequestMapping(value = "editShopCar", method = RequestMethod.POST)
    public Result editShopCar(@RequestBody ShopCarProductDto dto) {
        Result result = new Result();
        try {
            shopCarService.editShopCar(dto);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } finally {
            result.setSuccess(true);
            return result;
        }
    }

    /**
     * 通过uerId获取购物车数量
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getShopCarCountByUserId", method = RequestMethod.POST)
    public int getShopCarCountByUserId(@RequestBody Long userId) {
        int count = shopCarService.getShopCarCountByUserId(userId);
        return count;
    }

    /**
     * 根据主键查购物车信息
     *
     * @param shopCarId
     * @return
     */
    @RequestMapping(value = "getShopCarDtoById", method = RequestMethod.POST)
    public ShopCarDto getShopCarDtoById(@RequestBody Long shopCarId) {
        return shopCarService.getShopCarDtoById(shopCarId);
    }

    /**
     * 根据购物车产品主键 查购物车产品信息
     *
     * @param shopCarProductId
     * @return
     */
    @RequestMapping(value = "getShopCarProductDtoById", method = RequestMethod.POST)
    public ShopCarProductDto getShopCarProductDtoById(@RequestBody Long shopCarProductId) {
        return shopCarService.getShopCarProductDtoById(shopCarProductId);
    }


    /**
     * 生成订单后删除相应的购物车信息
     *
     * @param dtoList
     * @return
     */
    @RequestMapping(value = "delShopCarProduct", method = RequestMethod.POST)
    public Result delShopCarProduct( @RequestBody List<ShopCarDto> dtoList) {
        Result result =  new Result();
        shopCarService.delShopCarAfterOrderGenerate(dtoList);
        return result;
    }
}
