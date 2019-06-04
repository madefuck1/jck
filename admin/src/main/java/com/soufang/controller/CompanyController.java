package com.soufang.controller;

import com.soufang.base.dto.company.CompanyDto;
import com.soufang.feign.AdminCompanyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/company")
public class CompanyController {

    @Autowired
    AdminCompanyFeign adminCompanyFeign;

    @RequestMapping(value = "companyInfo/{id}", method = RequestMethod.GET)
    public String companyInfo(@PathVariable Long id, ModelMap model){
        CompanyDto companyDto = adminCompanyFeign.companyInfo(id);
        model.put("companyDto",companyDto);
        return "/user/details";
    }

}
