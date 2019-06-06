package com.soufang.app.controller;

import com.soufang.app.feign.AppShopFeign;
import com.soufang.app.vo.shop.DetailVo;
import com.soufang.app.vo.shop.ListShopReqVo;
import com.soufang.app.vo.shop.ListShopVo;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.search.shop.ShopSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/app/shop")
public class AppShopController {

    @Autowired
    AppShopFeign appShopFeign;

    /**
     * 获取店铺列表
     *
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public ListShopVo getList(@RequestBody ListShopReqVo reqVo) {
        //  封装数据
        ShopSo so = new ShopSo();
        so.setShopName(reqVo.getShopName());
        so.setPage(reqVo.getPage());
        so.setLimit(reqVo.getLimit());
        // 查询数据
        List<ShopDto> shopDtos = appShopFeign.appGetList(so);
        ListShopVo vo = new ListShopVo();
        vo.setData(shopDtos);
        return vo;
    }

    /**
     * 获取店铺详情
     *
     * @param shopId
     * @return
     */
    @RequestMapping(value = "getShopDetail/{shopId}", method = RequestMethod.POST)
    public DetailVo getShopDetail(@PathVariable Long shopId) {
        DetailVo vo = new DetailVo();
        ShopDto shopDetail = appShopFeign.getShopDetail(shopId);
        vo.setData(shopDetail);
        return vo;
    }

}
