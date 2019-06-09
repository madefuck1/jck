package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.search.purchase.PurchaseSo;
import com.soufang.model.Enquiry;
import com.soufang.service.EnquiryService;
import com.soufang.service.PurchaseService;
import com.soufang.service.SysParamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;
    @Autowired
    SysParamService sysParamService;
    @Autowired
    EnquiryService enquiryService;

    /**
     * 查询报价信息
     * @param enquiryNumber
     * @return
     */
    @RequestMapping(value = "getPurchaseListByEnqunum",method = RequestMethod.POST)
    public List<PurchaseDto>  getPurchaseListByEnqunum(@RequestBody String enquiryNumber){
        List<PurchaseDto>  purchaseDtos =purchaseService.getPurchaseListByEnqunum(enquiryNumber);
        return purchaseDtos;
    }

    /**
     * 新增报价单
     * @param purchaseDto
     * @return result
     */
    @RequestMapping(value = "addQuotation",method = RequestMethod.POST)
    public Result addQuotation(@RequestBody List<PurchaseDto> purchaseDto){
        Result result = new Result();
        if( purchaseService.addPurchase(purchaseDto)> 0){
            result.setSuccess(true);
            result.setMessage("增加成功");
        }else {
            result.setSuccess(false);
            result.setMessage("增加失败");
        }
        return result;
    }

    @RequestMapping(value = "updateUnitPrice",method = RequestMethod.POST)
    Result updateUnitPrice(@RequestBody PurchaseDto purchaseDto){
        return purchaseService.updateUnitPrice(purchaseDto);
    }

    /**
     * 采用/拒绝
     */
    @RequestMapping(value = "isUseRefused",method = RequestMethod.POST)
    Result isUseRefused(@RequestBody PurchaseDto purchaseDto){
        return purchaseService.isUseRefused(purchaseDto);
    }

    /**
     * 报价
     * @param purchaseDto
     * @return
     */
    @RequestMapping(value = "purchase",method = RequestMethod.POST)
    public int purchase(@RequestBody PurchaseDto purchaseDto){
        //存入报价编号
        purchaseDto.setPurchaseNumber("PCH"+sysParamService.getPurchaseNumber());
        return purchaseService.purchase(purchaseDto);
    }

    /**
     * 接收报价
     * @param purchaseSo
     * @return
     */
    @RequestMapping(value = "acceptPurchase",method = RequestMethod.POST)
    EnquiryDto acceptPurchase(@RequestBody PurchaseSo purchaseSo){
        int i =purchaseService.acceptPurchase(purchaseSo);
        EnquiryDto enquiryDto= new EnquiryDto();
        if(i>0){
            // 查询接受报价之后询盘状态
            EnquirySo enquirySo=new EnquirySo();
            enquirySo.setEnquiryNumber(purchaseSo.getEnquiryNumber());
            enquiryDto =enquiryService.getByEnqNum(enquirySo);
        }
        return enquiryDto;
    }


}
