package com.soufang.controller;

import com.soufang.Vo.AdminVo;
import com.soufang.Vo.enquiry.EnquiryReqVo;
import com.soufang.Vo.enquiry.EnquiryVo;
import com.soufang.Vo.enquiryProduct.EnqProReqVo;
import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.feign.AdminEnquiryFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/enquiry")
public class EnquiryController {

    @Autowired
    AdminEnquiryFeign adminEnquiryFeign;



    @RequestMapping(value = "toList",method = RequestMethod.GET)
    public String toList(){
        return "/enquiry/list";
    }

    @ResponseBody
    @RequestMapping(value = "getList",method = RequestMethod.GET)
    public EnquiryVo getList(Integer page, Integer limit, String enquiryTitle){
        EnquiryVo enquiryVo = new EnquiryVo();
        EnquirySo enquirySo = new EnquirySo();
        if(StringUtils.isNotBlank(enquiryTitle)){
            enquirySo.setEnquiryTitle(enquiryTitle);
        }
        enquirySo.setPage(page);
        enquirySo.setLimit(limit);
        PageHelp<EnquiryDto> pageHelp = adminEnquiryFeign.getList(enquirySo);
        enquiryVo.setData(pageHelp.getData());
        enquiryVo.setCount(pageHelp.getCount());
        return enquiryVo;
    }

    @RequestMapping(value = "enquiryDetail/{enqNum}", method = RequestMethod.GET)
    public String getDetail(@PathVariable String enqNum, ModelMap model){
        EnquiryDto enquiryDto = adminEnquiryFeign.getByNum(enqNum);
        UserDto userDto = adminEnquiryFeign.getByEnquiryNumber(enqNum);
        model.put("enquiryDto",enquiryDto);
        model.put("userDto",userDto);
        return "/enquiry/detail";
    }

    @RequestMapping(value = "enquiryDetail/viewProduct/{enqNum}", method = RequestMethod.GET)
    public String getProduct(@PathVariable String enqNum,ModelMap model){
        List<EnquiryProductDto> list = adminEnquiryFeign.getEPD(enqNum);
        List<EnquiryProductDto> listEPD = new ArrayList<>();
        for (EnquiryProductDto e:list) {
            EnquiryProductDto ePDto = new EnquiryProductDto();
            AssortDto assortDto = adminEnquiryFeign.getAssort(e.getProductAssort());
            BeanUtils.copyProperties(e,ePDto);
            ePDto.setAssortName(assortDto.getAssortName());
            listEPD.add(ePDto);
        }
        model.put("enqProDtoList",listEPD);
        return "/enquiry/productDetail";
    }
    @RequestMapping(value = "enquiryDetail/viewProduct/checkvalue/{id}", method = RequestMethod.GET)
    public String getPurchase(@PathVariable Long id,ModelMap model){
        List<PurchaseDto> list = adminEnquiryFeign.getPurList(id);
        List<PurchaseDto> listDto = new ArrayList<>();
        for (PurchaseDto purDto:list) {
            ShopDto shopDto = adminEnquiryFeign.getShop(purDto.getShopId());
            purDto.setShopName(shopDto.getShopName());
            listDto.add(purDto);
        }
        model.put("purchaseDtoList",listDto);
        return "/enquiry/getPurchase";
    }

    @ResponseBody
    @RequestMapping(value = "banQuote/{enqNum}", method = RequestMethod.GET)
    public AdminVo banQuote(@PathVariable String enqNum){
        Result result = adminEnquiryFeign.banQuote(enqNum);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @ResponseBody
    @RequestMapping(value = "regainQuote/{enqNum}", method = RequestMethod.GET)
    public AdminVo regainQuote(@PathVariable String enqNum){
        Result result = adminEnquiryFeign.regainQuote(enqNum);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }

    @RequestMapping(value = "toAddEnquiry", method = RequestMethod.GET)
    public String toAddEnquiry( ModelMap model){

        return "/enquiry/addEnquiry";
    }
    @ResponseBody
    @RequestMapping(value = "getAssortIdName", method = RequestMethod.GET)
    public List<Map<String, Object>> getAssortIdName(){
        List<Map<String, Object>> list = adminEnquiryFeign.getIdName();
        return list;
    }
    @ResponseBody
    @RequestMapping(value = "addEnquiry",method = RequestMethod.POST)
    AdminVo addEnquiry(@RequestBody EnquiryReqVo enquiryReqVo){
        //处理询盘表的输入内容
        EnquiryDto enquiryDto = new EnquiryDto();
        BeanUtils.copyProperties(enquiryReqVo,enquiryDto);
        enquiryDto.setEnquiryType(Integer.valueOf(enquiryReqVo.getStrEnquiryType()));
        enquiryDto.setEnquiryStatus(Integer.valueOf(enquiryReqVo.getStrEnquiryStatus()));
        enquiryDto.setTakeDate(enquiryReqVo.getTakeDate());
        enquiryDto.setEndTime(enquiryReqVo.getEndTime());
        String enquiryNumber = adminEnquiryFeign.addEnquiry(enquiryDto);
        //处理询盘产品表的输入内容
        List<EnqProReqVo> list = enquiryReqVo.getTable();
        List<EnquiryProductDto> listDto = new ArrayList<>();
        for (EnqProReqVo vo:list) {
            EnquiryProductDto epDto = new EnquiryProductDto();
            epDto.setProductNumber(Long.valueOf(vo.getProductNumber()));
            epDto.setProductAssort(Long.valueOf(vo.getAssortId()));
            epDto.setEnquiryNumber(enquiryNumber);
            BeanUtils.copyProperties(vo,epDto);
            listDto.add(epDto);
        }
        Result result = adminEnquiryFeign.addEnquiryProduct(listDto);
        AdminVo adminVo = new AdminVo(result);
        return adminVo;
    }
}
