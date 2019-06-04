package com.soufang.controller;


import com.soufang.base.PropertiesParam;
import com.soufang.base.Result;
import com.soufang.base.dto.address.AddressDto;
import com.soufang.base.dto.contract.ContractDto;
import com.soufang.base.dto.order.OrderDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.pay.PayDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.enums.OrderStatusEnum;
import com.soufang.base.enums.OrderTypeEnum;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.order.OrderSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.utils.FtpClient;
import com.soufang.base.utils.WordUtils;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.OrderFeign;
import com.soufang.feign.PcAddressFeign;
import com.soufang.feign.ProductFeign;
import com.soufang.vo.BaseVo;
import com.soufang.vo.order.OrderListVo;
import com.soufang.vo.order.OrderSubmitReqVo;
import com.soufang.vo.order.OrderUpdateStatusReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("orderManager/")
public class OrderController extends BaseController {
    @Autowired
    OrderFeign orderFeign;

    @Autowired
    PcAddressFeign pcAddressFeign;

    @Autowired
    ProductFeign productFeign;

    @Value("${upload.contract}")
    private String uploadContract;

    @MemberAccess
    @RequestMapping(value = "toOrderManager", method = RequestMethod.GET)
    public String toOrderManager(HttpServletRequest request, ModelMap model, Integer orderStatus, Integer orderType
            , Integer pageIndex) {
        UserDto userInfo = this.getUserInfo(request);
        OrderSo orderSo = new OrderSo();
        orderSo.setUserId(userInfo.getUserId());
        if (orderStatus != null && orderStatus != 0) {
            orderSo.setOrderStatus(orderStatus);
        }
        if (orderType != null && orderType == 3) {
            orderSo.setOrderType(3);
        }
        orderSo.setLimit(5);
        orderSo.setPage(1);
        PageHelp<OrderShopDto> pageHelp = orderFeign.getList(orderSo);
        model.put("orderStatus", orderStatus == null ? 0 : orderStatus);
        model.put("orderType", orderType == null ? 0 : orderType);
        model.put("orderShopList", pageHelp.getData());
        model.put("orderCount",pageHelp.getCount());
        return "personalCenter/order";
    }


