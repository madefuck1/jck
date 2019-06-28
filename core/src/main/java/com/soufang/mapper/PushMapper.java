package com.soufang.mapper;

import com.soufang.base.dto.push.PushDto;
import com.soufang.base.search.push.PushSo;
import com.soufang.model.Push;

import java.util.List;

public interface PushMapper {
    int deleteByPrimaryKey(Long pushId);

    int insert(Push record);

    int insertSelective(Push record);

    Push selectByPrimaryKey(Long pushId);

    int updateByPrimaryKeySelective(Push record);

    int updateByPrimaryKey(Push record);

    List<PushDto> getList(PushSo pushSo);

    int getCount(PushSo pushSo);
}