package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppAddressFeign;
import com.soufang.app.vo.address.AddressAddVo;
import com.soufang.app.vo.address.AddressVo;
import com.soufang.app.vo.address.DefaultAddress;
import com.soufang.app.vo.address.UpdateAddressVo;
import com.soufang.base.Result;
import com.soufang.base.dto.address.AddressDto;
import com.soufang.base.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("app/address/")
public class AppAddressController extends AppBaseController{

    @Autowired
    AppAddressFeign appAddressFeign;

    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public AddressVo getList(HttpServletRequest request){
        UserDto userInfo = this.getUserInfo(request);
        AddressVo addressVo = new AddressVo();
        List<AddressDto> listDto = appAddressFeign.getList(userInfo.getUserId());
        if(listDto != null && listDto.size() > 0){
            addressVo.setData(listDto);
            addressVo.setMessage("查询成功");
            addressVo.setSuccess(true);
        }else {
            addressVo.setData(null);
            addressVo.setMessage("对不起！您没有相关的地址信息");
            addressVo.setSuccess(false);
        }
        return addressVo;
    }

    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "saveAddress",method = RequestMethod.POST)
    public AddressVo saveAddress(HttpServletRequest request , @RequestBody AddressAddVo addressAddVo){
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        AddressDto addressDto = new AddressDto();
        addressDto.setUserId(userInfo.getUserId());//设置用户id
        //addressDto.setUserId(1L);//设置用户id
        addressDto.setCountry(addressAddVo.getCountry());
        addressDto.setDetailAddress(addressAddVo.getDetailAddress());
        addressDto.setLinkPhone(addressAddVo.getLinkPhone());
        addressDto.setLinkName(addressAddVo.getLinkName());
        addressDto.setIsDefaultAddress(addressAddVo.getIsDefaultAddress());
        Result result = appAddressFeign.saveAddress(addressDto);
        return judge(result);
    }

    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public AddressVo updateAddress(HttpServletRequest request , @RequestBody UpdateAddressVo updateAddressVo){
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(updateAddressVo.getAddressId());//要更新的地址id
        addressDto.setUserId(userInfo.getUserId());//设置用户id
        addressDto.setCountry(updateAddressVo.getCountry());
        addressDto.setDetailAddress(updateAddressVo.getDetailAddress());
        addressDto.setLinkName(updateAddressVo.getLinkName());
        addressDto.setIsDefaultAddress(updateAddressVo.getIsDefaultAddress());
        addressDto.setLinkPhone(updateAddressVo.getLinkPhone());
        Result result = appAddressFeign.update(addressDto);
        return judge(result);
    }

    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "delete/{addressId}",method = RequestMethod.POST)
    public AddressVo delete(@PathVariable Long addressId ){
        Result result = appAddressFeign.delete(addressId);
        return judge(result);
    }

    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "default/{addressId}",method = RequestMethod.POST)
    public AddressVo updatedDefaultAddress(@PathVariable Long addressId){
        Result result = appAddressFeign.updatedDefaultAddress(addressId);
        return judge(result);
    }

    private AddressVo judge(Result result){
        AddressVo addressVo = new AddressVo();
        if(result.isSuccess()){
            addressVo.setMessage(result.getMessage());
            addressVo.setSuccess(result.isSuccess());
        }else {
            addressVo.setSuccess(result.isSuccess());
            addressVo.setMessage(result.getMessage());
        }
        return addressVo;
    }

    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "getDefault",method = RequestMethod.POST)
    public DefaultAddress getDefault(HttpServletRequest request){
        UserDto userInfo = this.getUserInfo(request);
        DefaultAddress vo = new DefaultAddress();
        AddressDto addressDto = appAddressFeign.getDefault(userInfo.getUserId());
        if(addressDto != null){
            vo.setSuccess(true);
            vo.setMessage("获取成功");
            vo.setData(addressDto);
        }else {
            vo.setSuccess(false);
            vo.setMessage("您用户没有相关地址信息");
            vo.setData(null);
        }
        return vo;
    }
}
