package com.soufang.vo.address;

import com.soufang.base.dto.address.AddressDto;
import com.soufang.vo.BaseVo;

import java.util.List;

public class AddressVo extends BaseVo {

    private List<AddressDto> data;

    public List<AddressDto> getData() {
        return data;
    }

    public void setData(List<AddressDto> data) {
        this.data = data;
    }
}
