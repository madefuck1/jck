package com.soufang.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/app/upload")
public class AppUploadController {
    // 对应上传路径从配置文件获取 这里随便写了一个
    @Value("${upload.user}")
    private String uploadUrl;

    /**
     * 上传文件列子
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public void uploadBusinessLicenseImage(HttpServletRequest request, MultipartHttpServletRequest requestFile) {
        String code = request.getParameter("code");
        List< MultipartFile > files = requestFile.getFiles("files");
        Map<String, Object> map = new HashMap<>();
        // 入参判断 file不能为空
//        if (file != null) {
            // 连接ftp并上传头像
//            map = FtpClient.uploadImage(file, uploadUrl);
//        }

        boolean success = (boolean) map.get("success");
        if (success) {
            // 上传成功
            String uploadName = (String) map.get("uploadName"); // 获取文件名称

        } else {
            // 上传失败
            // todo 业务逻辑代码
        }
    }


}
