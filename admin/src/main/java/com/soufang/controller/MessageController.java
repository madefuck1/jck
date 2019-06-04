package com.soufang.controller;

import com.soufang.Vo.message.MessageVo;
import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.message.MessageSo;
import com.soufang.feign.AdminMessageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "admin/message")
public class MessageController {
    @Autowired
    AdminMessageFeign adminMessageFeign;

    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String getList(){
        return "/message/list";
    }

    @ResponseBody
    @RequestMapping(value = "getMessageList",method = RequestMethod.GET)
    public MessageVo getMessageList(Integer limit, Integer page,String phone){
        MessageVo messageVo = new MessageVo();
        MessageSo messageSo = new MessageSo();
        messageSo.setPhone(phone);
        messageSo.setPage(page);
        messageSo.setLimit(limit);
        PageHelp<MessageDto> pageHelp = adminMessageFeign.getMessageList(messageSo);

        messageVo.setData(pageHelp.getData());
        messageVo.setCount(pageHelp.getCount());
        return messageVo;
    }
}
