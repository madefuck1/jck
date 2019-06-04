package com.soufang.app.vo.invoice;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.invoice.InvoiceDto;

import java.util.List;

public class InvoiceVo extends AppVo {
    private List<InvoiceDto> data;

    public List<InvoiceDto> getData() {
        return data;
    }

    public void setData(List<InvoiceDto> data) {
        this.data = data;
    }
}
