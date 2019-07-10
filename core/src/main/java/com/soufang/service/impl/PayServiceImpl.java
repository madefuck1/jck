package com.soufang.service.impl;

import com.soufang.base.dto.pay.PayDto;
import com.soufang.base.enums.PayStatusEnum;
import com.soufang.mapper.PayMapper;
import com.soufang.model.Pay;
import com.soufang.service.PayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/22
 * @Description:
 */
@Service("payService")
public class PayServiceImpl implements PayService {

    @Autowired
    PayMapper payMapper;

    @Override
    public PayDto createPay(PayDto payDto) {
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDto,pay);
        pay.setPayStatus(PayStatusEnum.to_pay.getValue());
        List<Pay> list = payMapper.selectByOrderNumberAndStatus(pay);
        if(list != null && list.size() > 0){
            //如果已经存在，则不需要新增
            pay = list.get(0);
            BeanUtils.copyProperties(pay,payDto);
        }else {
            payMapper.insertSelective(pay);
        }
        return payDto;
    }

    @Override
    public void paySuccess(PayDto payDto) {
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDto,pay);
        pay.setPayStatus(PayStatusEnum.success.getValue());
        payMapper.updateByPrimaryKey(pay);
    }

    @Override
    public PayDto getByPayNumber(String payNumber) {
        Pay pay = payMapper.selectByPayNumber(payNumber);
        PayDto payDto = new PayDto();
        BeanUtils.copyProperties(pay,payDto);
        return payDto;
    }
}
