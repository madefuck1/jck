package com.soufang.service.impl;

import com.soufang.base.dto.invoice.InvoiceDto;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.InvoiceMapper;
import com.soufang.model.Invoice;
import com.soufang.service.InvoiceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceMapper invoiceMapper;

    @Override
    public List<InvoiceDto> getList(Long userId) {
        List<Invoice> list = invoiceMapper.getList(userId);
        List<InvoiceDto> listDto = new ArrayList<>();
        for (Invoice invoice : list) {
            InvoiceDto invoiceDto = new InvoiceDto();
            BeanUtils.copyProperties(invoice,invoiceDto);
            listDto.add(invoiceDto);
        }
        return listDto;
    }

    //删除
    @Override
    public int deleteByPrimaryKey(Long invoiceId) {
        return invoiceMapper.deleteByPrimaryKey(invoiceId);
    }

    //新增发票
    @Override
    public int insertSelective(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceDto,invoice);
        if(invoice.getCreateTime() == null){
            invoice.setCreateTime(DateUtils.getToday());
        }
        if(invoice.getInvoiceType() == null){
            invoice.setInvoiceType(1);
        }
        if(invoice.getInvoiceDefault() == null){
            invoice.setInvoiceDefault(0);
        }
        //判断是否是默认发票，是的话将原来的默认发表改为非默认的
        if (invoice.getInvoiceDefault() == 1) {
            invoiceMapper.updateNoDefaultByUserId(invoice.getUserId());
        }
        return invoiceMapper.insertSelective(invoice);
    }

    //更新发票
    @Override
    public int updateByPrimaryKeySelective(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(invoiceDto,invoice);
        if(invoice.getInvoiceDefault() == null){//说明用户在编辑个人发票， 而不是设置默认发票
            return invoiceMapper.updateByPrimaryKeySelective(invoice);
        }
        //判断是否是默认发票，是的话将原来的默认发表改为非默认的
        if (invoice.getInvoiceDefault() == 1) {
            invoiceMapper.updateNoDefaultByUserId(invoice.getUserId());
        }else {

        }
        return invoiceMapper.updateByPrimaryKeySelective(invoice);
    }
    //设置为默认发票
    @Override
    public int setDefault(Long invoiceId) {
        return invoiceMapper.setDefault(invoiceId);
    }

    @Override
    public List<Invoice> selectDefaultInvoice(Long invoiceId) {
        return invoiceMapper.selectDefaultInvoice(invoiceId);
    }

    @Override
    public int setNoDefaultByInvoice(Invoice invoice) {
        return invoiceMapper.setNoDefaultByInvoice(invoice);
    }

    @Override
    public InvoiceDto getInvoice(Long invoiceId) {
        Invoice invoice =  invoiceMapper.selectByPrimaryKey(invoiceId);
        InvoiceDto invoiceDto = new InvoiceDto();
        BeanUtils.copyProperties(invoice,invoiceDto);
        return invoiceDto;
    }


}
