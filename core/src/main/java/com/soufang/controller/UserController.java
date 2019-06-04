package com.soufang.controller;

import com.github.pagehelper.PageHelper;
import com.soufang.base.Result;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.user.UserSo;
import com.soufang.base.utils.MD5Utils;
import com.soufang.model.User;
import com.soufang.service.CompanyService;
import com.soufang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    CompanyService companyService;

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public PageHelp<UserDto> getList(@RequestBody UserSo userSo) {
        PageHelp<UserDto> pageHelp = new PageHelp<>();
        PageHelper.startPage(userSo.getPage(),userSo.getLimit());
        List<UserDto> list = userService.getList(userSo);
        int count = userService.getCount(userSo);
        pageHelp.setData(list);
        pageHelp.setCount(count);
        return pageHelp;
    }


    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    public  Result addUser( @RequestBody UserDto userDto){
        Result result =new Result();
        List<UserDto> list = userService.getUsers(userDto);
        if(list != null && list.size() > 0){
            result.setSuccess(false);
            result.setMessage("用户已存在");
        }else {
            Long userId = userService.addUser(userDto);
            result.setSuccess(true);
            result.setMessage(userId+"");
        }
        return result;
    }

    @RequestMapping(value = "ableUser",method = RequestMethod.POST)
    public Result ableUser(@RequestBody Long id){
        Result result = new Result();
        if(userService.ableUser(id) > 0){
            result.setSuccess(true);
            result.setMessage("恢复成功");
        }else {
            result.setSuccess(false);
            result.setMessage("恢复失败");
        }
        return result;
    }
    @RequestMapping(value = "disAbleUser",method = RequestMethod.POST)
    public Result disAbleUser(@RequestBody Long id){
        Result result = new Result();
        if(userService.disAbleUser(id) > 0){
            result.setSuccess(true);
            result.setMessage("禁用成功");
        }else {
            result.setSuccess(false);
            result.setMessage("禁用失败");
        }
        return result;
    }
    @RequestMapping(value = "resetPass",method = RequestMethod.POST)
    public Result resetPass(@RequestBody Long id){
        Result result = new Result();
        if(userService.resetPass(id) > 0){
            result.setSuccess(true);
            result.setMessage("重置秘密成功");
        }else {
            result.setSuccess(false);
            result.setMessage("重置秘密失败");
        }
        return result;
    }

    @RequestMapping(value = "detail",method = RequestMethod.POST)
    UserDto detail(@RequestBody Long id){
        return userService.getById(id);
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    Result login( @RequestBody UserDto userDto){
        UserDto userDto1 = userService.getOneUser(userDto);
        Result result = new Result();
        if(userDto1 != null){
            if(MD5Utils.md5(userDto.getPassWord()).equals(userDto1.getPassWord())){
                result.setSuccess(true);
                result.setMessage(userDto1.getUserId()+"");
            }else {
                result.setSuccess(false);
                result.setMessage("用户密码错误");
            }
        }else {
            result.setSuccess(false);
            result.setMessage("用户不存在");
        }
        return result;
    }

    @RequestMapping(value = "getOne",method = RequestMethod.POST)
    UserDto getOne(@RequestBody UserDto userDto){
        return userService.getOneUser(userDto);
    }


    @RequestMapping(value = "update",method = RequestMethod.POST)
    Result update (@RequestBody UserDto userDto){
        Result result = new Result();
        if(userService.update(userDto) > 0){
            if(userDto.getCompanyDto() != null){
                companyService.addCompany(userDto.getCompanyDto());
            }
            result.setMessage("更新成功");
            result.setSuccess(true);
        }else {
            result.setMessage("更新失败");
            result.setSuccess(false);
        }
        return result;
    }
}
