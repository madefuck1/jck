package com.soufang.service.impl;

import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.search.message.MessageSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.MessageMapper;
import com.soufang.model.Message;
import com.soufang.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    /**
     * 查询短信的list集合
     * @return
     */
    @Override
    public List<MessageDto> getMessageList(MessageSo messageSo) {
        Message message = new Message();
        message.setPhone(messageSo.getPhone());
        List<MessageDto> list = messageMapper.getMessageList(message);
        return list;
    }

    /**
     * 查询数量
     * @return
     */
    @Override
    public int getCount() {
        return messageMapper.getCount();
    }

    @Override
    public int addMessage(MessageDto messageDto) {
        Message message = new Message();
        BeanUtils.copyProperties(messageDto,message);
        message.setCreateTime(DateUtils.getToday());
        messageMapper.addMessage(message);
        return 1;
    }
}
