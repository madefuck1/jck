package com.soufang.base.jiguang;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.RegisterPayload;
import cn.jmessage.api.user.UserClient;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.utils.GetRandomUtils;
import com.soufang.base.utils.MD5Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/5/8
 * @Description:
 */
public class JMessageUtils {

    //soufang
    private static final String appKey   = "7b9136215eff9a4897a673ea" ;
    private static final String masterSecret = "a6c0927d688716eca1d7973a";
    JMessageClient client = new JMessageClient(appKey, masterSecret);


    public static void main(String[] args){
        regPP1("egt_44","123456");
    }


    /**
     * signature = md5(appkey={appkey}&timestamp={timestamp}&random_str={random_str}&key={secret})
     * @return
     */
    public static JMessageDto getSinatureByPc(){
        JMessageDto messageDto = new JMessageDto();
        String randomStr = GetRandomUtils.getRandomString(25);
        Long timestamp = DateUtils.getDate();
        StringBuffer message = new StringBuffer();
        message.append("appkey=").append(appKey).append("&timestamp=").append(timestamp)
                .append("&random_str=").append(randomStr).append("&key=").append(masterSecret);
        messageDto.setSignature(MD5Utils.md5(message.toString()));
        messageDto.setAppkey(appKey);
        messageDto.setRandomStr(randomStr);
        messageDto.setTimestamp(timestamp);
        return messageDto;
    }


    //JMessage 注册账号
    public static void regPP1(String username,String password) {
        UserClient client = new UserClient(appKey, masterSecret);

        try {
            List<RegisterInfo> users = new ArrayList<RegisterInfo>();
            RegisterInfo user = RegisterInfo.newBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .build();
            users.add(user);
            RegisterInfo[] regUsers = new RegisterInfo[users.size()];

            RegisterPayload payload = RegisterPayload.newBuilder()
                    .addUsers(users.toArray(regUsers)).build();
            ResponseWrapper registerUsers = client.registerUsers(payload);

            System.out.println(registerUsers);
        } catch (APIConnectionException e) {
            System.out.print("连接错误。请稍后重试。 ");
        } catch (APIRequestException e) {
            System.out.print("JPush服务器的错误响应。请检查并改正。");
            System.out.print("网络状态: " + e.getStatus());
            System.out.print("错误信息: " + e.getMessage());
        }
    }
}
