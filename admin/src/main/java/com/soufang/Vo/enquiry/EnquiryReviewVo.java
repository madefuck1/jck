package com.soufang.Vo.enquiry;

import com.soufang.Vo.AdminVo;

public class EnquiryReviewVo extends AdminVo {
    private String enquiryNumber;
    private String reason;

    public String getEnquiryNumber() {
        return enquiryNumber;
    }

    public void setEnquiryNumber(String enquiryNumber) {
        this.enquiryNumber = enquiryNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
