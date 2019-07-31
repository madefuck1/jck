package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.search.purchase.PurchaseSo;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.EnquiryFeign;
import com.soufang.vo.BaseVo;
import com.soufang.vo.Enquiry.EnquiryVo;
import com.soufang.vo.purchase.PurchaseVo;
import com.soufang.vo.purchase.UpdateUnitPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("enquiry")
public class EnquiryController extends BaseController {

    @Autowired
    EnquiryFeign enquiryFeign;

    //去我的报价页面
    @MemberAccess
    @RequestMapping(value = "toMyQuote", method = RequestMethod.GET)
    public String toOrderManager() {
        return "sellerCenter/myQuote";
    }
    //加载报价页面数据
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "getMyQuote", method = RequestMethod.POST)
    public PurchaseVo getMyQuote(HttpServletRequest request, @RequestBody PurchaseSo purchaseSo) {
        PurchaseVo vo = new PurchaseVo();
        ShopDto shopDto=this.getShopInfo(request);
        //卖家要通过ShopId查询报价信息
        purchaseSo.setShopId(shopDto.getShopId());
        purchaseSo.setUserId(shopDto.getUserId());
        purchaseSo.setLimit(5);
        if(shopDto.getShopId() == null){
            vo.setCount(0);
            return vo;
        }
        PageHelp<PurchaseDto> pageHelps =enquiryFeign.getMyPurchaseList(purchaseSo);
        vo.setData(pageHelps.getData());
        vo.setCount(pageHelps.getCount());
        vo.setSuccess(true);
        vo.setMessage("获取报价列表成功");
        return vo;
    }
    //报价详情
    @MemberAccess
    @RequestMapping(value = "/toQuoteDetails/{enquiryProductId}", method = RequestMethod.GET)
    public String toQuoteDetails(@PathVariable Long enquiryProductId, ModelMap modelMap) {
        EnquiryDto enquiryDto = enquiryFeign.getQuoteDetails(enquiryProductId);
        modelMap.put("enquiryDetail",enquiryDto);
        return "sellerCenter/quoteDetails";
    }

    //修改报价
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/updatePrice", method = RequestMethod.POST)
    public BaseVo updatePrice(@RequestBody UpdateUnitPrice unitPrice ) {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setPurchaseNumber(unitPrice.getPurchaseNumber());
        purchaseDto.setUnitPrice(unitPrice.getUnitPrice());
        BaseVo vo = new BaseVo();
        Result result = enquiryFeign.updateUnitPrice(purchaseDto);
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }

}
