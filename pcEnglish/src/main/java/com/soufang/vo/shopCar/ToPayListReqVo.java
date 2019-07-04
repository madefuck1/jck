package com.soufang.vo.shopCar;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/14
 * @Description:
 */
public class ToPayListReqVo {

    private List<ToPayReqVo> list ;

    private Long addressId ;
    private Integer payType ;
    private String remark ;

    public List<ToPayReqVo> getList() {
        return list;
    }

    public void setList(List<ToPayReqVo> list) {
        this.list = list;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
