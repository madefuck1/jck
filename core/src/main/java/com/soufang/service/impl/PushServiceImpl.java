package com.soufang.service.impl;

import com.soufang.base.dto.push.PushDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.push.PushSo;
import com.soufang.mapper.PushMapper;
import com.soufang.model.Push;
import com.soufang.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "pushService")
public class PushServiceImpl implements PushService {

    @Autowired
    PushMapper pushMapper;

    @Override
    public PageHelp<PushDto> getList(PushSo pushSo) {
        PageHelp pageHelp = new PageHelp();
        List<PushDto> list = pushMapper.getList(pushSo);
        int count = pushMapper.getCount(pushSo);
        pageHelp.setData(list);
        pageHelp.setCount(count);
        return pageHelp;
    }
}
