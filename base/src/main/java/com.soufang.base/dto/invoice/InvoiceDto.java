package com.soufang.base.dto.invoice;

import com.soufang.base.utils.DateUtils;

import java.util.Date;

public class InvoiceDto {
    private Long invoiceId;

    private Long userId;

    private String invoiceHead;//发票抬头

    private String taxNumber;//税号

    private String invoiceAddress;

    private String invoicePhone;

    private String invoiceBank;//开户行

    private String invoiceNumber;//开户账号

    private Integer invoiceDefault;//默认  1是  0不是

    private Integer invoiceType;//增值税发票  1是  2普通发票

    private Date createTime;

    private String strCreateTime;

    public String getStrCreateTime() {
        if(createTime == null){
            return null;
        }else {
            return DateUtils.date2String(createTime,DateUtils.format2);
        }
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInvoiceHead() {
        return invoiceHead;
    }

    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead == null ? null : invoiceHead.trim();
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber == null ? null : taxNumber.trim();
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress == null ? null : invoiceAddress.trim();
    }

    public String getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone == null ? null : invoicePhone.trim();
    }

    public String getInvoiceBank() {
        return invoiceBank;
    }

    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank == null ? null : invoiceBank.trim();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber == null ? null : invoiceNumber.trim();
    }

    public Integer getInvoiceDefault() {
        return invoiceDefault;
    }

    public void setInvoiceDefault(Integer invoiceDefault) {
        this.invoiceDefault = invoiceDefault;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
