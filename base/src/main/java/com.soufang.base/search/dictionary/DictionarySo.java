package com.soufang.base.search.dictionary;

/**
 * @Auther: chen
 * @Date: 2019/4/9
 * @Description:
 */
public class DictionarySo {

    private String dictVDescription ;
    private String dictKDescription ;
    private Integer page ;
    private Integer limit ;

    public String getDictVDescription() {
        return dictVDescription;
    }

    public void setDictVDescription(String dictVDescription) {
        this.dictVDescription = dictVDescription;
    }

    public String getDictKDescription() {
        return dictKDescription;
    }

    public void setDictKDescription(String dictKDescription) {
        this.dictKDescription = dictKDescription;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
