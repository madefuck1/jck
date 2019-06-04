package com.soufang.controller;


import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import com.soufang.service.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core/assess")
public class AssessController {

    @Autowired
    AssessService assessService;

    @RequestMapping(value = "getList",method = RequestMethod.POST)
    public PageHelp<AssessDto> getList(@RequestBody AssessSo assessSo){
        return assessService.getList(assessSo);
    }
}
