package com.soufang.controller;


import com.github.pagehelper.PageHelper;
import com.soufang.base.Result;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.service.ShopService;
import com.soufang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/shop")
public class ShopController {

    @Autowired
    ShopService shopService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public PageHelp<ShopDto> getList(@RequestBody ShopSo shopSo){
        PageHelp<ShopDto> pageHelp = new PageHelp<>();
        PageHelper.startPage(shopSo.getPage(),shopSo.getLimit());
        List<ShopDto> list = shopService.getList(shopSo);
        int count = shopService.getCount(shopSo);
        pageHelp.setData(list);
        pageHelp.setCount(count);
        return pageHelp;
    }

    //通过shopId
    @RequestMapping(value = "getDetail",method = RequestMethod.POST)
    public ShopDto getDetail(@RequestBody Long id){
        return shopService.getById(id);
    }

    @RequestMapping(value = "getUser",method = RequestMethod.POST)
    public UserDto getUser(@RequestBody Long id){
        return userService.getByShopId(id);
    }

    @RequestMapping(value = "reviewYes",method = RequestMethod.POST)
    public Result reviewYes(@RequestBody Long id){
        Result result = new Result();
        if(shopService.reviewYes(id) > 0){
            result.setMessage("审核完成");
            result.setSuccess(true);
        }else {
            result.setMessage("审核未完成");
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "reviewNo",method = RequestMethod.POST)
    Result reviewNo(@RequestBody ShopDto shopDto){
        Result result = new Result();
        if(shopService.reviewNo(shopDto) > 0){
            result.setMessage("审核完成");
            result.setSuccess(true);
        }else {
            result.setMessage("审核未完成");
            result.setSuccess(false);
        }
        return result;
    }

    //通过userId获取店铺信息
    @RequestMapping(value = "getByUserId",method = RequestMethod.POST)
    ShopDto getByUserId(@RequestBody Long userId){
        return shopService.getByUserId(userId);
    }

    @RequestMapping(value = "appGetList",method = RequestMethod.POST)
    public List<ShopDto> appGetList(@RequestBody ShopSo shopSo){
        shopSo.setPage(shopSo.getLimit()*(shopSo.getPage() - 1));
        List<ShopDto> list = shopService.appGetList(shopSo);
        return list;
    }

}
