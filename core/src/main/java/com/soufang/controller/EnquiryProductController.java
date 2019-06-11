package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.page.PageHelp;
import com.soufang.service.EnquiryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core/enquiryProduct")
public class EnquiryProductController {

    @Autowired
    EnquiryProductService enquiryProductService;

    /**
     * 删除照片
     * @param enquiryProductDto
     * @return
     */
    @RequestMapping(value = "delEnProImgUrl",method = RequestMethod.POST)
    public Result delEnProImgUrl(@RequestBody EnquiryProductDto enquiryProductDto){
        Result result = new Result();
       int i= enquiryProductService.delEnProImgUrl(enquiryProductDto.getEnquiryProductId());
        if(i> 0){
            result.setSuccess(true);
            result.setMessage("删除成功");
        }else {
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    @RequestMapping(value = "getIndexProductList",method = RequestMethod.POST)
    PageHelp<EnquiryProductDto> getIndexProductList(){
        return enquiryProductService.getIndexProductList();
    }
}
