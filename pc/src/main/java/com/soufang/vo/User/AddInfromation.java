package com.soufang.vo.User;

import org.springframework.web.multipart.MultipartFile;

public class AddInfromation {
    private String loginName;
    private String companyName;
    private String companyAddress;
    private String companyCorporate;
    private String companyPhone;
    private String vip;
    private String companyUrls;
    private MultipartFile file;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyCorporate() {
        return companyCorporate;
    }

    public void setCompanyCorporate(String companyCorporate) {
        this.companyCorporate = companyCorporate;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getCompanyUrls() {
        return companyUrls;
    }

    public void setCompanyUrls(String companyUrls) {
        this.companyUrls = companyUrls;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
