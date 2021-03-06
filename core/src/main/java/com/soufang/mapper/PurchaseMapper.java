package com.soufang.mapper;

import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.search.purchase.PurchaseSo;
import com.soufang.model.Purchase;

import java.util.List;

public interface PurchaseMapper {
    List<Purchase> getByProId(Long id);

    int deleteByPrimaryKey(String purchaseNumber);

    int purchase(Purchase record);

    //新增报价单
    int insertSelective(Purchase record);
    //根据询盘查询报价单
    List<EnquiryDto> selPurchaseByenquiryNumber(String enquiryNumber);

    int getCount(Purchase purchase);

    Purchase selectByPrimaryKey(String purchaseNumber);

    int updateByPrimaryKeySelective(Purchase record);

    int updateByPrimaryKey(Purchase record);

    int delPurchaseByEnquiryNumber(String enquiryNumber);

    List<Purchase> getPurchaseListByEnqunum( String enquiryNumber);

    int acceptPurchase(PurchaseSo purchaseSo);

    int refusePurchase(PurchaseSo purchaseSo);

    int userPurchaseNumber(PurchaseSo purchaseSo);

    List<PurchaseDto> getMyPurchaseList(PurchaseSo purchaseSo);

    int getMyPurchasecount(PurchaseSo purchaseSo);
}