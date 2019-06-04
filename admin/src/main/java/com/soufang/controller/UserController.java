package com.soufang.controller;


import com.soufang.Vo.AdminVo;
import com.soufang.Vo.user.UserVo;
import com.soufang.base.Result;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.user.UserSo;
import com.soufang.feign.AdminCompanyFeign;
import com.soufang.feign.AdminUserFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    AdminUserFeign adminUserFeign;
    @Autowired
    AdminCompanyFeign adminCompanyFeign;

    @RequestMapping(value = "toList",method = RequestMethod.GET)
    public String toList(){
        return "/user/list";
    }

    @ResponseBody
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public UserVo getList(Integer limit, Integer page, String realName, String userName, String phone, String email){
        UserVo userVo = new UserVo();
        UserSo userSo = new UserSo();
        if(StringUtils.isNotBlank(realName)){
            userSo.setRealName(realName);
        }
        if(StringUtils.isNotBlank(userName)){
            userSo.setUserName(userName);
        }
        if(StringUtils.isNotBlank(phone)){
            userSo.setPhone(phone);
        }
        if(StringUtils.isNotBlank(email)){
            userSo.setEmail(email);
        }
        userSo.setPage(page);
        userSo.setLimit(limit);
        PageHelp<UserDto> pageHelp = adminUserFeign.getList(userSo);
        userVo.setData(pageHelp.getData());
        userVo.setCount(pageHelp.getCount());
        return userVo;
    }

    @ResponseBody
    @RequestMapping(value = "ableUser/{id}", method = RequestMethod.GET)
    public AdminVo ableUser(@PathVariable Long id){
        Result result = adminUserFeign.ableUser(id);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @ResponseBody
    @RequestMapping(value = "disAbleUser/{id}", method = RequestMethod.GET)
    public AdminVo disAbleUser(@PathVariable Long id){
        Result result = adminUserFeign.disAbleUser(id);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @ResponseBody
    @RequestMapping(value = "resetPass/{id}", method = RequestMethod.GET)
    public AdminVo resetPass(@PathVariable Long id){
        Result result = adminUserFeign.resetPass(id);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }


    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id,ModelMap model){
        UserDto userDto = adminUserFeign.detail(id);
        model.put("userDto",userDto);
        return "/user/details";
    }

    @RequestMapping(value = "companyInfo/{id}", method = RequestMethod.GET)
    public String companyInfo(@PathVariable Long id, ModelMap model){
        CompanyDto companyDto = adminCompanyFeign.companyInfo(id);
        model.put("companyDto",companyDto);
        return "/company/companyInfo";
    }
}
