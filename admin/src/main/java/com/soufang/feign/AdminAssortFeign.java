package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.feign.hystrix.AdminAssortHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/19
 * @Description:
 */
@FeignClient(name="core",fallback = AdminAssortHystrix.class)
public interface AdminAssortFeign {

    @RequestMapping(value = "/core/assort/getAll",method = RequestMethod.POST)
    List<AssortDto> getAll();

    @RequestMapping(value = "/core/assort/save",method = RequestMethod.POST)
    Result save(@RequestBody AssortDto assortDto);

    @RequestMapping(value = "/core/assort/getDetail",method = RequestMethod.POST)
    AssortDto detail(@RequestBody Long assortId);

    @RequestMapping(value = "/core/assort/delete", method = RequestMethod.POST)
    Result delete(@RequestBody Long id);

    @RequestMapping(value = "/core/assort/update", method = RequestMethod.POST)
    Result update(@RequestBody AssortDto assortDto);
}
