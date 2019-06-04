package com.soufang.model;

import java.util.Date;

public class Dictionary {
    private Long dictId;

    private String dictKey;

    private String dictKDescription;

    private String dictValue;

    private String dictVDescription;

    private Integer dictSort;

    private Date createTime;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey == null ? null : dictKey.trim();
    }

    public String getDictKDescription() {
        return dictKDescription;
    }

    public void setDictKDescription(String dictKDescription) {
        this.dictKDescription = dictKDescription == null ? null : dictKDescription.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }

    public String getDictVDescription() {
        return dictVDescription;
    }

    public void setDictVDescription(String dictVDescription) {
        this.dictVDescription = dictVDescription == null ? null : dictVDescription.trim();
    }

    public Integer getDictSort() {
        return dictSort;
    }

    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}