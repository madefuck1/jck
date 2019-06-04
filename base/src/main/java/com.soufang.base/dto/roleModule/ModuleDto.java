package com.soufang.base.dto.roleModule;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description:
 */
public class ModuleDto {

    private Long moduleId;

    private String moduleName;

    private Long parentId;

    private String moduleLink;

    private Integer moduleSort;

    private Integer isExist;

    private Long roleModuleId;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getModuleLink() {
        return moduleLink;
    }

    public void setModuleLink(String moduleLink) {
        this.moduleLink = moduleLink;
    }

    public Integer getModuleSort() {
        return moduleSort;
    }

    public void setModuleSort(Integer moduleSort) {
        this.moduleSort = moduleSort;
    }

    public Integer getIsExist() {
        return isExist;
    }

    public void setIsExist(Integer isExist) {
        this.isExist = isExist;
    }

    public Long getRoleModuleId() {
        return roleModuleId;
    }

    public void setRoleModuleId(Long roleModuleId) {
        this.roleModuleId = roleModuleId;
    }
}
