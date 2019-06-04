package com.soufang.base.utils;

import com.soufang.base.sms.SendCodeTool;
import com.soufang.base.sms.SmsSendResponse;

public class MessageUtil {
    private static String account = "N341310_N9724353";
    private static String password = "F32Xk9gMqC6de7";
    private static String smsSingleRequestServerUrl = "http://smsbj1.253.com/msg/send/json";
    private static String report = "true";

    public static SmsSendResponse setMessage(String content, String phone) {
        SmsSendResponse smsSendResponse = SendCodeTool.setCode(account, password, smsSingleRequestServerUrl, content, phone, report);
        return smsSendResponse;
    }

    public static void main(String[] args){
        SmsSendResponse smsSendResponse = setMessage("【可可西里】ceshi","18785081470");
        System.out.println(smsSendResponse.getTime());
        System.out.println(smsSendResponse.getMsgId());
        System.out.println(smsSendResponse.getCode());
    }

}
