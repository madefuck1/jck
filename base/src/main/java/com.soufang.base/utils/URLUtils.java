package com.soufang.base.utils;

import com.soufang.base.PropertiesParam;
import org.apache.commons.lang.StringUtils;

/**
 * @Auther: chen
 * @Date: 2019/6/15
 * @Description:
 */
public class URLUtils {

    public static String addPrefix(String url){
        StringBuffer result = new StringBuffer();
        if(StringUtils.isNotBlank(url)){
            String[] list = url.split(";");
            if(list.length > 0){
                for (int i = 0; i < list.length ; i++) {
                    result.append(PropertiesParam.file_pre).append(list[i]).append(";");
                }
            }
        }
        return result.toString();
    }
}
