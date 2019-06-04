package com.soufang.mapper;

import com.soufang.model.AssortParam;

public interface AssortParamMapper {
    int deleteByPrimaryKey(Long paramId);

    int insert(AssortParam record);

    int insertSelective(AssortParam record);

    AssortParam selectByPrimaryKey(Long paramId);

    int updateByPrimaryKeySelective(AssortParam record);

    int updateByPrimaryKey(AssortParam record);
}