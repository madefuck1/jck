package com.soufang.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * 下拉选择框
 */
@FeignClient("core")
public interface AdminCommonPullDownFeign {

    @RequestMapping(value = "/core/commonPullDown/getShopIdAndName", method = RequestMethod.POST)
    List<Map<String, Object>> getShopIdAndName();

    @RequestMapping(value = "/core/commonPullDown/getAssortIdAndName", method = RequestMethod.POST)
    List<Map<String, Object>> getAssortIdAndName();

}
