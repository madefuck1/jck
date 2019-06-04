package com.soufang.app.vo.address;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.address.AddressDto;

import java.util.List;

public class AddressVo extends AppVo {

    private List<AddressDto> data;

    public List<AddressDto> getData() {
        return data;
    }

    public void setData(List<AddressDto> data) {
        this.data = data;
    }
}
