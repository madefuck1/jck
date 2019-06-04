package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.address.AddressDto;
import com.soufang.model.Address;
import com.soufang.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public List<AddressDto> getList(@RequestBody Long userId){
        return addressService.getList(userId);
    }

    @RequestMapping(value = "saveAddress",method = RequestMethod.POST)
    Result saveAddress(@RequestBody AddressDto addressDto){
        Result result = new Result();
        if(addressService.insertSelective(addressDto) > 0){
            result.setMessage("新增成功");
            result.setSuccess(true);
        }else {
            result.setMessage("新增失败");
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    Result update(@RequestBody AddressDto addressDto){
        Result result = new Result();
        if(addressService.updateByPrimaryKeySelective(addressDto) > 0){
            result.setSuccess(true);
            result.setMessage("修改成功");
        }else {
            result.setSuccess(false);
            result.setMessage("修改失败");
        }
        return result;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    Result delete(@RequestBody Long addressId){
        Result result = new Result();
        if(addressService.deleteByPrimaryKey(addressId) > 0){
            result.setSuccess(true);
            result.setMessage("删除成功");
        }else {
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    @RequestMapping(value = "default",method = RequestMethod.POST)
    Result updatedDefaultAddress(@RequestBody Long addressId){
        Result result = new Result();
        //先查询该用户是否存在默认地址，再将该地址设为默认地址
        List<Address> list = addressService.selectDefaultAddress(addressId);
        if( list.size() > 0 ){//有默认地址，将默认地址改为非默认地址
            for (Address address : list) {
                addressService.updatedNoDefaultAddress(address);
            }
        }
        //然后将该地址设置为该用户的默认地址
        if(addressService.updatedDefaultAddress(addressId) > 0){
            result.setSuccess(true);
            result.setMessage("设置成功");
        }else {
            result.setSuccess(false);
            result.setMessage("设置失败");
        }
        return result;
    }

    @RequestMapping(value = "getAddress",method = RequestMethod.POST)
    AddressDto getAddressById(@RequestBody Long addressId){
        AddressDto addressDto = addressService.selectByPrimaryKey(addressId);
        return addressDto;
    }

    @RequestMapping(value = "getDefault",method = RequestMethod.POST)
    AddressDto getDefault(@RequestBody Long userId){
        AddressDto addressDto = addressService.getDefaultAddress(userId);
        return addressDto;
    }
}
