package com.soufang.feign;

import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.search.assort.AssortSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/5/13
 * @Description:
 */
@FeignClient("core")
public interface CommonPullDownFeign {

    @RequestMapping(value = "/core/commonPullDown/getFirstAssort",method = RequestMethod.POST)
    List<AssortDto> getFirstAssort();

    //根据父Id获取所有子
    @RequestMapping(value = "/core/commonPullDown/getAssortByParentId",method = RequestMethod.POST)
    List<AssortDto> getAssortByParentId(@RequestBody Long parentId);


    // 根据父Id获取子
    @RequestMapping(value = "/core/commonPullDown/getAssortAByParentId",method = RequestMethod.POST)
    List<AssortDto> getAssortAByParentId(Long parentId);

    @RequestMapping(value = "/core/assort/getDetail",method = RequestMethod.POST)
    AssortDto detail(@RequestBody Long assortId);

    @RequestMapping(value = "/core/commonPullDown/getAssortByKey",method = RequestMethod.POST)
    Map<String,Object> getAssortByKey(AssortSo so);
}