    /**
     *  检索页面 ajax请求刷订单页面
     * @param request
     * @param orderType
     * @param orderStatus
     * @param pageIndex
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "clickPage", method = RequestMethod.GET)
    public Map<String, Object> searchAssortWithSort(HttpServletRequest request, Integer orderType, Integer orderStatus, Integer pageIndex) {
        Map<String, Object> map = new HashMap<>();
        UserDto userInfo = this.getUserInfo(request);
        OrderSo orderSo = new OrderSo();
        orderSo.setUserId(userInfo.getUserId());
        if (orderStatus != null && orderStatus != 0) {
            orderSo.setOrderStatus(orderStatus);
        }
        if (orderType != null && orderType == 3) {
            orderSo.setOrderType(3);
        }
        orderSo.setLimit(5);
        orderSo.setPage(pageIndex);

        PageHelp<OrderShopDto> pageHelp = orderFeign.getList(orderSo);

        // 订单
        map.put("list", pageHelp.getData());
        return map;
    }


    @MemberAccess
    @RequestMapping(value = "toDetail/{orderShopId}", method = RequestMethod.GET)
    public String toDetail(HttpServletRequest request, ModelMap model, @PathVariable Long orderShopId) {
        OrderShopDto orderShopDto = orderFeign.getDetail(orderShopId);
        for (OrderProductDto orderProductDto : orderShopDto.getOrderProducts()) {
            orderProductDto.setProductImage(orderProductDto.getProductImage());
        }
        model.put("order", orderShopDto);
        model.put("user", getUserInfo(request));
        return "personalCenter/orderDetail";
    }

    /**
     * 从产品详情直接提交订单
     *
     * @param reqVo
     * @param request
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "submitOrder", method = RequestMethod.POST)
    public String submitOrder(@ModelAttribute OrderSubmitReqVo reqVo, HttpServletRequest request) {

        OrderDto orderDto = new OrderDto();
        UserDto userInfo = getUserInfo(request);

        // 封装订单店铺
        orderDto.setUserId(userInfo.getUserId());
        orderDto.setBuyerName(userInfo.getUserName());
        AddressDto addressDto = pcAddressFeign.getAddressById(reqVo.getAddressId());
        orderDto.setTakeName(addressDto.getLinkName());
        orderDto.setTakeAddress(addressDto.getCountry()+addressDto.getDetailAddress());
        orderDto.setTakePhone(addressDto.getLinkPhone());
        orderDto.setOrderType(reqVo.getPayType() == 0 ? OrderTypeEnum.OFF_LINE.getValue() : OrderTypeEnum.ON_LINE.getValue());
        orderDto.setOrderRemark(reqVo.getRemark());
        orderDto.setCreateTime(DateUtils.getToday());
        BigDecimal totalMoney = BigDecimal.ZERO;

        // 查询颜色规格对应的产品信息
        ProductDto so = new ProductDto();
        so.setProductId(reqVo.getProductId());
        so.setProductNumber(reqVo.getProductNumber());
        so.setProductSpec(reqVo.getProductSpecName());
        ProductDto productDto = productFeign.getProductDetailBySpec_Number(so);

        // 封装订单店铺信息
        List<OrderShopDto> orderShopDtoList = new ArrayList<>();
        OrderShopDto orderShopDto = new OrderShopDto();
        orderShopDto.setUserId(userInfo.getUserId());
        orderShopDto.setShopId(productDto.getShopId());
        orderShopDto.setShopName(productDto.getShopName());
        BigDecimal sumMoney = BigDecimal.ZERO;

        // 封装店铺产品信息
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setProductId(productDto.getProductId());
        orderProductDto.setProductName(productDto.getProductName());
        orderProductDto.setProductNumber(new BigDecimal(reqVo.getProductNumber()));
        orderProductDto.setProductUnit(productDto.getProductUnit());
        orderProductDto.setProductPrice(productDto.getProductSpecDto().getSpecNumber());
        orderProductDto.setProductColor(reqVo.getProductColor());
        orderProductDto.setProductSpec(reqVo.getProductSpecName());
        sumMoney = sumMoney.add(orderProductDto.getProductNumber().multiply(orderProductDto.getProductPrice()));
        orderProductDtos.add(orderProductDto);
        orderShopDto.setOrderProducts(orderProductDtos);
        orderShopDto.setSumPrice(sumMoney);
        totalMoney = totalMoney.add(sumMoney);
        orderShopDto.setCreateTime(DateUtils.getToday());
        orderShopDtoList.add(orderShopDto);

        orderDto.setOrderShopDtoList(orderShopDtoList);
        orderDto.setTotalMoney(totalMoney);

        //生成订单
        orderFeign.createOrder(orderDto);

        return "redirect:toOrderManager?page=0&number=1";//重定向
    }


    //生成支付订单，前往支付
    @MemberAccess
    @RequestMapping(value = "pay/{orderShopId}", method = RequestMethod.GET)
    public String selectPayType(ModelMap model, @PathVariable Long orderShopId) {
        OrderShopDto orderShopDto = orderFeign.getDetail(orderShopId);
        if (orderShopDto.getOrderShopStatus() == OrderStatusEnum.to_pay.getValue()) {
            //付定金，重新生成订单
            orderFeign.payOrder(orderShopDto);
            orderShopDto = orderFeign.getDetail(orderShopId);
            model.put("payMoney", orderShopDto.getActualPrice().multiply(new BigDecimal("0.3")));
        } else if (orderShopDto.getOrderShopStatus() == OrderStatusEnum.to_pay_last.getValue()) {
            model.put("payMoney", orderShopDto.getActualPrice().multiply(new BigDecimal("0.7")));
        } else {
            return "404";
        }
        PayDto payDto = new PayDto();
        payDto.setOrderNumber(orderShopDto.getOrderNumber());
        payDto.setOrderType(orderShopDto.getOrderDto().getOrderType());
        payDto.setUserId(orderShopDto.getUserId());
        String productMessage = "";
        for (OrderProductDto orderProductDto : orderShopDto.getOrderProducts()) {
            productMessage += orderProductDto.getProductName() + " ";
        }
        payDto.setPayMoney(new BigDecimal(model.get("payMoney").toString()));
        payDto = orderFeign.createPay(payDto);
        model.put("payNumber", payDto.getPayNumber());
        model.put("orderName", orderShopDto.getOrderNumber());
        model.put("productMessage", productMessage);
        return "ali/alipay";
    }


    //下载合同
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "downContract/{orderShopId}", method = RequestMethod.GET)
    public BaseVo downContract(@PathVariable Long orderShopId) {
        BaseVo baseVo = new BaseVo();
        OrderShopDto orderShopDto = orderFeign.getDetail(orderShopId);
        ContractDto contractDto = new ContractDto();
        contractDto.setOrderNumber(orderShopDto.getOrderNumber());
        contractDto.setOrderShopId(orderShopDto.getOrderShopId());
        contractDto = orderFeign.getDetail(contractDto);
        if (contractDto != null && contractDto.getContractDownUrl() != null) {
            baseVo.setMessage(PropertiesParam.file_pre + contractDto.getContractDownUrl());
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("${sub}", orderShopDto.getOrderNumber());
            map.put("${item1}", "湖南大学");
            map.put("${item2}", "湖南");
            Map<String, Object> resultMap = WordUtils.readwriteWord(map);
            if ((boolean) resultMap.get("success")) {
                ContractDto dto = new ContractDto();
                dto.setOrderNumber(orderShopDto.getOrderNumber());
                dto.setOrderShopId(orderShopId);
                dto.setContractDownUrl(resultMap.get("uploadName").toString());
                orderFeign.down(dto);
                baseVo.setMessage(PropertiesParam.file_pre + resultMap.get("uploadName").toString());
            } else {
                baseVo.setSuccess(false);
                baseVo.setMessage("失败，再试一次吧");
            }
        }
        return baseVo;
    }

    //上传合同
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "uploadContract", method = RequestMethod.POST)
    public BaseVo uploadContract(HttpServletRequest request, MultipartFile file) {
        BaseVo baseVo = new BaseVo();
        String orderNumber = request.getParameter("orderNumber");
        Long orderShopId = Long.valueOf(request.getParameter("orderShopId"));
        ContractDto contractDto = new ContractDto();
        contractDto.setOrderShopId(orderShopId);
        contractDto.setOrderNumber(orderNumber);
        contractDto = orderFeign.getDetail(contractDto);
        if (contractDto != null && contractDto.getContractDownUrl() != null) {
            Map<String, Object> map = FtpClient.uploadImage(file, uploadContract);
            if ((boolean) map.get("success")) {
                contractDto.setContractUploadUrl(map.get("uploadName").toString());
                orderFeign.upload(contractDto);
                OrderShopDto orderShopDto = new OrderShopDto();
                orderShopDto.setOrderShopId(orderShopId);
                orderShopDto.setOrderShopStatus(OrderStatusEnum.review.getValue());
                orderFeign.updateOrderStatus(orderShopDto);
            } else {
                baseVo.setSuccess(false);
                baseVo.setMessage("失败，再试一次吧");
            }
        } else {
            baseVo.setSuccess(false);
            baseVo.setMessage("请先下载合同");
        }
        return baseVo;
    }

    /**
     * 更新订单页面状态
     *
     * @param reqVo
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "updStatus", method = RequestMethod.POST)
    @ResponseBody
    public OrderListVo updateOrderStatus(HttpServletRequest request, @RequestBody OrderUpdateStatusReqVo reqVo) {
        // 订单店铺 dto 封装
        OrderShopDto orderShopDto = new OrderShopDto();
        orderShopDto.setOrderShopId(reqVo.getOrderShopId());

        OrderListVo vo = new OrderListVo();
        // 订单页面传值状态(0:取消订单)
        Integer status = reqVo.getStatus();

        switch (status) {

            case 0:
                orderShopDto.setOrderShopStatus(OrderStatusEnum.cancel.getValue());
                break;
            case -1:
                orderShopDto.setOrderShopStatus(OrderStatusEnum.del.getValue());
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                break;


        }
        Result result = orderFeign.updateOrderStatus(orderShopDto);
        if (result.isSuccess()) {


            UserDto userInfo = this.getUserInfo(request);
            OrderSo so = new OrderSo();
            so.setUserId(userInfo.getUserId());
            if (reqVo.getOrderStatus() != null && reqVo.getOrderStatus() != 0) {
                so.setOrderStatus(reqVo.getOrderStatus());
            }
            if (reqVo.getOrderType() != null && reqVo.getOrderType() == 3) {
                so.setOrderType(3);
            }

            // 设置翻页
            switch (reqVo.getFlag() == null ? 0 : reqVo.getFlag()) {
                case 1:
                    so.setPage(reqVo.getEndPage() + 1);
                    break;
                case 2:
                    if(reqVo.getHitPage()==0){
                        so.setPage(reqVo.getHitPage()+1);
                    }else{
                        so.setPage(reqVo.getHitPage());
                    }

                    break;
                case -1:
                    so.setPage(reqVo.getStartPage() - 1);
                    break;
                default:
                    so.setPage(1);
                    break;
            }
            so.setLimit(5);
            PageHelp<OrderShopDto> list = orderFeign.getList(so);

            for (OrderShopDto temp : list.getData()) {
                temp.setStatusMessage(OrderStatusEnum.getByKey(orderShopDto.getOrderShopStatus()).getMessage());
                temp.setStatusColor(OrderStatusEnum.getByKey(orderShopDto.getOrderShopStatus()).getButtonColor());
                for (OrderProductDto orderProductDto : temp.getOrderProducts()) {
                    orderProductDto.setProductImage(orderProductDto.getProductImage());
                }
            }
            // page 翻页
            if (reqVo.getStartPage() == null) {
                vo.setStartPage(0);
            } else {
                vo.setStartPage(reqVo.getStartPage());
            }
            if (reqVo.getEndPage() == null) {
                vo.setEndPage(0);
            } else {
                vo.setEndPage(reqVo.getEndPage());
            }
            if (reqVo.getFlag() == null) {
                vo.setFlag(1);
            } else {
                vo.setFlag(reqVo.getFlag());
            }
            if (reqVo.getHitPage() == null) {
                vo.setHitPage(0);
            } else {
                vo.setHitPage(reqVo.getHitPage());
            }
            vo.setNumber(reqVo.getNumber());
            vo.setOrderStatus(reqVo.getOrderStatus()==null?0:reqVo.getOrderStatus());
            vo.setOrderType(reqVo.getOrderType()==null?0:reqVo.getOrderType());
            double ceil = Math.ceil(new Double(list.getCount()) / 5);
            vo.setTotalPages((int) ceil);
            vo.setList(list.getData());

        }
        return vo;

    }


}
