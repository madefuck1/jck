package com.soufang.service;

import com.soufang.base.dto.invoice.InvoiceDto;
import com.soufang.model.Invoice;

import java.util.List;

public interface InvoiceService {

    List<InvoiceDto> getList(Long userId);

    int deleteByPrimaryKey(Long invoiceId);

    int insertSelective(InvoiceDto invoiceDto);

    int updateByPrimaryKeySelective(InvoiceDto invoiceDto);

    int setDefault(Long invoiceId);

    List<Invoice> selectDefaultInvoice(Long invoiceId);

    int setNoDefaultByInvoice(Invoice invoice);

    InvoiceDto getInvoice(Long invoiceId);
}
