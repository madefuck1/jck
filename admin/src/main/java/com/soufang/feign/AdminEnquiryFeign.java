package com.soufang.feign;

import com.soufang.Vo.enquiry.EnquiryReviewVo;
import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquiryReviewSo;
import com.soufang.base.search.enquiry.EnquirySo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient("core")
public interface AdminEnquiryFeign {

    @RequestMapping(value = "/core/enquiry/getList",method = RequestMethod.POST)
    PageHelp<EnquiryDto> getList(@RequestBody EnquirySo enquirySo);

    @RequestMapping(value = "/core/enquiry/getByNum",method = RequestMethod.POST)
    EnquiryDto getByNum(@RequestBody String enquiryNumber);

    @RequestMapping(value = "/core/enquiry/getByEnquiryNumber",method = RequestMethod.POST)
    UserDto getByEnquiryNumber(@RequestBody String enquiryNumber);

    @RequestMapping(value = "/core/enquiry/getEPD",method = RequestMethod.POST)
    List<EnquiryProductDto> getEPD(@RequestBody String enquiryNumber);

    @RequestMapping(value = "/core/assort/getDetail",method = RequestMethod.POST)
    AssortDto getAssort(Long assortId);

    @RequestMapping(value = "/core/purchase/getPurchaseList",method = RequestMethod.POST)
    List<PurchaseDto> getPurList(@RequestBody Long id);

    @RequestMapping(value = "/core/shop/getDetail",method = RequestMethod.POST)
    ShopDto getShop(@RequestBody Long id);

    @RequestMapping(value = "/core/enquiry/banQuote",method = RequestMethod.POST)
    Result banQuote(@RequestBody String enquiryNumber);

    @RequestMapping(value = "/core/enquiry/regainQuote",method = RequestMethod.POST)
    Result regainQuote(@RequestBody String enquiryNumber);

    @RequestMapping(value = "/core/enquiry/addEnquiry",method = RequestMethod.POST)
    String addEnquiry(@RequestBody  EnquiryDto enquiryDto);

    @RequestMapping(value = "/core/productManage/getAllProduct", method = RequestMethod.POST)
    List<ProductDto> getAllProduct();

    @RequestMapping(value = "/core/assort/getAll",method = RequestMethod.POST)
    List<AssortDto> getAllAssort();

    @RequestMapping(value = "/core/enquiry/getIdName",method = RequestMethod.POST)
    List<Map<String, Object>> getIdName();

    @RequestMapping(value = "/core/enquiry/addEnquiryProduct")
    Result addEnquiryProduct(@RequestBody List<EnquiryProductDto> listDto);

    @RequestMapping(value = "/core/enquiry/passed")
    Result passed(@RequestBody String enqNum);

    @RequestMapping(value = "/core/enquiry/refuse")
    Result refuse(@RequestBody EnquiryReviewSo reviewVo);
}
