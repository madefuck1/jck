package com.soufang.mapper;

import com.soufang.model.SysParam;

public interface SysParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    SysParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);

    SysParam selectByCode(String code);
}