package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.invoice.InvoiceDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("core")
public interface PcInvoiceFeign {

    @RequestMapping(value = "/core/invoice/getList",method = RequestMethod.POST)
    List<InvoiceDto> getList(@RequestBody Long userId);

    @RequestMapping(value = "/core/invoice/save",method = RequestMethod.POST)
    Result saveInvoice(@RequestBody InvoiceDto invoiceDto);

    @RequestMapping(value = "/core/invoice/update",method = RequestMethod.POST)
    Result update(@RequestBody InvoiceDto invoiceDto);

    @RequestMapping(value = "/core/invoice/delete",method = RequestMethod.POST)
    Result delete(@RequestBody Long invoiceId);

    @RequestMapping(value = "/core/invoice/default",method = RequestMethod.POST)
    Result updatedDefaultInvoice(@RequestBody Long invoiceId);

    @RequestMapping(value = "/core/invoice/getInvoice",method = RequestMethod.POST)
    InvoiceDto getInvoice(@RequestBody Long invoiceId);
}
