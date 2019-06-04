package com.soufang.base.dto.roleModule;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description:
 */
public class RoleDto {

    private Long roleId;

    private String roleName;

    private String roleIntro;

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
}
