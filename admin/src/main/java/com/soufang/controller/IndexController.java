package com.soufang.controller;

import com.soufang.Vo.AdminVo;
import com.soufang.Vo.user.LoginReqVo;
import com.soufang.base.Result;
import com.soufang.base.dto.adminUser.AdminUserDto;
import com.soufang.base.utils.RedisUtils;
import com.soufang.feign.AdminUserFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @Auther: chen
 * @Date: 2019/4/9
 * @Description:
 */
@Controller
public class IndexController {

    @Autowired
    AdminUserFeign adminUserFeign;

    @RequestMapping(value = "index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "toLogin")
    public String toLogin(){
        return "login";
    }


    @ResponseBody
    @RequestMapping(value = "login")
    public AdminVo login(@RequestBody LoginReqVo loginReqVo){
        AdminVo adminVo = new AdminVo();
        AdminUserDto adminUserDto = new AdminUserDto();
        if(StringUtils.isNotBlank(loginReqVo.getPhone())){
            adminUserDto.setPhone(loginReqVo.getPhone());
        }else {
            adminVo.setCode("1");
            adminVo.setMsg("手机号不能为空");
            return adminVo ;
        }
        if(StringUtils.isNotBlank(loginReqVo.getPassword())){
            adminUserDto.setPassword(loginReqVo.getPassword());
        }else {
            adminVo.setCode("1");
            adminVo.setMsg("密码不能为空");
            return adminVo ;
        }
        Result result = adminUserFeign.login(adminUserDto);
        adminVo = new AdminVo(result);
        if(result.isSuccess()){
            String token = UUID.randomUUID().toString().replace("-", "");
            RedisUtils.setString(token, result.getMessage(),1296000);
            adminVo.setMsg(token);
        }
        return adminVo;
    }


}
