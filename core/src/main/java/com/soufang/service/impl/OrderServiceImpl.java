package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.Result;
import com.soufang.base.dto.contract.ConfirmContractDto;
import com.soufang.base.dto.order.OrderDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.enums.OrderStatusEnum;
import com.soufang.base.enums.OrderTypeEnum;
import com.soufang.base.search.order.AddOrderProductSo;
import com.soufang.base.search.order.AddOrderShopSo;
import com.soufang.base.search.order.AddOrderSo;
import com.soufang.base.search.order.OrderSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.*;
import com.soufang.model.*;
import com.soufang.service.OrderService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/23
 * @Description:
 */
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderShopMapper orderShopMapper;

    @Autowired
    SysParamMapper sysParamMapper;

    @Autowired
    OrderProductMapper orderProductMapper;

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<OrderShopDto> getList(OrderSo orderSo) {

        // 设置页面起始数量
         int page = orderSo.getPage();
        page = (page - 1) * orderSo.getLimit();
        orderSo.setPage(page);
        List<OrderShop> list = orderShopMapper.getList(orderSo);
        List<OrderShopDto> data = new ArrayList<>();
        for (OrderShop shop : list) {
            OrderShopDto orderShopDto = new OrderShopDto();
            BeanUtils.copyProperties(shop, orderShopDto);
            List<OrderProductDto> orderProductDtos = new ArrayList<>();
            for (OrderProduct product : shop.getOrderProducts()) {
                OrderProductDto orderProductDto = new OrderProductDto();
                BeanUtils.copyProperties(product, orderProductDto);
                orderProductDtos.add(orderProductDto);
            }
             orderShopDto.setOrderProducts(orderProductDtos);
            data.add(orderShopDto);
        }
        return data;
    }

    @Override
    public int getCount(OrderSo orderSo) {
        return orderShopMapper.getCount(orderSo);
    }

    @Override
    @Transactional
    public void saveOrderProduct(AddOrderSo so, String orderNumber) {
        BigDecimal sumPrice;
        BigDecimal totalPrice = BigDecimal.ZERO;
        OrderShop orderShop;
        OrderProduct orderProduct;

        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setUserId(so.getBuyerId());
        order.setBuyerName(so.getBuyer());
        order.setTakeName(so.getTakeName());
        order.setTakeAddress(so.getTakeAddress());
        order.setTakePhone(so.getTakePhone());
        order.setOrderStatus(so.getOrderStatus());
        order.setPaidMoney(so.getPaidMoney());
        order.setOrderType(OrderTypeEnum.GUANG_JIAO_HUI.getValue());
        order.setSendTime(DateUtils.getDefaultDate());
        order.setCreateTime(DateUtils.getToday());
        // 订单店铺
        try {
            if (so.getOrderShop() == null) {
                throw new BusinessException("店铺信息没完善！");
            }
            for (AddOrderShopSo orderShopTemp : so.getOrderShop()) {
                orderShop = new OrderShop();
                orderShop.setOrderNumber(orderNumber);
                orderShop.setUserId(so.getBuyerId());
                orderShop.setShopId(orderShopTemp.getShopId());
                orderShop.setShopName(orderShopTemp.getShopName());
                orderShop.setSendName(orderShopTemp.getSendName());
                orderShop.setSendAddress(orderShopTemp.getSendAddress());
                orderShop.setSendPhone(orderShopTemp.getSendPhone());
                // orderShop.setFrontPrice();
                orderShop.setCreateTime(DateUtils.getToday());
                orderShop.setOrderShopStatus(so.getOrderStatus());
                orderShopMapper.insert(orderShop);
                sumPrice = BigDecimal.ZERO;
                // 订单产品
                if (orderShopTemp.getOrderProducts() == null) {
                    throw new BusinessException("产品信息没完善！");
                }
                for (AddOrderProductSo orderProductTemp : orderShopTemp.getOrderProducts()) {
                    orderProduct = new OrderProduct();
                    orderProduct.setProductId(99999999l);
                    orderProduct.setOrderShopId(orderShop.getOrderShopId());
                    orderProduct.setProductName(orderProductTemp.getProductName());
                    if (StringUtils.isNotBlank(orderProductTemp.getProductNumber())) {
                        orderProduct.setProductNumber(new BigDecimal(orderProductTemp.getProductNumber()));
                    } else {
                        orderProduct.setProductNumber(BigDecimal.ZERO);
                    }
                    orderProduct.setProductUnit(orderProductTemp.getProductUnit());
                    if (StringUtils.isNotBlank(orderProductTemp.getProductPrice())) {
                        orderProduct.setProductPrice(new BigDecimal(orderProductTemp.getProductPrice()));
                    } else {
                        orderProduct.setProductPrice(BigDecimal.ZERO);
                    }
                    orderProduct.setProductSpec(orderProductTemp.getProductSpec());
                    orderProduct.setProductColor(orderProductTemp.getProductColor());
                    sumPrice = sumPrice.add(orderProduct.getProductPrice() == null ? BigDecimal.ZERO : orderProduct.getProductPrice());
                    orderProductMapper.insert(orderProduct);

                }
                // 店铺订单总价
                orderShop.setSumPrice(sumPrice);
                orderShop.setActualPrice(sumPrice);
                totalPrice = totalPrice.add(sumPrice);
                orderShopMapper.updateByPrimaryKeySelective(orderShop);
            }
            // 订单总价
            order.setTotalMoney(totalPrice);
            orderMapper.insert(order);
        } catch (Exception e) {
            throw new BusinessException("填写信息错误！");
        }
    }


    @Override
    public void delOrder(Order order) {
        orderMapper.delOrder(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderDto dto, String orderNumber) {
        try{
            Order order = new Order();
            BeanUtils.copyProperties(dto,order);
            order.setOrderNumber(orderNumber);
            int status ;
            if(order.getOrderType() == OrderTypeEnum.OFF_LINE.getValue()){
                status = OrderStatusEnum.success.getValue();
            }else {
                status = OrderStatusEnum.to_submit.getValue();
            }
            order.setOrderStatus(status);
            order.setPaidMoney(BigDecimal.ZERO);
            orderMapper.insert(order);
            for(OrderShopDto orderShopDto : dto.getOrderShopDtoList()){
                OrderShop orderShop = new OrderShop();
                BeanUtils.copyProperties(orderShopDto,orderShop);
                orderShop.setOrderNumber(orderNumber);
                orderShop.setActualPrice(orderShop.getSumPrice());
                orderShop.setShopName(shopMapper.getById(orderShop.getShopId()).getShopName());
                orderShop.setFrontPrice(BigDecimal.ZERO);
                orderShop.setOrderShopStatus(status);
                orderShopMapper.insert(orderShop);
                for(OrderProductDto orderProductDto : orderShopDto.getOrderProducts()){
                    OrderProduct orderProduct = new OrderProduct();
                    BeanUtils.copyProperties(orderProductDto,orderProduct);
                    orderProduct.setOrderShopId(orderShop.getOrderShopId());
                    orderProductMapper.insert(orderProduct);
                }
            }
        }catch (Exception e){
            logger.info("订单生成失败："+e.toString());
            throw new BusinessException("订单生成失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(OrderShopDto orderShopDto, String orderNumber) {
        try {
            Order order = orderMapper.selectByPrimaryKey(orderNumber);
            order.setOrderNumber(orderNumber);
            order.setCreateTime(DateUtils.getToday());
            orderMapper.insert(order);
            orderShopDto.setOrderNumber(orderNumber);
            OrderShop orderShop = new OrderShop();
            BeanUtils.copyProperties(orderShopDto, orderShop);
            orderShopMapper.updateByPrimaryKey(orderShop);
        }catch (Exception e){
            logger.info("订单支付时，修改订单信息失败:"+e.getMessage());
            throw new BusinessException("订单支付失败");
        }
    }

    @Override
    @Transactional
    public void updateOrder(OrderShopDto orderShopDto) {
        try {
            OrderShop orderShop = orderShopMapper.selectByPrimaryKey(orderShopDto.getOrderShopId());
            orderShop.setActualPrice(orderShopDto.getActualPrice());
            orderShopMapper.updateByPrimaryKey(orderShop);
        }catch (Exception e){
            logger.info("卖家确认订单报错:"+e.getMessage());
            throw new BusinessException("店铺不存在");
        }
    }

    @Override
    @Transactional
    public void confirmOrder(ConfirmContractDto confirmContractDto) {
        try {
            //修改订单状态
            OrderShop orderShop = orderShopMapper.selectByPrimaryKey(confirmContractDto.getOrderShopId());
            orderShop.setOrderShopStatus(OrderStatusEnum.to_pay.getValue());
            orderShopMapper.updateByPrimaryKey(orderShop);
            //修改合同状态
            Contract temp = new Contract();
            temp.setOrderNumber(confirmContractDto.getOrderNumber());
            temp.setOrderShopId(confirmContractDto.getOrderShopId());
            Contract contract = contractMapper.selectByOrderNumberAndOrderShopId(temp);
            contract.setContractStatus(confirmContractDto.getContractStatus());
            contract.setContractReason(confirmContractDto.getContractReason());
            contract.setConfirmTime(DateUtils.getToday());
            contractMapper.updateByPrimaryKey(contract);
        }catch (Exception e){
            logger.info("确认合同报错:"+e.getMessage());
            throw new BusinessException("确认合同报错");
        }
    }

    @Override
    public OrderShopDto getDetail(Long orderShopId) {
        OrderShopDto orderShopDto = orderShopMapper.getDetail(orderShopId);
        return orderShopDto;
    }

    @Override
    public OrderDto getOrder(String orderNumber) {
        Order order = orderMapper.selectByPrimaryKey(orderNumber);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order,orderDto);
        return orderDto;
    }

    @Override
    public void paySuccess(String orderNumber) {
        try {
            Order order = orderMapper.selectByPrimaryKey(orderNumber);
            OrderSo orderSo = new OrderSo();
            orderSo.setOrderNumber(orderNumber);
            List<OrderShop> list = orderShopMapper.getList(orderSo);
            if(order.getOrderStatus() == OrderStatusEnum.to_pay.getValue()){
                order.setOrderStatus(OrderStatusEnum.product.getValue());
                orderMapper.updateByPrimaryKey(order);
                for(OrderShop orderShop :list){
                    orderShop.setOrderShopStatus(OrderStatusEnum.product.getValue());
                    orderShopMapper.updateByPrimaryKey(orderShop);
                }
            }else if(order.getOrderStatus() == OrderStatusEnum.to_pay_last.getValue()) {
                order.setOrderStatus(OrderStatusEnum.to_send.getValue());
                orderMapper.updateByPrimaryKey(order);
                for(OrderShop orderShop :list){
                    orderShop.setOrderShopStatus(OrderStatusEnum.to_send.getValue());
                    orderShopMapper.updateByPrimaryKey(orderShop);
                }
            }else {
                throw new BusinessException("订单状态不对");
            }
        }catch (Exception e){
            logger.info("支付成功失败："+e.getMessage());
            throw new BusinessException("订单状态不对");
        }

    }

    //查询用户自己的order_shop
    @Override
    public List<OrderShopDto> getUserOrderShopList(Long userId) {
        List<OrderShopDto> listDto = new ArrayList<>();
        List<OrderShop> list = orderShopMapper.getUserOrderShopList(userId);
        for (OrderShop oS:list) {
            OrderShopDto oSD = new OrderShopDto();
            BeanUtils.copyProperties(oS,oSD);
            listDto.add(oSD);
        }
        return listDto;
    }

    @Override
    public OrderDto getOrderDto(String orderNumber) {
        Order order = orderMapper.selectByPrimaryKey(orderNumber);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order,orderDto);
        return orderDto;
    }

    @Override
    public List<OrderProductDto> getOrderProductList(Long orderShopId) {
        List<OrderProductDto> list = new ArrayList<>();
        List<OrderProduct> oPList = orderProductMapper.getOPByOSId(orderShopId);
        for (OrderProduct oP:oPList) {
            OrderProductDto oPD = new OrderProductDto();
            BeanUtils.copyProperties(oP,oPD);
            list.add(oPD);
        }
        return list;
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product,productDto);
        return productDto;
    }

    @Override
    public Result updateOrderStatus(OrderShopDto orderShopDto) {
        OrderShop orderShop = new OrderShop();
        orderShop.setOrderShopId(orderShopDto.getOrderShopId());
        orderShop.setOrderShopStatus(orderShopDto.getOrderShopStatus());
        Result result = new Result();
        if( orderShopMapper.updateByPrimaryKeySelective(orderShop)>0){
            result.setSuccess(true);
            result.setMessage("取消成功！");
        }else{
            result.setSuccess(false);
            result.setMessage("取消失败！");
        }
        return result;
    }

    @Override
    public Result updateActualPrice(OrderShopDto orderShopDto) {
        OrderShop orderShop = new OrderShop();
        BeanUtils.copyProperties(orderShopDto,orderShop);
        Order order = new Order();
        order.setOrderNumber(orderShopDto.getOrderNumber());
        order.setTotalMoney(orderShopDto.getSumPrice());
        Result result = new Result();
        int i = orderShopMapper.updateActualPrice(orderShop);
        if(i > 0){
            result.setSuccess(true);
            result.setMessage("修改价格成功");
        }else {
            result.setSuccess(false);
            result.setMessage("修改价格失败");
        }
        return result;
    }

    @Override
    public Result sendProduct(OrderShopDto orderShopDto) {
        Result result = new Result();
        OrderShop orderShop = new OrderShop();
        BeanUtils.copyProperties(orderShopDto,orderShop);
        int i = orderShopMapper.sendProduct(orderShop);
        if(i > 0 ){
            result.setMessage("发货成功");
            result.setSuccess(true);
        }else {
            result.setMessage("发货失败");
            result.setSuccess(false);
        }
        return result;
    }

}
