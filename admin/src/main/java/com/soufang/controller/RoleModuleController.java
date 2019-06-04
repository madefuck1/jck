package com.soufang.controller;

import com.soufang.Vo.AdminVo;
import com.soufang.Vo.roleModule.ModuleReqVo;
import com.soufang.Vo.roleModule.ModuleVo;
import com.soufang.Vo.roleModule.RoleReqVo;
import com.soufang.Vo.roleModule.RoleVo;
import com.soufang.base.Result;
import com.soufang.base.dto.roleModule.ModuleDto;
import com.soufang.base.dto.roleModule.RoleDto;
import com.soufang.base.dto.roleModule.RoleModuleDto2;
import com.soufang.base.page.PageHelp;
import com.soufang.feign.AdminRoleModuleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description:
 */
@Controller
@RequestMapping(value = "admin/roleModule")
public class RoleModuleController {


    @Autowired
    AdminRoleModuleFeign adminRoleModuleFeign;

    /**
     * 菜单栏接口
     */


    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String toList() {
        return "/roleModule/list";
    }

    @ResponseBody
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public ModuleVo getList() {
        ModuleVo vo = new ModuleVo();
        vo.setData(adminRoleModuleFeign.getModuleList());
        vo.setCode("0");
        return vo;
    }

    @RequestMapping(value = "newModule", method = RequestMethod.GET)
    public String newAssort(ModelMap model) {
        model.put("list", adminRoleModuleFeign.getModuleList());
        return "/roleModule/new";
    }

    @ResponseBody
    @RequestMapping(value = "addModule", method = RequestMethod.POST)
    public AdminVo addAssort(@RequestBody ModuleReqVo moduleReqVo) {
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setModuleName(moduleReqVo.getModuleName());
        moduleDto.setParentId(moduleReqVo.getParentId());
        moduleDto.setModuleLink(moduleReqVo.getModuleLink());
        moduleDto.setModuleSort(moduleReqVo.getModuleSort());
        Result result = adminRoleModuleFeign.saveModule(moduleDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }


    /**
     * 角色页面跳转
     *
     * @return
     */
    @RequestMapping(value = "toRoleList", method = RequestMethod.GET)
    public String toRoleList() {
        return "/roleModule/roleList";
    }

    /**
     * 角色列表
     */
    @ResponseBody
    @RequestMapping(value = "getRoleList", method = RequestMethod.GET)
    public RoleVo getRoleList() {
        RoleVo roleVo = new RoleVo();
        PageHelp<RoleDto> roleList = adminRoleModuleFeign.getRoleList();
        roleVo.setData(roleList.getData());
        return roleVo;
    }

    /**
     * 新增角色页面
     *
     * @return
     */
    @RequestMapping(value = "newRole", method = RequestMethod.GET)
    public String newRole() {
        return "/roleModule/newRole";
    }

    /**
     * 添加角色
     *
     * @param reqVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    public AdminVo addRole(@RequestBody RoleReqVo reqVo) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName(reqVo.getRoleName());
        roleDto.setRoleIntro(reqVo.getRoleIntro());
        Result result = adminRoleModuleFeign.saveRole(roleDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteRoleByKey", method = RequestMethod.GET)
    public AdminVo deleteRoleByKey(@RequestParam Long roleId) {
        Result result = adminRoleModuleFeign.deleteRoleByKey(roleId);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    /**
     * 关联菜单
     *
     * @return
     */
    @RequestMapping(value = "newAddModule/{roleId}", method = RequestMethod.GET)
    public String newAddModule(@PathVariable Long roleId, ModelMap model) {
        RoleDto roleDto = adminRoleModuleFeign.getRoleByKey(roleId);
        model.put("dto", roleDto);
        return "/roleModule/addModule";
    }

    /**
     * 通过角色获取菜单列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getModuleListByRole/{roleId}", method = RequestMethod.GET)
    public ModuleVo getModuleListByRole(@PathVariable Long roleId) {
        ModuleVo moduleVo = new ModuleVo();
        PageHelp<ModuleDto> roleList = adminRoleModuleFeign.getModuleListByRole(roleId);
        moduleVo.setData(roleList.getData());
        return moduleVo;
    }

    /**
     * 添加角色关联菜单
     */
    @ResponseBody
    @RequestMapping(value = "addRoleModuleByModuleKey", method = RequestMethod.GET)
    public AdminVo addRoleModuleByModuleKey(@RequestParam Long moduleId, @RequestParam Long roleId) {
        RoleModuleDto2 roleModuleDto = new RoleModuleDto2();
        roleModuleDto.setRoleId(roleId);
        roleModuleDto.setModuleId(moduleId);
        Result result = adminRoleModuleFeign.addRoleModuleByModuleKey(roleModuleDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    /**
     * 删除角色关联菜单
     */
    @ResponseBody
    @RequestMapping(value = "deleteRoleModuleByModuleKey", method = RequestMethod.GET)
    public AdminVo deleteRoleModuleByModuleKey(@RequestParam Long roleModuleId) {
        Result result = adminRoleModuleFeign.deleteRoleModuleByModuleKey(roleModuleId);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

}
