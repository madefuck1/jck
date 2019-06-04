package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.purchase.PurchaseSo;
import com.soufang.service.PurchaseService;
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

    /**
     * 查询所有报价
     * @param purchaseSo
     * @return
     */
    @RequestMapping(value = "getMyPurchaseList",method = RequestMethod.POST)
    public PageHelp<EnquiryDto> getMyPurchaseList(@RequestBody PurchaseSo purchaseSo){
        PageHelp<EnquiryDto> purchaseDtoPageHelp = new PageHelp<>();
        List<EnquiryDto> list= purchaseService.getMyPurchaseList(purchaseSo);
        //总条数
        int count = purchaseService.getCount(purchaseSo);
        purchaseDtoPageHelp.setData(list);
        purchaseDtoPageHelp.setCount(count);
        return purchaseDtoPageHelp;
    }

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


}
