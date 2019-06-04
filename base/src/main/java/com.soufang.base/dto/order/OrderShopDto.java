package com.soufang.base.dto.order;

import com.soufang.base.enums.OrderStatusEnum;
import com.soufang.base.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/23
 * @Description:
 */
public class OrderShopDto {

    private Long orderShopId;

    private Long userId;

    private String loginName ;

    private String userName;

    private String userAvatar;

    private String orderNumber;

    private Long shopId;

    private String shopName;

    private String sendName;

    private String sendAddress;

    private String sendPhone;

    private Integer invoice;

    private BigDecimal sumPrice;

    private BigDecimal actualPrice;

    private BigDecimal frontPrice;

    private Integer orderShopStatus;
    private String orderShopStatusString;

    public String getOrderShopStatusString(){
        return OrderStatusEnum.getMessageByKey(orderShopStatus);
    }


    private Date createTime;

    // 发货物流/快递  类型
    private String sendType;
    // 单号
    private String sendNumber;

    private String createTimeString ;

    private List<OrderProductDto> orderProducts;

    private OrderDto orderDto;

    private String statusMessage;

    private String statusColor ;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }

    public String getCreateTimeString(){
        return createTime==null? "2000-01-01" :DateUtils.date2String(createTime,DateUtils.format1);
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Long getOrderShopId() {
        return orderShopId;
    }

    public void setOrderShopId(Long orderShopId) {
        this.orderShopId = orderShopId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getOrderShopStatus() {
        return orderShopStatus;
    }

    public void setOrderShopStatus(Integer orderShopStatus) {
        this.orderShopStatus = orderShopStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<OrderProductDto> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDto> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getFrontPrice() {
        if(frontPrice == null){
            return BigDecimal.valueOf(0);
        }else {
            return frontPrice;
        }
    }

    public void setFrontPrice(BigDecimal frontPrice) {
        this.frontPrice = frontPrice;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(String sendNumber) {
        this.sendNumber = sendNumber;
    }
}
