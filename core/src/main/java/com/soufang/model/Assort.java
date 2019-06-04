package com.soufang.model;

public class Assort {
    private Long assortId;

    private String assortName;

    private Long parentId;

    private Integer sort;

    private Integer assortLevel;

    private String key1;

    private String key2;

    private String key3;

    private String key4;

    private String key5;

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
        this.assortName = assortName == null ? null : assortName.trim();
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
        this.key1 = key1 == null ? null : key1.trim();
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2 == null ? null : key2.trim();
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3 == null ? null : key3.trim();
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4 == null ? null : key4.trim();
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5 == null ? null : key5.trim();
    }
}