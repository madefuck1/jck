package com.soufang.service;

import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;

import java.util.List;

public interface EnquiryProductService {

    /**
     * 由询盘号拿到询盘产品详细信息集合
     * @param enquiryNumber
     * @return
     */
    List<EnquiryProductDto> getByEnquiryNumber(String enquiryNumber);

    /**
     * 添加询盘的商品信息
     * @param listDto
     * @return
     */
    int addEnquiryProduct(List<EnquiryProductDto> listDto);

    /**
     * 修改询盘产品信息
     * @param listDto
     * @return
     */
    int updateEnquiryProduct(List<EnquiryProductDto> listDto);

    //删除照片
    int delEnProImgUrl(Long enquiryProductId);
}
