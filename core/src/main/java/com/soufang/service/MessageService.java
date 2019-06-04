package com.soufang.service;

import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.search.message.MessageSo;
import com.soufang.model.Message;

import java.util.List;

public interface MessageService {
    /**
     * 查询短信的list集合
     * @return
     */
    List<MessageDto> getMessageList(MessageSo messageSo);

    /**
     * 查询数量
     * @return
     */
    int getCount();

    /**
     * 添加信息
     * @param messageDto
     * @return
     */
    int addMessage(MessageDto messageDto);
}
