package com.soufang.Vo.push;

import com.soufang.Vo.AdminVo;

public class PushVo extends AdminVo {


    private String pushContent;
    private Integer pushType;

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }
}
