package com.soufang.controller;

import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.ShopFeign;
import com.soufang.vo.shop.ShopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "getList" , method = RequestMethod.GET)
    public String getList(ModelMap map , String shopName){
        map.put("searchProductName",shopName);
        return "shop/shopList";
    }

    @ResponseBody
    @RequestMapping(value = "getShopList" , method = RequestMethod.POST)
    public ShopVo getShopList(@RequestBody ShopSo shopSo){
        PageHelp<ShopDto> pageHelp = shopFeign.appGetList(shopSo);
        ShopVo shopVo = new ShopVo();
        shopVo.setCount(pageHelp.getCount());
        shopVo.setList(pageHelp.getData());
        return shopVo;
    }

}
