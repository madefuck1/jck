package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.roleModule.ModuleDto;
import com.soufang.base.dto.roleModule.RoleDto;
import com.soufang.base.dto.roleModule.RoleModuleDto;
import com.soufang.base.dto.roleModule.RoleModuleDto2;
import com.soufang.base.page.PageHelp;
import com.soufang.model.RoleModule;
import com.soufang.service.RoleModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description: 后台角色，权限
 */
@RestController
@RequestMapping("/core/roleModule")
public class RoleModuleController {

    @Autowired
    RoleModuleService roleModuleService;


    @RequestMapping(value = "getModuleList", method = RequestMethod.POST)
    public List<ModuleDto> getModuleList() {
        return roleModuleService.getModuleList();
    }

    /**
     * 新增
     */
    @RequestMapping(value = "saveModule", method = RequestMethod.POST)
    public Result saveModule(@RequestBody ModuleDto moduleDto) {
        Result result = new Result();
        roleModuleService.saveModule(moduleDto);
        result.setSuccess(true);
        result.setMessage("添加成功");
        return result;
    }

    @RequestMapping(value = "getDetail", method = RequestMethod.POST)
    public ModuleDto detail(@RequestBody Long moduleId) {
        return roleModuleService.getModuleDetail(moduleId);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestBody Long moduleId) {
        Result result = new Result();
        roleModuleService.deleteModule(moduleId);
        result.setSuccess(true);
        result.setMessage("删除成功");
        return result;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestBody ModuleDto moduleDto) {
        Result result = new Result();
        if (roleModuleService.updateModule(moduleDto) > 0) {
            result.setSuccess(true);
            result.setMessage("更新成功");
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 角色列表
     */
    @RequestMapping(value = "getRoleList", method = RequestMethod.POST)
    PageHelp<RoleDto> getRoleList() {
        PageHelp<RoleDto> pageHelp = new PageHelp<>();
        List<RoleDto> list = roleModuleService.getRoleList();
        pageHelp.setCount(list.size());
        pageHelp.setData(list);
        return pageHelp;
    }

    @RequestMapping(value = "getRoleDetail", method = RequestMethod.POST)
    RoleModuleDto getDetail(@RequestBody Long roleId) {
        return roleModuleService.getRoleDetail(roleId);
    }


    /**
     * 新增角色
     */
    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    public Result saveRole(@RequestBody RoleDto roleDto) {
        Result result = new Result();
        roleModuleService.saveRole(roleDto);
        result.setSuccess(true);
        result.setMessage("添加成功");
        return result;
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "deleteRoleByKey", method = RequestMethod.POST)
    public Result deleteRoleByKey(@RequestBody Long roleId) {
        Result result = new Result();
        roleModuleService.deleteRoleByKey(roleId);
        result.setSuccess(true);
        result.setMessage("删除成功！");
        return result;
    }

    /**
     * 通过角色获取菜单信息
     *
     * @return
     */
    @RequestMapping(value = "getModuleListByRole", method = RequestMethod.POST)
    public PageHelp<ModuleDto> getModuleListByRole(@RequestBody Long roleId) {
        PageHelp<ModuleDto> pageHelp;
        RoleModule roleModule = new RoleModule();
        roleModule.setRoleId(roleId);
        pageHelp = roleModuleService.getModuleListByRole(roleModule);

        return pageHelp;
    }


    /**
     * 通过key获取角色信息
     *
     * @return
     */
    @RequestMapping(value = "getRoleByKey", method = RequestMethod.POST)
    public RoleDto getRoleByKey(@RequestBody Long roleId) {
        RoleDto roleDto = roleModuleService.getRoleByKey(roleId);
        return roleDto;
    }

    /**
     * 新增角色
     */
    @RequestMapping(value = "addRoleModuleByModuleKey", method = RequestMethod.POST)
    public Result addRoleModuleByModuleKey(@RequestBody RoleModuleDto2 roleModuleDto) {
        Result result = new Result();
        RoleModule roleModule = new RoleModule();
        BeanUtils.copyProperties(roleModuleDto,roleModule);
        roleModuleService.addRoleModuleByModuleKey(roleModule);
        result.setSuccess(true);
        result.setMessage("添加成功");
        return result;
    }

    /**
     * 新增角色
     */
    @RequestMapping(value = "deleteRoleModuleByModuleKey", method = RequestMethod.POST)
    public Result deleteRoleModuleByModuleKey(@RequestBody Long roleModuleId) {
        Result result = new Result();
        roleModuleService.deleteRoleModuleByModuleKey(roleModuleId);
        result.setSuccess(true);
        result.setMessage("添加成功");
        return result;
    }



}
