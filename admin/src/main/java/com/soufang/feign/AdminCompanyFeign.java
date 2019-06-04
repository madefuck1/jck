package com.soufang.feign;

import com.soufang.base.dto.company.CompanyDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface AdminCompanyFeign {

    @RequestMapping(value = "core/company/companyInfo", method = RequestMethod.POST)
    CompanyDto companyInfo(@RequestBody Long id);


}
