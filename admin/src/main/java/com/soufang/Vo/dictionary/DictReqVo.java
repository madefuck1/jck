package com.soufang.Vo.dictionary;

/**
 * @Auther: chen
 * @Date: 2019/3/9
 * @Description: 新增字典时用到
 */

public class DictReqVo {

    private String dictKey;

    private String dictKDescription;

    private String dictValue;

    private String dictVDescription;

    private Integer dictSort;

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
}
