package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.suggest.SuggestDto;
import com.soufang.base.dto.user.UserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface PcUserFeign {

    @RequestMapping(value = "/core/user/detail", method = RequestMethod.POST)
    UserDto getUserById(@RequestBody Long id);

    @RequestMapping(value = "/core/user/login",method = RequestMethod.POST)
    Result login(@RequestBody UserDto userDto);

    @RequestMapping(value = "/core/message/addMessage",method = RequestMethod.POST)
    Result addMessage(@RequestBody MessageDto messageDto);

    @RequestMapping(value = "/core/user/addUser",method = RequestMethod.POST)
    Result register(@RequestBody UserDto userDto);

    @RequestMapping(value = "/core/user/update",method = RequestMethod.POST)
    Result update(@RequestBody UserDto userDto);

    @RequestMapping(value = "/core/company/addCompany",method = RequestMethod.POST)
    Result addCompany(@RequestBody CompanyDto companyDto);

    @RequestMapping(value = "/core/company/getCompany",method = RequestMethod.POST)
    CompanyDto getCompany(@RequestBody Long userId);

    @RequestMapping(value = "/core/company/getCompanyByName" , method = RequestMethod.POST)
    CompanyDto getCompany(@RequestBody String name);

    @RequestMapping(value = "/core/company/updateCompany",method = RequestMethod.POST)
    Result updateCompany(@RequestBody CompanyDto companyDto);

    @RequestMapping(value = "/core/order/updateActualPrice",method = RequestMethod.POST)
    Result updateActualPrice(@RequestBody OrderShopDto orderShopDto);

    @RequestMapping(value = "/core/suggest/addSuggest",method = RequestMethod.POST)
    Result addSuggest(@RequestBody SuggestDto suggestDto);

    @RequestMapping(value = "/core/user/updateUserInfo",method = RequestMethod.POST)
    Result updateUserInfo(@RequestBody UserDto userDto);

    @RequestMapping(value = "/core/user/settle" , method = RequestMethod.POST)
    Result settleShop(@RequestBody UserDto userDto);
}
