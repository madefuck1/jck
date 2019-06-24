package com.soufang.app.feign;


import com.soufang.base.Result;
import com.soufang.base.dto.adminUser.AdminUserDto;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.user.UserSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("core")
public interface AppUserFeign {

    @RequestMapping(value = "/core/user/login",method = RequestMethod.POST)
    Result login(@RequestBody UserDto userDto);

    @RequestMapping(value = "/core/user/addUser",method = RequestMethod.POST)
    Result register(@RequestBody UserDto userDto);

    @RequestMapping(value = "/core/user/getOne",method = RequestMethod.POST)
    UserDto getOne(@RequestBody UserDto userDto);

    @RequestMapping(value = "/core/user/update",method = RequestMethod.POST)
    Result update (@RequestBody UserDto userDto);

    @RequestMapping(value = "/core/company/addCompany",method = RequestMethod.POST)
    Result addCompany(@RequestBody CompanyDto companyDto);

    @RequestMapping(value = "/core/message/addMessage",method = RequestMethod.POST)
    Result addMessage(@RequestBody MessageDto messageDto);

    @RequestMapping(value = "/admin/user/ableUser",method = RequestMethod.POST)
    Result ableUser(@RequestBody Long id);

    @RequestMapping(value = "/core/user/disAbleUser",method = RequestMethod.POST)
    Result disAbleUser(@RequestBody Long id);

    @RequestMapping(value = "/core/user/resetPass",method = RequestMethod.POST)
    Result resetPass(@RequestBody Long id);

    @RequestMapping(value = "/core/user/detail",method = RequestMethod.POST)
    UserDto detail(@RequestBody Long id);

    @RequestMapping(value = "/core/company/companyInfo", method = RequestMethod.POST)
    CompanyDto companyInfo(@RequestBody Long id);

    @RequestMapping(value = "/core/user/getList",method = RequestMethod.POST)
    PageHelp<UserDto> getList(@RequestBody UserSo userSo);

    @RequestMapping(value = "/core/company/updateCompany",method = RequestMethod.POST)
    Result updateCompany(@RequestBody CompanyDto companyDto);
}
