package com.soufang.service.impl;

import com.soufang.base.PropertiesParam;
import com.soufang.base.Result;
import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.AssessMapper;
import com.soufang.mapper.OrderMapper;
import com.soufang.mapper.OrderShopMapper;
import com.soufang.service.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("assessService")
public class AssessServiceImpl implements AssessService {

    @Autowired
    AssessMapper assessMapper;
    @Autowired
    OrderShopMapper orderShopMapper;
    @Autowired
    OrderMapper orderMapper;
    @Override
    public PageHelp<AssessDto> getList(AssessSo assessSo) {
        assessSo.setPage((assessSo.getPage() - 1) * assessSo.getLimit());
        List<AssessDto> list = assessMapper.getList(assessSo);
        for (AssessDto a :list) {
            a.setUserAvatar(PropertiesParam.file_pre+a.getUserAvatar());
        }
        int count = assessMapper.getCount(assessSo);
        PageHelp<AssessDto> pageHelp = new PageHelp<>();
        pageHelp.setCount(count);
        pageHelp.setData(list);
        return pageHelp;
    }

    @Override
    public Map<String, Integer> getCount(AssessSo assessSo) {
        int allCount = assessMapper.getCount(assessSo);
        assessSo.setType(3);
        int good = assessMapper.getCount(assessSo);
        assessSo.setType(2);
        int general = assessMapper.getCount(assessSo);
        assessSo.setType(1);
        int bad = assessMapper.getCount(assessSo);
        Map<String, Integer> map = new HashMap<>();
        map.put("allCount",allCount);
        map.put("good",good);
        map.put("general",general);
        map.put("bad",bad);
        return map;
    }

    @Override
    public OrderShopDto getOrderProduct(String orderNumber) {
        OrderShopDto orderShopDto = orderShopMapper.getOrderProduct(orderNumber);
        for ( OrderProductDto orderProductDto:orderShopDto.getOrderProducts() ) {
            orderProductDto.setImage(PropertiesParam.file_pre+orderProductDto.getImage());
        }
        return orderShopDto;
    }

    @Override
    public Long putAssess(List<AssessDto> assessDtos) {
        int i = 0;
        Result result = new Result();
        for (AssessDto a :assessDtos) {
            a.setCreateTime(DateUtils.getToday());
            assessMapper.insertSelective(a);
            i++;
        }
        //修改订单状态为已评价状态
        orderMapper.updateOrderStatus(assessDtos.get(0).getOrderNumber());
        //修改order_shop的order_shop_status为已评价
        orderShopMapper.updateOrderShopStatus(assessDtos.get(0).getOrderNumber());
        Long orderShopId = orderShopMapper.getOrderShopId(assessDtos.get(0).getOrderNumber());
        return orderShopId;
    }


}

