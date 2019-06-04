package com.soufang.service;

import com.soufang.base.dto.company.CompanyDto;
import com.soufang.model.Company;

public interface CompanyService {
    /**
     * 由用户id拿其所属公司信息
     * @param id
     * @return
     */
    CompanyDto getByUserId(Long id);

    int addCompany(CompanyDto companyDto);

    //修改公司信息ByUserId
    int updateCompany(CompanyDto companyDto);
}
