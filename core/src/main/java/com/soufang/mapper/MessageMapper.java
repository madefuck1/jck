package com.soufang.mapper;

import com.soufang.base.dto.message.MessageDto;
import com.soufang.model.Message;

import java.util.List;

public interface MessageMapper {
    /**
     * 查询短信的list集合
     * @return
     */
    List<MessageDto> getMessageList(Message message);

    /**
     * 查询数量
     * @return
     */
    int getCount();

    /**
     * 添加信息
     * @param message
     * @return
     */
    int addMessage(Message message);
}
