package com.soufang.Vo.suggest;

public class SugReqVo {
    private Long userId;
    private String sugContent;
    private String renContent;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSugContent() {
        return sugContent;
    }

    public void setSugContent(String sugContent) {
        this.sugContent = sugContent;
    }

    public String getRenContent() {
        return renContent;
    }

    public void setRenContent(String renContent) {
        this.renContent = renContent;
    }
}
