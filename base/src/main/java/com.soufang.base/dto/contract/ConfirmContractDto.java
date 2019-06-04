package com.soufang.base.dto.contract;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
public class ConfirmContractDto {

    private String orderNumber ;
    private Long orderShopId ;
    private int contractStatus ;
    private String contractReason ;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getOrderShopId() {
        return orderShopId;
    }

    public void setOrderShopId(Long orderShopId) {
        this.orderShopId = orderShopId;
    }

    public int getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(int contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractReason() {
        return contractReason;
    }

    public void setContractReason(String contractReason) {
        this.contractReason = contractReason;
    }
}
