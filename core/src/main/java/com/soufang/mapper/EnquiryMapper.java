package com.soufang.mapper;

import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.search.purchase.PurchaseSo;
import com.soufang.model.Enquiry;

import java.util.List;

public interface EnquiryMapper {

    /**
     * 由询盘号拿询盘信息
     * @param enquirySo
     * @return
     */
    Enquiry getByEnqNum(EnquirySo enquirySo);

    //查询详情-询盘-产品-询价
    Enquiry selDetailEnquiryAndProAndPur(Enquiry enquiry);

    /**
     * 按条件查询询盘信息集合
     * @param enquirySo
     * @return
     */
    List<Enquiry> getList(EnquirySo enquirySo);

    /**
     * 获取我的报价列表数量
     * @param enquirySo
     * @return
     */
    int getMyQuoteCount(EnquirySo enquirySo);
    /**
     * 获取报价详情
     */
    Enquiry getQuoteDetail(Long enquiryProductId);


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

    int deleteByPrimaryKey(String enquiryNumber);

    int insert(Enquiry record);

    /**
     * 添加询盘信息
     * @param record
     * @return
     */
    int insertSelective(Enquiry record);

    //修改询盘信息
    int updateByPrimaryKeySelective(Enquiry record);

    int updateByPrimaryKey(Enquiry record);
    //删除询盘
    int delEnquiry(EnquirySo enquirySo);

    //查询我的报价
    List<Enquiry> getMyPurchaseList(PurchaseSo purchaseSo);

    int  enquiryTableCount(EnquirySo EnquirySo);

    Long selUserIdByEnquiryNumber(String enquiryNumber);
}