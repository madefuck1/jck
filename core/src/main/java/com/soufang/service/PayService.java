package com.soufang.service;

import com.soufang.base.dto.pay.PayDto;

/**
 * @Auther: chen
 * @Date: 2019/5/22
 * @Description:
 */
public interface PayService {

    PayDto createPay(PayDto payDto);

    void paySuccess(PayDto payDto);

    PayDto getByPayNumber(String payNumber);
}
