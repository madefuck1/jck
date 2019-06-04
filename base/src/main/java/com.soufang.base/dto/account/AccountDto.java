package com.soufang.base.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AccountDto {

    private Long accountId;

    private Long userId;

    private BigDecimal totalMoney;

    private BigDecimal freezeMoney;

    private BigDecimal cashOutMoney;
}
