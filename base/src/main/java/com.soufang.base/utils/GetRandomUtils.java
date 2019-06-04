package com.soufang.base.utils;

import java.util.Random;

public class GetRandomUtils {


    //生成四位数的随机数
    public static String getRandom(){
        int x ;
        Random ne=new Random();
        x=ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
        String VerCode = String.valueOf(x);
        return VerCode;
    }

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
