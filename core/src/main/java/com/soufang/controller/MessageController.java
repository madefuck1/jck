package com.soufang.controller;

import com.github.pagehelper.PageHelper;
import com.soufang.base.Result;
import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.message.MessageSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/core/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    /**
     * 得到所有Message信息
     * @param messageSo
     * @return
     */
    @RequestMapping(value = "getMessageList",method = RequestMethod.POST)
    PageHelp<MessageDto> getMessageList(@RequestBody MessageSo messageSo){
        PageHelp<MessageDto> pageHelp = new PageHelp<>();
        PageHelper.startPage(messageSo.getPage(),messageSo.getLimit());
        List<MessageDto> list = messageService.getMessageList(messageSo);
        int count = messageService.getCount();
        pageHelp.setData(list);
        pageHelp.setCount(count);
        return pageHelp;
    }

    @RequestMapping(value = "addMessage",method = RequestMethod.POST)
    public Result addMessage(@RequestBody  MessageDto messageDto){
        Result result = new Result();
        messageDto.setMesType(1);//公司
        messageDto.setMesStatus(0);//发送成功
        messageDto.setCreateTime(DateUtils.getToday());
        if(messageService.addMessage(messageDto) > 0){
            result.setSuccess(true);
            result.setMessage("添加成功");
        }else {
            result.setSuccess(false);
            result.setMessage("添加失败");
        }
        return result;
    }
}
