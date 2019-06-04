package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.utils.DateUtils;
import com.soufang.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @RequestMapping(value = "companyInfo", method = RequestMethod.POST)
    CompanyDto companyInfo(@RequestBody Long id){
        return companyService.getByUserId(id);
    }

    @RequestMapping(value = "addCompany",method = RequestMethod.POST)
    Result addCompany(@RequestBody CompanyDto companyDto){
        Result result = new Result();
        companyDto.setCreateTime(DateUtils.getToday());
        if(companyService.addCompany(companyDto) > 0){
            result.setMessage("添加成功");
            result.setSuccess(true);
        }else {
            result.setMessage("添加失败");
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping(value = "getCompany",method = RequestMethod.POST)
    CompanyDto getCompany(@RequestBody Long userId){
        return companyService.getByUserId(userId);
    }

    @RequestMapping(value = "updateCompany",method = RequestMethod.POST)
    Result updateCompany(@RequestBody CompanyDto companyDto){
        Result result = new Result();
        if(companyService.updateCompany(companyDto) > 0){
            result.setMessage("更新成功");
            result.setSuccess(true);
        }else {
            result.setMessage("更新失败");
            result.setSuccess(false);
        }
        return result;
    }
}
