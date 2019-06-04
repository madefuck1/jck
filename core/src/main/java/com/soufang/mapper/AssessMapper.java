package com.soufang.mapper;

import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.search.assess.AssessSo;
import com.soufang.model.Assess;

import java.util.List;

public interface AssessMapper {
    int deleteByPrimaryKey(Long assessId);

    int insert(Assess record);

    int insertSelective(Assess record);

    Assess selectByPrimaryKey(Long assessId);

    int updateByPrimaryKeySelective(Assess record);

    int updateByPrimaryKey(Assess record);

    List<AssessDto> getList(AssessSo assessSo);

    int getCount(AssessSo assessSo);
}