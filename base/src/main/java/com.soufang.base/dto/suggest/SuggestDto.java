package com.soufang.base.dto.suggest;

import com.soufang.base.utils.DateUtils;

import java.util.Date;

public class SuggestDto {
    private Long sugId;
    private Long userId;
    private String sugContent;
    private Date createTime ;
    private String strCreateTime;
    private String renContent;
    private Date returnTime;
    private String strReturnTime;

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

    public String getSugContent() {
        return sugContent;
    }

    public void setSugContent(String sugContent) {
        this.sugContent = sugContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRenContent() {
        return renContent;
    }

    public void setRenContent(String renContent) {
        this.renContent = renContent;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getStrCreateTime() {
        if(createTime == null){
            return  null;
        }
        return DateUtils.date2String(createTime,DateUtils.format1);
    }

    public String getStrReturnTime() {
        if(returnTime == null){
            return  null;
        }
        return DateUtils.date2String(returnTime,DateUtils.format1);
    }

    public SuggestDto() {
    }

}
