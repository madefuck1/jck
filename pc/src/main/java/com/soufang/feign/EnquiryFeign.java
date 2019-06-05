package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.vo.Enquiry.EnquiryAddVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@FeignClient("core")
public interface EnquiryFeign {

    //列表查询
    @RequestMapping(value = "core/enquiry/getList",method = RequestMethod.POST)
    PageHelp<EnquiryDto> getList(@RequestBody EnquirySo enquirySo);

    //我的报价列表
    @RequestMapping(value = "core/enquiry/getMyQuoteList",method = RequestMethod.POST)
    PageHelp<EnquiryDto> getMyQuoteList(@RequestBody EnquirySo enquirySo);

    //新增
    @RequestMapping(value = "core/enquiry/addEnquiry",method = RequestMethod.POST)
    Result addEnquiry(@RequestBody  EnquiryDto enquiryDto);

    //编辑
    @RequestMapping(value = "core/enquiry/updateEnquiryAndProduct",method = RequestMethod.POST)
    Result updateEnquiryAndProduct(@RequestBody EnquiryDto enquiryDto);

    //删除
    @RequestMapping(value = "core/enquiry/delEnquiry",method = RequestMethod.POST)
    Result delEnquiry(@RequestBody String enquiryNumber);

    //查看详情-询盘
    @RequestMapping(value = "core/enquiry/selEnquiryByNumber",method = RequestMethod.POST)
    EnquiryDto selEnquiryByNumber(@RequestBody String enquiryNumber);

    //获取单号
    @RequestMapping(value = "core/enquiry/toGetEqNumber",method = RequestMethod.POST)
    String toGetEqNumber();

    @RequestMapping(value = "core/enquiry/getQuoteDetails",method = RequestMethod.POST)
    EnquiryDto getQuoteDetails(@RequestBody Long enquiryProductId);

    @RequestMapping(value = "/core/purchase/updateUnitPrice",method = RequestMethod.POST)
    Result updateUnitPrice(@RequestBody PurchaseDto purchaseDto);

    //求购列表信息
    @RequestMapping(value = "core/enquiry/enquiryTableMessage",method = RequestMethod.POST)
    PageHelp<FavoriteDto> enquiryTableMessage(@RequestBody EnquirySo enquirySo);
}
