package com.soufang.mapper;

import com.soufang.base.dto.roleModule.RoleModuleDto;
import com.soufang.model.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getRoleList();

    RoleModuleDto getRoleDetail(Long roleId);
}