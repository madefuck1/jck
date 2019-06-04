package com.soufang.feign;


import com.soufang.base.Result;
import com.soufang.base.dto.adminUser.AdminUserDto;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.user.UserSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@FeignClient("core")
public interface AdminUserFeign {

    @RequestMapping(value = "/core/adminUser/login",method = RequestMethod.POST)
    Result login(@RequestBody AdminUserDto adminUserDto);

    @RequestMapping(value = "/core/adminUser/getById",method = RequestMethod.POST)
    AdminUserDto getById(@RequestBody Long id);

    @RequestMapping(value = "/core/user/getList",method = RequestMethod.POST)
    PageHelp<UserDto> getList(@RequestBody UserSo userSo);

    @RequestMapping(value = "/core/user/ableUser",method = RequestMethod.POST)
    Result ableUser(@RequestBody Long id);

    @RequestMapping(value = "/core/user/disAbleUser",method = RequestMethod.POST)
    Result disAbleUser(@RequestBody Long id);

    @RequestMapping(value = "/core/user/resetPass",method = RequestMethod.POST)
    Result resetPass(@RequestBody Long id);

    @RequestMapping(value = "/core/user/detail",method = RequestMethod.POST)
    UserDto detail(@RequestBody Long id);

    @RequestMapping(value = "/core/user/companyInfo", method = RequestMethod.POST)
    CompanyDto companyInfo(@RequestBody Long id);
}
