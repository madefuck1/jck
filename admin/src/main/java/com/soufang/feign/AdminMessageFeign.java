package com.soufang.feign;

import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.message.MessageSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface AdminMessageFeign {


    @RequestMapping(value = "/core/message/getMessageList",method = RequestMethod.POST)
    PageHelp<MessageDto> getMessageList(@RequestBody MessageSo messageSo);
}
