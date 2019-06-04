package com.soufang.model;

import java.util.Date;

public class Company {
    private Long compId;
    private Long userId;
    private String compName;
    private String companyInfo;
    private String compAddress;
    private String compLinker;
    private String compPhone;
    private String compUrls;
    private String compCorporate;
    private String bank;
    private String bankNumber;
    private String longitude;
    private String latitude;
    private Date createTime;

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompAddress() {
        return compAddress;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }

    public String getCompLinker() {
        return compLinker;
    }

    public void setCompLinker(String compLinker) {
        this.compLinker = compLinker;
    }

    public String getCompPhone() {
        return compPhone;
    }

    public void setCompPhone(String compPhone) {
        this.compPhone = compPhone;
    }

    public String getCompUrls() {
        return compUrls;
    }

    public void setCompUrls(String compUrls) {
        this.compUrls = compUrls;
    }

    public String getCompCorporate() {
        return compCorporate;
    }

    public void setCompCorporate(String compCorporate) {
        this.compCorporate = compCorporate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
