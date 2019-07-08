package com.soufang.controller;

import com.soufang.Vo.AdminVo;
import com.soufang.Vo.push.PushVo;
import com.soufang.base.Result;
import com.soufang.base.dto.push.PushDto;
import com.soufang.base.jiguang.JPushUtils;
import com.soufang.base.utils.RedisUtils;
import com.soufang.feign.PushFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "admin/push")
public class PushController {

    @Autowired
    PushFeign pushFeign;

    @RequestMapping(value = "toPush", method = RequestMethod.GET)
    public String toPush() {
        return "/push/addPush";
    }

    @ResponseBody
    @RequestMapping(value = "addPush", method = RequestMethod.POST)
    public AdminVo addPush(HttpServletRequest request, @RequestBody PushVo pushVo){
        /*String token = request.getHeader("Authorization");
        Long userId = Long.valueOf(RedisUtils.getString(token));*/
        PushDto pushDto = new PushDto();
        pushDto.setPushContent(pushVo.getPushContent());
        pushDto.setPushType(pushVo.getPushType());
        /*//发送推送
        JPushUtils.pushNotice("alias","yhkj_",pushVo.getPushContent());*/
        Result result = pushFeign.addPush(pushDto);
        AdminVo vo = new AdminVo(result);
        return vo;
    }


}
