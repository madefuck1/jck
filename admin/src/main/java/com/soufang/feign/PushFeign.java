package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.push.PushDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface PushFeign {

    @RequestMapping(value = "/core/push/addPush",method = RequestMethod.POST)
    Result addPush(@RequestBody PushDto pushDto);

}
