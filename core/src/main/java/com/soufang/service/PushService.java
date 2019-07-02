package com.soufang.service;


import com.soufang.base.Result;
import com.soufang.base.dto.push.PushDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.push.PushSo;


public interface PushService {

    /**
     * 获取消息列表
     * @param pushSo
     * @return
     */
    PageHelp<PushDto> getList(PushSo pushSo);

    Result addPush(PushDto pushDto);
}
