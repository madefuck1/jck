package com.soufang.service;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.purchase.PurchaseSo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PurchaseService {

    /**
     * 通过enquiry_product_id 拿报价信息集合 List
     * @param id
     * @return
     */
    List<PurchaseDto> getByProId(Long id);

    /**
     * 新增报价单
     * @param purchaseDto
     * @return
     */
    int addPurchase(List<PurchaseDto> purchaseDto);

    /**
     * 询盘产品信息-询盘报价-询盘商铺信息
     * @param purchaseDto
     * @return
     */
    Result updateUnitPrice(PurchaseDto purchaseDto);

    //查询报价信息
    List<PurchaseDto> getPurchaseListByEnqunum(String enquiryNumber);

   // int getCount( PurchaseSo purchaseSo);

    int purchase(PurchaseDto purchaseDto);

    int acceptPurchase(PurchaseSo purchaseSo);

    int userPurchaseNumber(PurchaseSo purchaseSo);

    PageHelp<PurchaseDto> getMyPurchaseList(PurchaseSo purchaseSo);
}
