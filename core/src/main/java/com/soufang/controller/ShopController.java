package com.soufang.controller;


import com.github.pagehelper.PageHelper;
import com.soufang.base.Result;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.base.search.user.UserSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.service.CompanyService;
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
    @Autowired
    CompanyService companyService;

    // 后台创建店铺
    @RequestMapping(value = "admin/createShop")
    public Result adminCreateShop(@RequestBody UserDto userDto){
        Result result = new Result();
        if(checkPhone(userDto.getPhone())){
            result.setMessage("手机号已存在");
            result.setSuccess(false);
            return result;
        }
        if(checkCompany(userDto.getCompanyDto().getCompName())){
            result.setMessage("公司名已存在");
            result.setSuccess(false);
            return result;
        }
        if(checkSHop(userDto.getShopDto().getShopName())){
            result.setMessage("店铺名已存在");
            result.setSuccess(false);
            return result;
        }
        userDto.setPassWord("123456");//初始密码
        Long userId = userService.addUser(userDto);
        CompanyDto companyDto = userDto.getCompanyDto();
        companyDto.setUserId(userId);
        companyDto.setCreateTime(DateUtils.getToday());
        companyService.addCompany(companyDto);
        ShopDto shopDto = userDto.getShopDto();
        shopDto.setCreateTime(DateUtils.getToday());
        shopDto.setUserId(userId);
        shopDto.setShopLevel(1);
        shopDto.setShopStatus(0);
        shopService.addShop(shopDto);
        result.setSuccess(true);
        return result;
    }

    private boolean checkPhone(String phone){
        UserSo userSo = new UserSo();
        userSo.setPhone(phone);
        List<UserDto> list = userService.getList(userSo);
        if(list != null && list.size() > 0){
            return true;
        }else {
            return false ;
        }
    }

    private boolean checkCompany(String companyName){
        CompanyDto companyDto = companyService.selectByCompanyName(companyName);
        if(companyDto !=null){
            return true;
        }else {
            return false;
        }
    }

    private boolean checkSHop(String shopName){
        ShopSo shopSo = new ShopSo();
        shopSo.setShopName(shopName);
        List<ShopDto> list = shopService.getList(shopSo);
        if(list != null && list.size()>0){
            return true;
        }else {
            return false;
        }
    }

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
    public PageHelp<ShopDto> appGetList(@RequestBody ShopSo shopSo){
        shopSo.setPage(shopSo.getLimit()*(shopSo.getPage() - 1));
        PageHelp<ShopDto> pageHelp = shopService.appGetList(shopSo);
        return pageHelp;
    }

    @RequestMapping(value = "updateShop",method = RequestMethod.POST)
    public Result updateShop(@RequestBody ShopDto shopDto){
        Result result = new Result();
        shopService.updateShop(shopDto);
        return result;
    }

    @RequestMapping(value = "getHotShop",method = RequestMethod.POST)
    public List<ShopDto> getHotShop(){
        List<ShopDto> shopDtos = shopService.getHotShop();
        return shopDtos;
    }
}
