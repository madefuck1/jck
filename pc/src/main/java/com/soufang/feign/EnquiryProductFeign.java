package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.page.PageHelp;
import com.soufang.vo.enquiryProduct.EnquiryProductUpdateVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface EnquiryProductFeign {
    //删除照片
    @RequestMapping(value = "core/enquiryProduct/delEnProImgUrl",method = RequestMethod.POST)
    Result delEnProImgUrl(@RequestBody EnquiryProductUpdateVo enquiryProductUpdateVo);


}
