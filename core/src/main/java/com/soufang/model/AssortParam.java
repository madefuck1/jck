package com.soufang.model;

public class AssortParam {
    private Long paramId;

    private Long assortId;

    private String paramKey;

    private String paramValue;

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public Long getAssortId() {
        return assortId;
    }

    public void setAssortId(Long assortId) {
        this.assortId = assortId;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey == null ? null : paramKey.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }
}