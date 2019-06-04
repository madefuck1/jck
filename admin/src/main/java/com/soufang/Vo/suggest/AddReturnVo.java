package com.soufang.Vo.suggest;

public class AddReturnVo  {
    private String renContent;
    private Long userId;
    private Long sugId;

    public Long getSugId() {
        return sugId;
    }

    public void setSugId(Long sugId) {
        this.sugId = sugId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRenContent() {
        return renContent;
    }

    public void setRenContent(String renContent) {
        this.renContent = renContent;
    }
}
