package com.soufang.app.vo.address;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.address.AddressDto;

public class DefaultAddress extends AppVo {
    private AddressDto data;

    public AddressDto getData() {
        return data;
    }

    public void setData(AddressDto data) {
        this.data = data;
    }
}
