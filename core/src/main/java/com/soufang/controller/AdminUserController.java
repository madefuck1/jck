package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.adminUser.AdminUserDto;
import com.soufang.base.utils.MD5Utils;
import com.soufang.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: chen
 * @Date: 2019/4/29
 * @Description:
 */
@RestController
@RequestMapping("/core/adminUser")
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result login(@RequestBody AdminUserDto adminUserDto){
        Result result = new Result();
        AdminUserDto temp = adminUserService.getUserByPhone(adminUserDto.getPhone());
        if(temp.getId() != null && temp.getPassword().equals(MD5Utils.md5(adminUserDto.getPassword()))){
            result.setSuccess(true);
            result.setMessage(temp.getId()+"");
        }else {
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "getById",method = RequestMethod.POST)
    public AdminUserDto getById(@RequestBody Long id){
        return adminUserService.getById(id);
    }
}
