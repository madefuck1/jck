package com.soufang.controller;


import com.soufang.Vo.AdminVo;
import com.soufang.Vo.shop.ShopVo;
import com.soufang.Vo.shop.UpShopVo;
import com.soufang.base.Result;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.feign.AdminShopFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/shop")
public class ShopController {

    @Autowired
    AdminShopFeign adminShopFeign;

    @RequestMapping(value = "toList",method = RequestMethod.GET)
    public String toList(){
        return "/shop/list";
    }

    @ResponseBody
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public ShopVo getList(Integer limit, Integer page, String shopName, String mainProducts){
        ShopVo shopVo = new ShopVo();
        ShopSo shopSo = new ShopSo();
        if(StringUtils.isNotBlank(shopName)){
            shopSo.setShopName(shopName);
        }
        if(StringUtils.isNotBlank(mainProducts)){
            shopSo.setMainProducts(mainProducts);
        }
        shopSo.setPage(page);
        shopSo.setLimit(limit);
        PageHelp<ShopDto> pageHelp = adminShopFeign.getList(shopSo);
        shopVo.setData(pageHelp.getData());
        shopVo.setCount(pageHelp.getCount());
        return shopVo;
    }

    @RequestMapping(value = "shopDetail/{id}", method = RequestMethod.GET)
    public String getDetail(@PathVariable Long id, ModelMap model){
        ShopDto shopDto = adminShopFeign.getDetail(id);
        UserDto userDto = adminShopFeign.getUser(id);
        model.put("shopDto",shopDto);
        model.put("userDto",userDto);
        return "/shop/detail";
    }

    @RequestMapping(value = "reviewShop/{id}", method = RequestMethod.GET)
    public String reviewShop(@PathVariable Long id, ModelMap model){
        ShopDto shopDto = adminShopFeign.getDetail(id);
        UserDto userDto = adminShopFeign.getUser(id);
        model.put("shopDto",shopDto);
        model.put("userDto",userDto);
        return "/shop/reviewShop";
    }

    @ResponseBody
    @RequestMapping(value = "reviewShop/reviewYes/{id}", method = RequestMethod.GET)
    public AdminVo reviewYes(@PathVariable Long id){
        Result result = adminShopFeign.reviewYes(id);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @ResponseBody
    @RequestMapping(value = "reviewShop/reviewNo", method = RequestMethod.POST)
    public AdminVo reviewNo(@RequestBody UpShopVo upShopVo){
        ShopDto shopDto = new ShopDto();
        shopDto.setRefuseReason(upShopVo.getRefuseReason());
        shopDto.setShopId(upShopVo.getId());
        Result result = adminShopFeign.reviewNo(shopDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @RequestMapping(value = "toAddShop", method = RequestMethod.GET)
    public String toAddNews(){
        return "/shop/addShop";
    }

    @RequestMapping(value = "addShop", method = RequestMethod.POST)
    public AdminVo addShop(@RequestBody UserDto userDto){
        AdminVo vo = new AdminVo();
        adminShopFeign.adminCreateShop(userDto);
        return vo;
    }

}
