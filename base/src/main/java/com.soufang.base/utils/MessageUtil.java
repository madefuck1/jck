package com.soufang.base.utils;

import com.soufang.base.sms.SendCodeTool;
import com.soufang.base.sms.SmsSendResponse;

public class MessageUtil {
    private static String account = "N625423_N9724353";
    private static String password = "WR1VndhAgl22b8";
    private static String smsSingleRequestServerUrl = "http://smsbj1.253.com/msg/send/json";
    private static String report = "true";
    private static String sign = "【进出口产品交易网】";

    public static SmsSendResponse setMessage(String content, String phone) {
        SmsSendResponse smsSendResponse = SendCodeTool.setCode(account, password, smsSingleRequestServerUrl, sign+content, phone, report);
        return smsSendResponse;
    }

    public static void main(String[] args){
        SmsSendResponse smsSendResponse = setMessage("ceshi","13867556162");
        System.out.println(smsSendResponse.getTime());
        System.out.println(smsSendResponse.getMsgId());
        System.out.println(smsSendResponse.getCode());
    }

}
