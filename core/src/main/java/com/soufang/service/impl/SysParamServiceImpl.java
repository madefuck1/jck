package com.soufang.service.impl;

import com.soufang.mapper.SysParamMapper;
import com.soufang.model.SysParam;
import com.soufang.service.SysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: chen
 * @Date: 2019/3/13
 * @Description:
 */
@Service
public class SysParamServiceImpl implements SysParamService {

    @Autowired
    SysParamMapper sysParamMapper;

    private static String pre_order = "D";
    private static String order_type = "order";
    private static String pre_pay = "P";
    private static String pay_type = "pay";
    private static String pre_refund = "R";
    private static String refund_type = "refund";
    private static String pre_unite = "U";
    private static String unite_type = "unite";
    private static String purchase_type = "purchase";
    private static String offer_type = "offer";

    @Override
    public String getOrderNumber() {
        return getByType(order_type, pre_order);
    }

    @Override
    public String getPurchaseNumber() {
        return getByType(purchase_type);
    }

    @Override
    public String getOfferNumber() {
        return getByType(offer_type);
    }


    @Override
    public String getPayNumber() {
        return getByType(pay_type, pre_pay);
    }

    @Override
    public String getRefundNumber() {
        return getByType(refund_type, pre_refund);
    }

    @Override
    public String getUnitePayOrderNumber() {
        return makeOrderNum();
    }

    private String getByType(String type, String pre) {

        synchronized (this) {

            StringBuffer number = new StringBuffer();
            SysParam systemParam = sysParamMapper.selectByCode(type);
            Long sqlDate = Long.valueOf(systemParam.getValue().substring(1)) / 100000;
            Long nowDate = Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            if (sqlDate.intValue() == nowDate.intValue()) {
                number.append(pre).append(Long.valueOf(systemParam.getValue().substring(1)) + 1);
                systemParam.setValue(number.toString());
            } else {
                number.append(pre).append(nowDate * 100000 + 1);
                systemParam.setValue(number.toString());
            }
            sysParamMapper.updateByPrimaryKey(systemParam);
            return number.toString();
        }
    }

    private String getByType(String type) {

        synchronized (this) {

            StringBuffer number = new StringBuffer();
            SysParam systemParam = sysParamMapper.selectByCode(type);
            Long sqlDate = Long.valueOf(systemParam.getValue().substring(1)) / 100000;
            Long nowDate = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            if (sqlDate.intValue() == nowDate.intValue()) {
                number.append(Long.valueOf(systemParam.getValue().substring(1)) + 1);
                systemParam.setValue(number.toString());
            } else {
                number.append(nowDate * 100000 + 1);
                systemParam.setValue(number.toString());
            }
            sysParamMapper.updateByPrimaryKey(systemParam);
            return number.toString();
        }
    }

    private static long orderNumCount = 0L;
    private int maxPerMSECSize = 999;

    private String makeOrderNum() {
        String finUniteOrderNum = "";
        try {
            // 最终生成的统一支付单号
            synchronized (this) {
                // 取系统当前时间作为统一支付单号变量前半部分,精确到毫秒
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                // 计数器到最大值归零,可扩展更大,目前1毫秒处理峰值1000个,1秒100万
                if (orderNumCount > maxPerMSECSize) {
                    orderNumCount = 0L;
                }
                //组装统一支付单号
                if (orderNumCount < 10) {
                    finUniteOrderNum = nowLong + "00" + orderNumCount;
                }
                if (orderNumCount > 9 && orderNumCount < 100) {
                    finUniteOrderNum = nowLong + "0" + orderNumCount;
                }
                if (orderNumCount > 99) {
                    finUniteOrderNum = nowLong + "" + orderNumCount;
                }
                orderNumCount++;
                return finUniteOrderNum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finUniteOrderNum;
    }

}
