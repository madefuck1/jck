package com.soufang.model;

import java.math.BigDecimal;

public class Account {
    private Long accountId;

    private Long userId;

    private BigDecimal totalMoney;

    private BigDecimal freezeMoney;

    private BigDecimal cashOutMoney;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(BigDecimal freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public BigDecimal getCashOutMoney() {
        return cashOutMoney;
    }

    public void setCashOutMoney(BigDecimal cashOutMoney) {
        this.cashOutMoney = cashOutMoney;
    }
}