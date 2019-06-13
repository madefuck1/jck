package com.soufang.controller;

import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.storeConstruction.StoreConstructionDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.ShopFeign;
import com.soufang.feign.StoreConstructionFeign;
import com.soufang.vo.StoreConstruction.DetailStoreConstructionVo;
import com.soufang.vo.shop.ShopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: chen
 * @Date: 2019/6/12
 * @Description:
 */
@RequestMapping("shop")
@Controller
public class ShopController extends BaseController {

    @Autowired
    ShopFeign shopFeign;
    @Autowired
    StoreConstructionFeign storeConstructionFeign;

    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public String getList(ModelMap map, String shopName) {
        map.put("searchProductName", shopName);
        return "shop/shopList";
    }

    @ResponseBody
    @RequestMapping(value = "getShopList", method = RequestMethod.POST)
    public ShopVo getShopList(@RequestBody ShopSo shopSo) {
        PageHelp<ShopDto> pageHelp = shopFeign.appGetList(shopSo);
        ShopVo shopVo = new ShopVo();
        shopVo.setCount(pageHelp.getCount());
        shopVo.setList(pageHelp.getData());
        return shopVo;
    }

    /**
     * 跳转到店铺首页页面
     */
    @RequestMapping(value = "toStoreIndex", method = RequestMethod.GET)
    public String toStoreIndex(ModelMap modelMap, Long shopId) {
        modelMap.put("shopId", shopId);
        return "/shop/store.index";
    }

    /**
     * 获取店铺首页信息
     */
    @ResponseBody
    @RequestMapping(value = "getStoreIndexInfo/{shopId}", method = RequestMethod.POST)
    public DetailStoreConstructionVo getStoreIndexInfo(@PathVariable Long shopId) {
        DetailStoreConstructionVo vo = new DetailStoreConstructionVo();
        // 店铺信息
        ShopDto shop = shopFeign.getShopInfo(shopId);
        vo.setShopDto(shop);
        // 公司信息
        CompanyDto company = pcUserFeign.getCompany(shop.getUserId());
        vo.setCompanyDto(company);
        StoreConstructionDto storeConstructionDto = storeConstructionFeign.getStoreInfo(shop.getShopId());
        vo.setStoreConstructionDto(storeConstructionDto);
        return vo;
    }


}
