package com.soufang.mapper;

import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.model.EnquiryProduct;

import java.util.List;

public interface EnquiryProductMapper {

    List<EnquiryProduct> getByEnquiryNumber(String enquiryNumber);

    int deleteByPrimaryKey(Long enquiryProductId);

    int delByEnquiryNum(String enquiryNumber);

    int insert(EnquiryProduct record);

    int insertSelective(EnquiryProduct record);

    EnquiryProduct selectByPrimaryKey(Long enquiryProductId);

    int updateByPrimaryKeySelective(EnquiryProduct record);

    int updateByPrimaryKey(EnquiryProduct record);

    int delEnProImgUrl(Long enquiryProductId);


}