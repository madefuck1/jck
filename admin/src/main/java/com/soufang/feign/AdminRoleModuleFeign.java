package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.roleModule.ModuleDto;
import com.soufang.base.dto.roleModule.RoleDto;
import com.soufang.base.dto.roleModule.RoleModuleDto2;
import com.soufang.base.page.PageHelp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description:
 */
@FeignClient("core")
public interface AdminRoleModuleFeign {

    @RequestMapping(value = "/core/roleModule/getModuleList", method = RequestMethod.POST)
    List<ModuleDto> getModuleList();

    @RequestMapping(value = "/core/roleModule/saveModule", method = RequestMethod.POST)
    Result saveModule(@RequestBody ModuleDto moduleDto);

    @RequestMapping(value = "/core/roleModule/getDetail", method = RequestMethod.POST)
    ModuleDto detail(@RequestBody Long moduleId);

    @RequestMapping(value = "/core/roleModule/delete", method = RequestMethod.POST)
    Result delete(@RequestBody Long moduleId);

    @RequestMapping(value = "/core/roleModule/update", method = RequestMethod.POST)
    Result update(@RequestBody ModuleDto moduleDto);

    @RequestMapping(value = "/core/roleModule/getRoleList", method = RequestMethod.POST)
    PageHelp<RoleDto> getRoleList();

    @RequestMapping(value = "/core/roleModule/addRole", method = RequestMethod.POST)
    Result saveRole(@RequestBody RoleDto roleDto);

    @RequestMapping(value = "/core/roleModule/deleteRoleByKey", method = RequestMethod.POST)
    Result deleteRoleByKey(@RequestBody Long roleId);

    @RequestMapping(value = "/core/roleModule/getModuleListByRole", method = RequestMethod.POST)
    PageHelp<ModuleDto> getModuleListByRole(@RequestBody Long roleId);

    @RequestMapping(value = "/core/roleModule/getRoleByKey", method = RequestMethod.POST)
    RoleDto getRoleByKey(@RequestBody Long roleId);

    @RequestMapping(value = "/core/roleModule/addRoleModuleByModuleKey", method = RequestMethod.POST)
    Result addRoleModuleByModuleKey(@RequestBody RoleModuleDto2 roleModuleDto);

    @RequestMapping(value = "/core/roleModule/deleteRoleModuleByModuleKey", method = RequestMethod.POST)
    Result deleteRoleModuleByModuleKey(@RequestBody Long roleModuleId);
}
