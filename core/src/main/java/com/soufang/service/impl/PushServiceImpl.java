package com.soufang.service.impl;

import com.soufang.base.Result;
import com.soufang.base.dto.push.PushDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.push.PushSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.PushMapper;
import com.soufang.mapper.UserMapper;
import com.soufang.model.Push;
import com.soufang.model.User;
import com.soufang.service.PushService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service(value = "pushService")
public class PushServiceImpl implements PushService {

    @Autowired
    PushMapper pushMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public PageHelp<PushDto> getList(PushSo pushSo) {
        PageHelp pageHelp = new PageHelp();
        if(pushSo.getPage() != 0){
            pushSo.setPage((pushSo.getPage()-1)*pushSo.getLimit());
        }
        List<PushDto> list = pushMapper.getList(pushSo);
        int count = pushMapper.getCount(pushSo);
        pageHelp.setData(list);
        pageHelp.setCount(count);
        return pageHelp;
    }

    @Override
    public Result addPush(PushDto pushDto) {
        Result result = new Result();
        Push push = new Push();
        BeanUtils.copyProperties(pushDto,push);
        push.setCreateTime(DateUtils.getToday());
        push.setPushStatus(1);
        List<User> users = userMapper.getList(new User());
        List<Push> pushes = new ArrayList<>();
        for (int i=0; i<users.size(); i++){
            pushes.add(push);
            pushes.get(i).setUserId(users.get(i).getUserId());
        }
        int i = pushMapper.insertList(pushes);
        if(i > 0){
            result.setMessage("推送成功");
            result.setSuccess(true);
        }else {
            result.setMessage("推送失败");
            result.setSuccess(false);
        }
        return result;
    }
}
