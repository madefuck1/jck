package com.soufang.controller;

import com.soufang.Vo.order.OrderShopVo;
import com.soufang.base.Result;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.enums.OrderStatusEnum;
import com.soufang.base.enums.OrderTypeEnum;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.order.AddOrderSo;
import com.soufang.base.search.order.OrderSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.feign.AdminOrderFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


/**
 * @Auther: chen
 * @Date: 2019/4/23
 * @Description:
 */
@Controller
@RequestMapping(value = "admin/order")
public class OrderController {

    @Autowired
    AdminOrderFeign adminOrderFeign;

    @RequestMapping(value = "toList", method = RequestMethod.GET)
    public String getList(ModelMap map) {
        map.put("orderTypeMap", OrderTypeEnum.getAll());
        map.put("orderStatusMap", OrderStatusEnum.getAll());
        return "/order/list";
    }

    @RequestMapping(value = "toAddOrderList", method = RequestMethod.GET)
    public String toAddOrderList(ModelMap map) {
        map.put("orderStatusMap", OrderStatusEnum.getAll());
        return "/order/addOrderList";
    }

//    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public OrderShopVo getList(String orderNumber, Integer orderStatus, Long shopId, Integer orderType,
                               String beginDate, String endDate, Integer page, Integer limit) {
        OrderShopVo vo = new OrderShopVo();

        OrderSo so = new OrderSo();
        if (StringUtils.isNotBlank(orderNumber)) {
            so.setOrderNumber(orderNumber);
        }
        if (null != orderStatus) {
            so.setOrderStatus(orderStatus);
        }
        if (null != orderType) {
            so.setOrderType(orderType);
        }
        if (null != shopId) {
            so.setShopId(shopId);
        }
        try {
            if (StringUtils.isNotBlank(beginDate)) {
                so.setBeginDate(DateUtils.string2Date(beginDate, DateUtils.format2));
            }
            if (StringUtils.isNotBlank(endDate)) {
                so.setEndDate(DateUtils.string2Date(endDate, DateUtils.format2));
            }
        } catch (Exception e) {

        }
        so.setLimit(limit);
        so.setPage(page);
        PageHelp<OrderShopDto> pageHelp = adminOrderFeign.getList(so);
        vo.setCount(pageHelp.getCount());
        vo.setData(pageHelp.getData());
        return vo;
    }


    @RequestMapping(value = "addOrder", method = RequestMethod.GET)
    public String addOrder(ModelMap map) {
        map.put("orderStatusMap", OrderStatusEnum.getAll());
        return "/order/addOrder";
    }

    @RequestMapping(value = "openAddProduct", method = RequestMethod.GET)
    public String openAddProduct() {
        return "/order/addOrderProduct";
    }

    @ResponseBody
    @RequestMapping(value = "saveOrderProduct", method = RequestMethod.POST)
    public Result saveOrderProduct(@RequestBody AddOrderSo so) {
        Result result = adminOrderFeign.saveOrderProduct(so);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "delOrder", method = RequestMethod.POST)
    public Result delOrder(@RequestParam String orderNumber) {
        Result result = adminOrderFeign.delOrder(orderNumber);
        return result;
    }


}
