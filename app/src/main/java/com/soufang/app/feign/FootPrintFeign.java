package com.soufang.app.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.footprint.FootPrintDto;
import com.soufang.base.page.PageHelp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface FootPrintFeign {

    //新增
    @RequestMapping(value = "core/footPrint/addFootPrint",method = RequestMethod.POST)
    boolean addFootPrint(@RequestBody FootPrintDto footPrintDto);

    // 列表
    @RequestMapping(value = "core/footPrint/getFootPrintList",method = RequestMethod.POST)
    PageHelp<FootPrintDto> getFootPrintList(@RequestBody FootPrintDto footPrintDto);

    // 删除
    @RequestMapping(value = "core/footPrint/delFootPrintById",method = RequestMethod.POST)
    Result delFootPrintById(@RequestBody String footPringtId);

    //查询详情
    @RequestMapping(value = "core/footPrint/selFootPrintById",method = RequestMethod.POST)
    FootPrintDto selFootPrintById(@RequestBody String footPringtId);

}
