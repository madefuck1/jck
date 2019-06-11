/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: ExhibitionController
 * Author:   小三
 * Date:     2019/6/11 10:11
 * Description: 展会
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.controller;

import com.soufang.base.dto.exhibition.ExhibitionDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.exhibition.ExhibitionSo;
import com.soufang.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈展会〉
 *
 * @author 小三
 * @create 2019/6/11
 * @since 1.0.0
 */
@RestController
@RequestMapping("/core/exhibition")
public class ExhibitionController {

    @Autowired
    ExhibitionService exhibitionService;

    /**
     *查询展示列表
     * @return
     */
    @RequestMapping(value = "selExhibitionList",method = RequestMethod.POST)
    public PageHelp<ExhibitionDto> selExhibitionList(@RequestBody ExhibitionSo exhibitionSo){
        PageHelp<ExhibitionDto> pageHelp = new PageHelp<>();
        List<ExhibitionDto>  exhibitionDtoList = exhibitionService.selExhibition(exhibitionSo);
        pageHelp.setData(exhibitionDtoList);
        pageHelp.setCount(exhibitionDtoList.size());
        return pageHelp;
    }
}
