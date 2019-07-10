package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.push.PushDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.push.PushSo;
import com.soufang.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core/push")
public class PushController {
    @Autowired
    PushService pushService;

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    PageHelp<PushDto> getList(@RequestBody PushSo pushSo){
        return pushService.getList(pushSo);
    }

    @RequestMapping(value = "changeIsRead",method = RequestMethod.POST)
    Result changeIsRead(@RequestBody PushSo pushSo){
        return pushService.changeIsRead(pushSo);
    }


    @RequestMapping(value = "addPush",method = RequestMethod.POST)
    Result addPush(@RequestBody PushDto pushDto){
        Result result = pushService.addPush(pushDto);
        return result;
    }
}
