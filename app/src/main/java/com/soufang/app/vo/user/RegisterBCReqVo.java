package com.soufang.app.vo.user;



public class RegisterBCReqVo {
    private String loginName;
    private String companyName;
    private String companyAddress;
    private String companyLinker;//公司联系人
    private String companyCorporate;//公司负责人
    private String companyImage;//公司营业照

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

    public String getCompanyLinker() {
        return companyLinker;
    }

    public void setCompanyLinker(String companyLinker) {
        this.companyLinker = companyLinker;
    }

    public String getCompanyCorporate() {
        return companyCorporate;
    }

    public void setCompanyCorporate(String companyCorporate) {
        this.companyCorporate = companyCorporate;
    }

    public String getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(String companyImage) {
        this.companyImage = companyImage;
    }
}
