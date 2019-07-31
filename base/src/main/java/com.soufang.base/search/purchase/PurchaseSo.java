/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: PurchaseSo
 * Author:   小三
 * Date:     2019/5/29 11:23
 * Description: 报价表
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.base.search.purchase;

import lombok.Getter;
import lombok.Setter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈报价表〉
 *
 * @author 小三
 * @create 2019/5/29
 * @since 1.0.0
 */

public class PurchaseSo {
    private Integer page ;
    private Integer limit ;
    private Long userId;
    private Long shopId;
    private String enquiryNumber;
    private String purchaseNumber;
    private String  enquiryProductId;
    private Integer enquiryStatus;
    private Integer offerStatus;
    private int unitPrice;
    private String remark;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(String enquiryNumber) {
        this.enquiryNumber = enquiryNumber;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public String getEnquiryProductId() {
        return enquiryProductId;
    }

    public void setEnquiryProductId(String enquiryProductId) {
        this.enquiryProductId = enquiryProductId;
    }

    public Integer getEnquiryStatus() {
        return enquiryStatus;
    }

    public void setEnquiryStatus(Integer enquiryStatus) {
        this.enquiryStatus = enquiryStatus;
    }

    public Integer getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(Integer offerStatus) {
        this.offerStatus = offerStatus;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
