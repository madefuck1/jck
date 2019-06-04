package com.soufang.service.impl;

import com.soufang.base.dto.roleModule.ModuleDto;
import com.soufang.base.dto.roleModule.RoleDto;
import com.soufang.base.dto.roleModule.RoleModuleDto;
import com.soufang.base.page.PageHelp;
import com.soufang.mapper.ModuleMapper;
import com.soufang.mapper.RoleMapper;
import com.soufang.mapper.RoleModuleMapper;
import com.soufang.model.Module;
import com.soufang.model.Role;
import com.soufang.model.RoleModule;
import com.soufang.service.RoleModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description:
 */
@Service("roleModuleService")
public class RoleModuleServiceImpl implements RoleModuleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    ModuleMapper moduleMapper;

    @Autowired
    RoleModuleMapper roleModuleMapper;

    @Override
    public List<RoleDto> getRoleList() {
        List<Role> list = roleMapper.getRoleList();
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role role : list) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }


    @Override
    public RoleModuleDto getRoleDetail(Long roleId) {
        return roleMapper.getRoleDetail(roleId);
    }

    @Override
    public List<ModuleDto> getModuleList() {
        List<Module> list = moduleMapper.getModuleList();
        List<ModuleDto> moduleDtos = new ArrayList<>();
        for (Module module : list) {
            ModuleDto moduleDto = new ModuleDto();
            BeanUtils.copyProperties(module, moduleDto);
            moduleDtos.add(moduleDto);
        }
        return moduleDtos;
    }

    @Override
    public void saveModule(ModuleDto moduleDto) {
        Module module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        moduleMapper.insertSelective(module);
    }

    @Override
    public ModuleDto getModuleDetail(Long moduleId) {
        Module module = moduleMapper.selectByPrimaryKey(moduleId);
        ModuleDto moduleDto = new ModuleDto();
        BeanUtils.copyProperties(module, moduleDto);
        return moduleDto;
    }

    @Override
    public void deleteModule(Long moduleId) {
        moduleMapper.deleteByPrimaryKey(moduleId);
    }

    @Override
    public int updateModule(ModuleDto moduleDto) {
        Module module = new Module();
        BeanUtils.copyProperties(moduleDto, module);
        moduleMapper.updateByPrimaryKey(module);
        return 1;
    }

    @Override
    public void deleteRoleByKey(Long roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public void saveRole(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        roleMapper.insert(role);
    }

    @Override
    public PageHelp<ModuleDto> getModuleListByRole(RoleModule roleModule) {
        List<ModuleDto> listByRole = moduleMapper.getModuleListByRole(roleModule);
        PageHelp<ModuleDto> pageHelp = new PageHelp<>();
        pageHelp.setData(listByRole);
        return pageHelp;
    }

    @Override
    public RoleDto getRoleByKey(Long roleId) {
        Role role = roleMapper.selectByPrimaryKey(roleId);
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(role,roleDto);
        return roleDto;
    }

    @Override
    public void addRoleModuleByModuleKey(RoleModule roleModule) {
        roleModuleMapper.insert(roleModule);
    }

    @Override
    public void deleteRoleModuleByModuleKey(Long roleModuleId) {
        roleModuleMapper.deleteByPrimaryKey(roleModuleId);
    }
}
