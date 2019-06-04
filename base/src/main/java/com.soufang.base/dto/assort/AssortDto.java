package com.soufang.base.dto.assort;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/19
 * @Description:
 */
public class AssortDto {

    private Long assortId;

    private String assortName;

    private Long parentId;

    private Integer sort;

    private Integer assortLevel;

    private Integer hasKey ;

    private String key1;

    private String key2;

    private String key3;

    private String key4;

    private String key5;

    private List<AssortDto> children;

    public Long getAssortId() {
        return assortId;
    }

    public void setAssortId(Long assortId) {
        this.assortId = assortId;
    }

    public String getAssortName() {
        return assortName;
    }

    public void setAssortName(String assortName) {
        this.assortName = assortName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getAssortLevel() {
        return assortLevel;
    }

    public void setAssortLevel(Integer assortLevel) {
        this.assortLevel = assortLevel;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }

    public List<AssortDto> getChildren() {
        return children;
    }

    public void setChildren(List<AssortDto> children) {
        this.children = children;
    }

    public Integer getHasKey() {
        return hasKey;
    }

    public void setHasKey(Integer hasKey) {
        this.hasKey = hasKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssortDto assortDto = (AssortDto) o;

        if (!assortId.equals(assortDto.assortId)) return false;
        return assortName.equals(assortDto.assortName);

    }
}
