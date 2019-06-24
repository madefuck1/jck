package com.soufang.app.feign;

import com.soufang.base.dto.exhibition.ExhibitionDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.exhibition.ExhibitionSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface AppExhibitionFeign {

    //列表查询
    @RequestMapping(value = "core/exhibition/selExhibitionList",method = RequestMethod.POST)
    PageHelp<ExhibitionDto> selExhibitionList(@RequestBody ExhibitionSo exhibitionSo);
}
