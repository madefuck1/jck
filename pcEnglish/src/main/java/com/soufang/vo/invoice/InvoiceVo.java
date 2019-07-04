package com.soufang.vo.invoice;

import com.soufang.base.dto.invoice.InvoiceDto;
import com.soufang.vo.BaseVo;

import java.util.List;

public class InvoiceVo extends BaseVo {
    private List<InvoiceDto> data;

    public List<InvoiceDto> getData() {
        return data;
    }

    public void setData(List<InvoiceDto> data) {
        this.data = data;
    }
}
