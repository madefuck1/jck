package com.soufang.Vo.message;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.message.MessageDto;

import java.util.List;

public class MessageVo extends AdminVo {

    List<MessageDto> data;

    public List<MessageDto> getData() {
        return data;
    }

    public void setData(List<MessageDto> data) {
        this.data = data;
    }
}
