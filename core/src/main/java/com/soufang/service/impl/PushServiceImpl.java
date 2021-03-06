package com.soufang.service.impl;

import com.soufang.base.Result;
import com.soufang.base.dto.push.PushDto;
import com.soufang.base.jiguang.JPushUtils;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.push.PushSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.PushMapper;
import com.soufang.mapper.UserMapper;
import com.soufang.model.Push;
import com.soufang.service.PushService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //没有未读的消息
        if(count == 0){
            pushSo.setPushStatus(null);
            list = pushMapper.getList(pushSo);
        }
        pageHelp.setData(list);
        pageHelp.setCount(count);
        return pageHelp;
    }

    @Override
    public Result addPush(PushDto pushDto) {
        Push push = new Push();
        BeanUtils.copyProperties(pushDto,push);
        push.setCreateTime(DateUtils.getToday());
        push.setPushStatus(1);
        /*List<User> users = userMapper.getList(new User());
        List<Push> pushes = new ArrayList<>();
        for (int i=0; i<users.size(); i++){
            pushes.add(push);
            pushes.get(i).setUserId(users.get(i).getUserId());
        }*/

        //广播形式发送推送
        JPushUtils.pushNotice("","",pushDto.getPushContent());

        //采用别名的方式推送
        /*JPushUtils.pushNotice("alias","yhkj_"+4,pushDto.getPushContent());
        push.setUserId(4L);*/
        int i = pushMapper.insertSelective(push);
        //int i = pushMapper.insertList(pushes);

        return getResult(i);
    }

    @Override
    public Result changeIsRead(PushSo pushSo) {
        int i = pushMapper.changeIsRead(pushSo);
        return getResult(i);
    }


    Result getResult(int i){
        Result result = new Result();
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
