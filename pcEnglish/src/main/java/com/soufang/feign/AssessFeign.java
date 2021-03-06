package com.soufang.feign;

import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;


@FeignClient("core")
public interface AssessFeign {

    @RequestMapping(value = "/core/assess/getList",method = RequestMethod.POST)
    PageHelp<AssessDto> getList(@RequestBody AssessSo assessSo);

    @RequestMapping(value = "/core/assess/getCount",method = RequestMethod.POST)
    Map<String,Integer> getCount(@RequestBody AssessSo assessSo);

    @RequestMapping(value = "/core/assess/getOrderProduct",method = RequestMethod.POST)
    OrderShopDto getOrderProduct(@RequestBody String orderNumber);

    @RequestMapping(value = "/core/assess/putAssess",method = RequestMethod.POST)
    Long putAssess(@RequestBody List<AssessDto> assessDtos);

}
