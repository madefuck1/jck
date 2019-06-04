package com.soufang.mapper;

import com.soufang.model.RoleModule;

public interface RoleModuleMapper {
    int deleteByPrimaryKey(Long roleModuleId);

    int insert(RoleModule record);

    int insertSelective(RoleModule record);

    RoleModule selectByPrimaryKey(Long roleModuleId);

    int updateByPrimaryKeySelective(RoleModule record);

    int updateByPrimaryKey(RoleModule record);
}