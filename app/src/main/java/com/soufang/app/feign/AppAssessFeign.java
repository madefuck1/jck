package com.soufang.app.feign;


import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("core")
public interface AppAssessFeign {

    @RequestMapping(value = "/core/assess/getList",method = RequestMethod.POST)
    PageHelp<AssessDto> getList(@RequestBody AssessSo assessSo);
}
