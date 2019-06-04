package com.soufang.Vo.enquiry;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.enquiry.EnquiryDto;

import java.util.List;

public class EnquiryVo extends AdminVo {

    private List<EnquiryDto> data;

    public List<EnquiryDto> getData() {
        return data;
    }

    public void setData(List<EnquiryDto> data) {
        this.data = data;
    }
}
