package com.soufang.app.feign;

import com.soufang.app.vo.purchase.addPurchaseVo;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.purchase.PurchaseSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("core")
public interface AppPurchaseFeign {

    //查询我的所有报价
    @RequestMapping(value = "/core/purchase/getMyPurchaseList",method = RequestMethod.POST)
    public PageHelp<EnquiryDto> getMyPurchaseList(@RequestBody PurchaseSo purchaseSo);

    //查询报价信息
    @RequestMapping(value = "/core/purchase/getPurchaseListByEnqunum",method = RequestMethod.POST)
    public List<PurchaseDto> getPurchaseListByEnqunum(String enquiryNumber);

    //接收报价
    @RequestMapping(value = "/core/purchase/acceptPurchase",method = RequestMethod.POST)
    public EnquiryDto acceptPurchase(@RequestBody PurchaseSo purchaseSo);

    //报价
    @RequestMapping(value = "/core/purchase/purchase",method = RequestMethod.POST)
    public int purchase(@RequestBody PurchaseDto purchaseDto);

}
