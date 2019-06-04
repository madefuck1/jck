package com.soufang.base.dto.dictionary;

import java.util.Date;

public class DictionaryDto {
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
        this.dictKey = dictKey;
    }

    public String getDictKDescription() {
        return dictKDescription;
    }

    public void setDictKDescription(String dictKDescription) {
        this.dictKDescription = dictKDescription;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictVDescription() {
        return dictVDescription;
    }

    public void setDictVDescription(String dictVDescription) {
        this.dictVDescription = dictVDescription;
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

    public DictionaryDto(Long dictId, String dictKey, String dictKDescription, String dictValue, String dictVDescription, Integer dictSort, Date createTime) {
        this.dictId = dictId;
        this.dictKey = dictKey;
        this.dictKDescription = dictKDescription;
        this.dictValue = dictValue;
        this.dictVDescription = dictVDescription;
        this.dictSort = dictSort;
        this.createTime = createTime;
    }

    public DictionaryDto() {
    }
}
