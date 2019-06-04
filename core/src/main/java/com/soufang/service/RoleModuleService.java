package com.soufang.service;

import com.soufang.base.dto.roleModule.ModuleDto;
import com.soufang.base.dto.roleModule.RoleDto;
import com.soufang.base.dto.roleModule.RoleModuleDto;
import com.soufang.base.page.PageHelp;
import com.soufang.model.RoleModule;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description:
 */
public interface RoleModuleService {


    List<RoleDto> getRoleList();

    RoleModuleDto getRoleDetail(Long roleId);

    List<ModuleDto> getModuleList();

    void saveModule(ModuleDto moduleDto);

    ModuleDto getModuleDetail(Long moduleId);

    void deleteModule(Long moduleId);

    int updateModule(ModuleDto moduleDto);

    void saveRole(RoleDto roleDto);

    void deleteRoleByKey(Long roleId);

    PageHelp<ModuleDto> getModuleListByRole(RoleModule roleModule);

    RoleDto getRoleByKey(Long roleId);

    void addRoleModuleByModuleKey(RoleModule roleModule);

    void deleteRoleModuleByModuleKey(Long roleModuleId);
}
