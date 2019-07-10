package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.pay.PayDto;
import com.soufang.base.enums.PayStatusEnum;
import com.soufang.base.enums.PayTypeEnum;
import com.soufang.base.utils.DateUtils;
import com.soufang.service.PayService;
import com.soufang.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: chen
 * @Date: 2019/5/22
 * @Description:
 */
@RestController
@RequestMapping("/core/pay")
public class PayController {

    @Autowired
    PayService payService;

    @Autowired
    SysParamService sysParamService;

    //如果已经有，则查询，没有就新增
    @RequestMapping(value = "createPay", method = RequestMethod.POST)
    public PayDto createPay(@RequestBody PayDto payDto){
        payDto.setPayNumber(sysParamService.getPayNumber());
        payDto.setCreateTime(DateUtils.getToday());
        payDto.setPayType(PayTypeEnum.zhifubao.getValue());
        payService.createPay(payDto);
        return payDto ;
    }

    @RequestMapping(value = "getByPayNumber", method = RequestMethod.POST)
    public PayDto getByPayNumber(@RequestBody String payNumber){
        return payService.getByPayNumber(payNumber);
    }
}
