package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.EnquiryFeign;
import com.soufang.vo.BaseVo;
import com.soufang.vo.Enquiry.EnquiryVo;
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
    public EnquiryVo getMyQuote(HttpServletRequest request, @RequestBody EnquirySo enquirySo) {
        EnquiryVo vo = new EnquiryVo();
        UserDto userInfo = this.getUserInfo(request);
        ShopDto shopInfo = this.getShopInfo(request);
        enquirySo.setPage(enquirySo.getPage());
        enquirySo.setShopId(shopInfo.getShopId());
        enquirySo.setUserId(userInfo.getUserId());
        enquirySo.setLimit(5);
        if(enquirySo.getShopId() == null){
            vo.setCount(0);
             return vo;
        }
        PageHelp<EnquiryDto> pageHelp = enquiryFeign.getMyQuoteList(enquirySo);
        vo.setData(pageHelp.getData());
        vo.setCount(pageHelp.getCount());
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
