package com.soufang.feign;

import com.soufang.base.dto.assort.AssortDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient("core")
public interface AssortFeign {

    /**
     * 获取分类信息--ID、NAME
     * @return
     */
    @RequestMapping(value = "core/assort/getIdName",method = RequestMethod.POST)
    List<Map<String, Object>> getIdName();

}
