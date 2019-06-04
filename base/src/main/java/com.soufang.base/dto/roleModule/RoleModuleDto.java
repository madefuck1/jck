package com.soufang.base.dto.roleModule;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description:
 */
public class RoleModuleDto {

    private Long roleId;

    private String roleName;

    private String roleIntro;

    private List<ModuleDto> moduleDtos ;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleIntro() {
        return roleIntro;
    }

    public void setRoleIntro(String roleIntro) {
        this.roleIntro = roleIntro;
    }

    public List<ModuleDto> getModuleDtos() {
        return moduleDtos;
    }

    public void setModuleDtos(List<ModuleDto> moduleDtos) {
        this.moduleDtos = moduleDtos;
    }
}
