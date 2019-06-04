package com.soufang.base.dto.contract;

import java.util.Date;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
public class ContractDto {
    private Long contractId;

    private String contractDownUrl;

    private String contractUploadUrl;

    private Integer contractStatus;

    private String orderNumber;

    private Long orderShopId;

    private Date createTime;

    private Date confirmTime;

    private String contractReason;


    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getContractDownUrl() {
        return contractDownUrl;
    }

    public void setContractDownUrl(String contractDownUrl) {
        this.contractDownUrl = contractDownUrl;
    }

    public String getContractUploadUrl() {
        return contractUploadUrl;
    }

    public void setContractUploadUrl(String contractUploadUrl) {
        this.contractUploadUrl = contractUploadUrl;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getContractReason() {
        return contractReason;
    }

    public void setContractReason(String contractReason) {
        this.contractReason = contractReason;
    }
}
