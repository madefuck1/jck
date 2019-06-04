package com.soufang.controller;

import com.soufang.base.jiguang.JMessageDto;
import com.soufang.base.jiguang.JMessageUtils;
import com.soufang.config.interceptor.MemberAccess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String toIndex(ModelMap map){
        return "/index";
    }

    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "getMessage", method = RequestMethod.GET)
    public JMessageDto getMessage(HttpServletRequest request , Long shopId){
        JMessageDto dto = JMessageUtils.getSinatureByPc();
        dto.setTarget("egt_"+shopId);
        dto.setFrom("egt_"+getUserInfo(request).getUserId());
        return dto ;
    }

}
