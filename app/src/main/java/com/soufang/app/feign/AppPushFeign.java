package com.soufang.app.feign;


import com.soufang.base.Result;
import com.soufang.base.dto.push.PushDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.push.PushSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface AppPushFeign {

    @RequestMapping(value = "/core/push/getList",method = RequestMethod.POST)
    PageHelp<PushDto> getList(@RequestBody PushSo pushSo);

    @RequestMapping(value = "/core/push/changeIsRead",method = RequestMethod.POST)
    Result changeIsRead(@RequestBody PushSo pushSo);
}
