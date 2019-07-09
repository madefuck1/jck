package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppPushFeign;
import com.soufang.app.vo.push.PushVo;
import com.soufang.base.Result;
import com.soufang.base.dto.push.PushDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.push.PushSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("app/push/")
public class AppPushController extends AppBaseController{

    @Autowired
    AppPushFeign appPushFeign;

    //查看消息
    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "getUnread",method = RequestMethod.POST)
    public PushVo getUnread(@RequestBody PushSo pushSo, HttpServletRequest request){
        UserDto userInfo = this.getUserInfo(request);
        pushSo.setUserId(userInfo.getUserId());
        pushSo.setPushStatus(1);
        pushSo.setPage(0);
        pushSo.setLimit(0);
        PageHelp<PushDto> pageHelp = appPushFeign.getList(pushSo);
        PushVo vo = new PushVo();
        vo.setCount(pageHelp.getCount());
        vo.setData(pageHelp.getData());
        return vo;
    }
    //获取推送列表
    @ResponseBody
    @AppMemberAccess
    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public PushVo getPurchaseList(@RequestBody PushSo pushSo, HttpServletRequest request){
        UserDto userInfo = this.getUserInfo(request);
        pushSo.setUserId(userInfo.getUserId());
        PageHelp<PushDto> pageHelp = appPushFeign.getList(pushSo);
        PushVo vo = new PushVo();
        vo.setCount(pageHelp.getCount());
        vo.setData(pageHelp.getData());
        //TODO 查看玩所有消息后，将消息状态改为已读
        Result result = appPushFeign.changeIsRead(pushSo);
        vo.setSuccess(result.isSuccess());
        return vo;
    }

}
