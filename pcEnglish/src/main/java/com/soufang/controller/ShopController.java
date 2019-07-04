package com.soufang.controller;

import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.storeConstruction.StoreConstructionDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.feign.FavoriteFeign;
import com.soufang.feign.ShopFeign;
import com.soufang.feign.StoreConstructionFeign;
import com.soufang.vo.StoreConstruction.DetailStoreConstructionVo;
import com.soufang.vo.shop.ShopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    FavoriteFeign favoriteFeign;

    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public String getList(ModelMap map, String shopName) {
        map.put("searchProductName", shopName);
        return "shop/shopList";
    }

    @ResponseBody
    @RequestMapping(value = "getShopList", method = RequestMethod.POST)
    public ShopVo getShopList(@RequestBody ShopSo shopSo) {
        if (shopSo.getPage() == 0) {
            shopSo.setPage(1);
        }
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
    public DetailStoreConstructionVo getStoreIndexInfo(HttpServletRequest request, @PathVariable Long shopId) {
        UserDto userDto = getUserInfo(request);
        FavoriteDto favoriteDto = new FavoriteDto();
        favoriteDto.setFavoriteTargetId(shopId);
        favoriteDto.setFavoriteTargetType(2);
        favoriteDto.setUserId(userDto.getUserId());
        boolean isCollect = favoriteFeign.isCollect(favoriteDto);

        DetailStoreConstructionVo vo = new DetailStoreConstructionVo();
        // 店铺信息
        ShopDto shop = shopFeign.getShopInfo(shopId);
        vo.setShopDto(shop);
        // 公司信息
        CompanyDto company = pcUserFeign.getCompany(shop.getUserId());
        vo.setCompanyDto(company);
        StoreConstructionDto storeConstructionDto = storeConstructionFeign.getStoreInfo(shop.getShopId());
        vo.setIsCollection(isCollect);
        vo.setStoreConstructionDto(storeConstructionDto);
        return vo;
    }

    /**
     * 店铺是否被收藏
     *
     * @param request
     * @param shopId
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "isCollection", method = RequestMethod.GET)
    public Boolean isCollection(HttpServletRequest request, Long shopId, Integer type) {
        UserDto userDto = getUserInfo(request);
        FavoriteDto favoriteDto = new FavoriteDto();
        favoriteDto.setFavoriteTargetId(shopId);
        favoriteDto.setFavoriteTargetType(type);
        favoriteDto.setUserId(userDto.getUserId());
        ShopDto shop = shopFeign.getShopInfo(shopId);
        if(!favoriteFeign.isCollect(favoriteDto)){
            favoriteDto.setFavoriteTargetName(shop.getShopName());
            favoriteFeign.addFavorite(favoriteDto);
            return true;
        }  else{
            favoriteFeign.delFavorite(favoriteDto);
            return false;
        }

    }

}
