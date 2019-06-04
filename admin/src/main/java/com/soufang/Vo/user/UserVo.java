package com.soufang.Vo.user;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.user.UserDto;

import java.util.List;

public class UserVo extends AdminVo {
    private List<UserDto> data;

    public List<UserDto> getData() {
        return data;
    }

    public void setData(List<UserDto> data) {
        this.data = data;
    }
}
