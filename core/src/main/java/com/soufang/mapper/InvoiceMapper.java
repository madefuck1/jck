package com.soufang.mapper;

import com.soufang.base.dto.invoice.InvoiceDto;
import com.soufang.model.Invoice;

import java.util.List;

public interface InvoiceMapper {

    List<Invoice> getList(Long userId);

    int deleteByPrimaryKey(Long invoiceId);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    Invoice selectByPrimaryKey(Long invoiceId);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);

    int updateNoDefaultByUserId(Long userId);

    List<Invoice> selectDefaultInvoice(Long invoiceId);

    int setNoDefaultByInvoice(Invoice invoice);

    int setDefault(Long invoiceId);

}