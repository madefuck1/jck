package com.soufang.feign;

import com.soufang.base.dto.banner.BannerDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("core")
public interface BannerFeign {
    @RequestMapping(value = "/core/banner/getList",method = RequestMethod.POST)
    List<BannerDto> getList(@RequestBody Integer terminal);
}
