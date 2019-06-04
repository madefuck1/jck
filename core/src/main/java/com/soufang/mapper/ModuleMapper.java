package com.soufang.mapper;

import com.soufang.base.dto.roleModule.ModuleDto;
import com.soufang.model.Module;
import com.soufang.model.RoleModule;

import java.util.List;

public interface ModuleMapper {
    int deleteByPrimaryKey(Long moduleId);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Long moduleId);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);

    List<Module> getModuleList();

    List<ModuleDto> getModuleListByRole(RoleModule roleModule);
}