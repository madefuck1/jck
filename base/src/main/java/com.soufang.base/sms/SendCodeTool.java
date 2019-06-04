package com.soufang.base.sms;

import com.alibaba.fastjson.JSON;

public class SendCodeTool {

    public static final String charset = "utf-8";

    /**
     * @param account                   API账号
     * @param password                  API密码
     * @param smsSingleRequestServerUrl 变量短信发送的URL
     * @param msg                       设置您要发送的内容
     * @param phone                     手机号码
     * @param report                    状态报告
     * @return
     */
    public static SmsSendResponse setCode(String account, String password, String smsSingleRequestServerUrl, String msg, String phone, String report) {

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone, report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

        return smsSingleResponse;
    }
}
