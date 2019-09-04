package com.soufang.base.utils;


import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class OauthUtils {

    private static String weChat_pc_appid="wxf01a70ce62c5ac8e";
    private static String weChat_pc_secret = "1edffbeab1166899c06595b21eacea1a";
    private static String weChat_app_appid = "wx57def611f2da3fd5";
    private static String weChat_app_appSecret="b98ae7f8989b29e0a05935adb1c9ac5a";


    public static String getOauth_openid(String oauthType,String terminalType,String code){
        String openid = "";
        String result = "";
        String url = "";
        if(terminalType.equals("pc")){
            if(oauthType.equals("weChat")){
                url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+weChat_pc_appid+"&secret="+weChat_pc_secret+"&code="+code+"&grant_type=authorization_code";
            }else if(oauthType.equals("QQ")){
                url = "https://graph.qq.com/oauth2.0/me?access_token="+code;//获取QQ的openid
            }else if(oauthType.equals("weiBo")){

            }
        }else if(terminalType.equals("app")){
            if(oauthType.equals("weChat")){
                url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+weChat_app_appid+"&secret="+weChat_app_appSecret+"&code="+code+"&grant_type=authorization_code";
            }else if(oauthType.equals("QQ")){

            }else if(oauthType.equals("weiBo")){

            }
        }
        result = SendGet(url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        openid = jsonObject.getString("openid");
        return openid;
    }

    public static String SendGet(String url){
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            //打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            //建立实际的连接
            connection.connect();
            //获取所有响应的头字段
            Map<String,List<String>> map = connection.getHeaderFields();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line ;
            while ((line = in.readLine()) != null){
                result += line;
            }
        }catch (Exception e) {
            System.out.println("发送GET请求出现异常"+e);
            e.printStackTrace();
        }
        finally {
            //关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return result;
    }

    //截取字符串
    public static String splitData(String str, String strStart, String strEnd) {
        String tempStr;
        tempStr = str.substring(str.indexOf(strStart) + 1, str.lastIndexOf(strEnd));
        return tempStr;
    }
}
