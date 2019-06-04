package com.soufang.Vo.roleModule;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description:
 */
public class ModuleReqVo {


    private String moduleName;

    private Long parentId;

    private String moduleLink;

    private Integer moduleSort;

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
}
