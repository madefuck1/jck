package com.soufang.app.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface AppEnquiryFeign {

    //获取询盘编号
    @RequestMapping(value = "/core/enquiry/toGetEqNumber",method = RequestMethod.POST)
    public String toGetEqNumber();

    //发布询盘
    @RequestMapping(value = "/core/enquiry/addEnquiry",method = RequestMethod.POST)
    public Result addEnquiry(@RequestBody EnquiryDto enquiryDto);

    //询盘列表
    @RequestMapping(value = "/core/enquiry/getList",method = RequestMethod.POST)
    public PageHelp<EnquiryDto> getList(@RequestBody EnquirySo enquirySo);

    //询盘详情
    @RequestMapping(value = "/core/enquiry/selEnquiryByNumber",method = RequestMethod.POST)
    public EnquiryDto selEnquiryByNumber(@RequestBody EnquirySo enquirySo);


}
