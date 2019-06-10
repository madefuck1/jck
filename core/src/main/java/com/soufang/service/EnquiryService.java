package com.soufang.service;

import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.search.purchase.PurchaseSo;
import com.soufang.model.Enquiry;

import java.util.List;

public interface EnquiryService {

    /**
     * 由询盘号拿询盘信息
     * @param enquiryNumber
     * @return
     */
    EnquiryDto getByEnqNum( EnquirySo enquirySo);

    /**
     * 按条件查询询盘信息集合
     * @param enquirySo
     * @return
     */
    List<EnquiryDto> getList(EnquirySo enquirySo);

    int  enquiryTableCount(EnquirySo EnquirySo);

    /**
     * 按条件查询询盘的数量
     * @param enquirySo
     * @return
     */
    int getCount(EnquirySo enquirySo);

    /**
     * 禁止报价  将enquiry_status 修改为-1
     * @param enquiryNumber
     * @return
     */
    int banQuote(String enquiryNumber);

    /**
     * 恢复报价  将enquiry_status 修改为0
     * @param enquiryNumber
     * @return
     */
    int regainQuote(String enquiryNumber);

    /**
     * 添加询盘信息-产品
     * @param enquiryDto
     * @return
     */
    int insertSelective(EnquiryDto enquiryDto);

    /**
     * 修改询盘信息-产品
     */
    int updateEnquiry(EnquiryDto enquiryDto);

    /**
     * 删除询盘-产品
     * @return
     */
    int delEnquiry(String enquiryNumber);

    /**
     * 获取我的报价列表的数量
     * @param enquirySo
     * @return
     */
    int getMyQuoteCount(EnquirySo enquirySo);

    //获取报价详情
    EnquiryDto getQuoteDetails(Long enquiryProductId);
}
