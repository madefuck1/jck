package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.banner.BannerDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient("core")
public interface AdminBannerFeign {

    @RequestMapping(value = "/core/banner/addImg",method = RequestMethod.POST)
    Result addImg(@RequestBody BannerDto bannerDto);

    @RequestMapping(value = "/core/banner/getList",method = RequestMethod.POST)
    List<BannerDto> getList();

    @RequestMapping(value = "/core/banner/deleteById",method = RequestMethod.POST)
    Result deleteById(@RequestBody Long id);
}
