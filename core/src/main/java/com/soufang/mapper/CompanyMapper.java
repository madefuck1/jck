package com.soufang.mapper;

import com.soufang.model.Company;

public interface CompanyMapper {

    /**
     * 由用户id拿其所属公司信息
     * @param id
     * @return
     */
    Company getByUserId(Long id);

    /**
     * 添加公司
     * @param company
     * @return
     */
    int addCompany(Company company);

    /**
     * 更新公司信息
     * @param company
     * @return
     */
    int updateCompany (Company company);

    int updateCompanyByUserId(Company company);

    Company selectByCompanyName(String companyName);
}
