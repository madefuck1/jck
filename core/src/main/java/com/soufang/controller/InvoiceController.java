package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.invoice.InvoiceDto;
import com.soufang.model.Invoice;
import com.soufang.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public List<InvoiceDto> getList(@RequestBody Long userId){
        return invoiceService.getList(userId);
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    Result saveInvoice(@RequestBody InvoiceDto invoiceDto){
        Result result = new Result();
        if(invoiceService.insertSelective(invoiceDto) > 0){
            result.setMessage("新增发票成功");
            result.setSuccess(true);
        }else {
            result.setMessage("新增发票失败");
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "getInvoice",method = RequestMethod.POST)
    InvoiceDto getInvoice(@RequestBody Long invoiceId){
        return invoiceService.getInvoice(invoiceId);
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    Result update(@RequestBody InvoiceDto invoiceDto){
        Result result = new Result();
        if(invoiceService.updateByPrimaryKeySelective(invoiceDto) > 0){
            result.setMessage("修改成功");
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
            result.setMessage("修改失败");
        }
        return result;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    Result delete(@RequestBody Long invoiceId){
        Result result = new Result();
        if(invoiceService.deleteByPrimaryKey(invoiceId) > 0){
            result.setSuccess(true);
            result.setMessage("删除发票成功");
        }else {
            result.setSuccess(false);
            result.setMessage("删除发票失败");
        }
        return result;
    }

    @RequestMapping(value = "default",method = RequestMethod.POST)
    Result updatedDefaultInvoice(@RequestBody Long invoiceId){
        Result result = new Result();
        //先查询该用户是已否存在默认发票，再将该发票设为默认发票
        List<Invoice> list = invoiceService.selectDefaultInvoice(invoiceId);
        if(list != null && list.size() > 0){
            for (Invoice invoice: list) {
                invoiceService.setNoDefaultByInvoice(invoice);
            }
        }
        //最后在将该发票设为默认地址
        if(invoiceService.setDefault(invoiceId) > 0){
            result.setMessage("设置默认发票成功");
            result.setSuccess(true);
        }else {
            result.setMessage("设置默认发票失败");
            result.setSuccess(false);
        }
        return result;
    }
}
