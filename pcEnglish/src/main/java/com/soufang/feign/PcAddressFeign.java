package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.address.AddressDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("core")
public interface PcAddressFeign {

    @RequestMapping(value = "/core/address/getList",method = RequestMethod.POST)
    List<AddressDto> getList(@RequestBody Long userId);

    @RequestMapping(value = "/core/address/saveAddress",method = RequestMethod.POST)
    Result saveAddress(@RequestBody AddressDto addressDto);

    @RequestMapping(value = "/core/address/update",method = RequestMethod.POST)
    Result update(@RequestBody AddressDto addressDto);

    @RequestMapping(value = "/core/address/delete",method = RequestMethod.POST)
    Result delete(@RequestBody Long addressId);

    @RequestMapping(value = "/core/address/default",method = RequestMethod.POST)
    Result updatedDefaultAddress(@RequestBody Long addressId);

    @RequestMapping(value = "/core/address/getAddress",method = RequestMethod.POST)
    AddressDto getAddressById(@RequestBody Long addressId);
}
