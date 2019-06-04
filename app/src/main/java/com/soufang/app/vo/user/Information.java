package com.soufang.app.vo.user;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.user.UserDto;

public class Information extends AppVo {
    private UserDto data;

    public UserDto getData() {
        return data;
    }

    public void setData(UserDto data) {
        this.data = data;
    }
}


