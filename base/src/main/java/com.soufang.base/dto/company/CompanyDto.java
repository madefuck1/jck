package com.soufang.base.dto.company;

import com.soufang.base.PropertiesParam;
import com.soufang.base.enums.CompanyTypeEnum;
import com.soufang.base.utils.DateUtils;

import java.util.Date;

public class CompanyDto {
    private Long compId;
    private Long userId;
    private String compName;
    private Integer compType;
    private String compTypeString;
    private String companyInfo;
    private String compAddress;
    private String compLinker;
    private String compPhone;
    private String compUrls;
    private String companyURL;

    private String compCorporate;
    private String bank;
    private String bankNumber;
    private String longitude;
    private String latitude;
    private Date createTime;
    private String strCreateTime;

    public String getCompTypeString() {
        return compType == null ? "" : CompanyTypeEnum.getByKey(compType);
    }

    public String getCompanyURL() {
        return compUrls == null ? "" : PropertiesParam.file_pre + compUrls;
    }

    public String getStrCreateTime() {
        if (createTime == null) {
            return null;
        } else {
            return DateUtils.date2String(createTime, DateUtils.format1);
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
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

    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }

    public String getCompLinker() {
        return compLinker;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
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
        if (longitude == null) {
            return "0";
        } else {
            return longitude;
        }
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        if (latitude == null) {
            return "0";
        } else {
            return latitude;
        }
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


    public Integer getCompType() {
        return compType;
    }

    public void setCompType(Integer compType) {
        this.compType = compType;
    }
}
