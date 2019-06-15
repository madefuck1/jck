package com.soufang.base.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: chen
 * @Date: 2019/6/14
 * @Description:
 */
public class IOClient {

    public static Map<String ,Object> uploadImage(MultipartFile file , String url) {
        Map<String ,Object> map = new HashMap<>();
        if(file == null){
            return null ;
        }
        try{
            String name = file.getOriginalFilename();
            String uploadName = new String(name.getBytes("UTF-8"), "iso-8859-1");
            String suffix = uploadName.substring(uploadName.lastIndexOf(".") + 1);
            uploadName = UUID.randomUUID().toString().replace("-", "") + "." + suffix;
            //File dest = new File("/www/upload"+url + "/" + url + "/" + uploadName);
            File dest = new File("D://" + url + "/" + uploadName);
            file.transferTo(dest);
            map.put("success",true);
            map.put("uploadName", url + "/" + uploadName);
            return map ;
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }
}
