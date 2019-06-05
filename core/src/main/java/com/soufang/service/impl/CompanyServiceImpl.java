package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.mapper.CompanyMapper;
import com.soufang.model.Company;
import com.soufang.service.CompanyService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "companyService")
public class CompanyServiceImpl implements CompanyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public CompanyDto getByUserId(Long id) {
        CompanyDto companyDto = new CompanyDto();
        Company company = companyMapper.getByUserId(id);
        if(company != null){
            BeanUtils.copyProperties(company,companyDto);
            return companyDto;
        }else {
            return companyDto;
        }
    }

    @Override
    @Transactional
    public int addCompany(CompanyDto companyDto) {
        try {
            Company company = new Company();
            BeanUtils.copyProperties(companyDto, company);
            Company company1 = companyMapper.getByUserId(company.getUserId());
            if (company1 != null) {
                //公司存在则更新公司信息
                company.setCompId(company1.getCompId());
                companyMapper.updateCompany(company);
            } else {
                //公司不存在则添加公司信息
                companyMapper.addCompany(company);
            }
            return 1;
        }catch (Exception e){
            logger.info("添加公司报错:"+e.getMessage());
            throw new BusinessException("添加公司报错");
        }
    }

    @Override
    public int updateCompany(CompanyDto companyDto) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDto,company);
        return companyMapper.updateCompanyByUserId(company);
    }

    @Override
    public CompanyDto selectByCompanyName(String companyName) {
        Company company = companyMapper.selectByCompanyName(companyName);
        if(company == null){
            return  null ;
        }else {
            CompanyDto companyDto = new CompanyDto();
            BeanUtils.copyProperties(company,companyDto);
            return companyDto;
        }
    }
}
