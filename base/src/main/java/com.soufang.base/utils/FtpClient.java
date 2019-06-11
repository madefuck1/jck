package com.soufang.base.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
@PropertySource(value = "classpath:conf.properties")
public class FtpClient {

    @Value("${ftp.host}")
    private String ftpHost;
    @Value("${ftp.loginName}")
    private String ftpUserName;
    @Value("${ftp.password}")
    private String ftpPassword;
    @Value("${ftp.port}")
    private int ftpPort;
    @Value("${ftp.returnPort}")
    private int returnPort;


    private static String host;
    private static String userName;
    private static String password;
    private static int port;
    private static int rPort;

    @PostConstruct
    public void getApiToken() {
        host = this.ftpHost;
        userName = this.ftpUserName;
        password = this.ftpPassword;
        port = this.ftpPort;
        rPort = this.returnPort;
    }

    private static FTPClient INSTANCE;


    private static FTPClient getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new FTPClient();
                INSTANCE.connect(host, port);
                INSTANCE.login(userName, password);
                if (!FTPReply.isPositiveCompletion(INSTANCE.getReplyCode())) {
                    INSTANCE.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return INSTANCE;
    }

    /**
     * @param multipartFile 上传文件
     * @param url           ftp 文件路径
     * @return
     * @throws IOException
     */
    public static Map<String, Object> uploadImage(MultipartFile multipartFile, String url) {
        Map<String, Object> map = new HashMap<>();
        FTPClient ftpClient = getInstance();
        try {
            boolean success;
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode(); //被动模式
            ftpClient.changeWorkingDirectory(url);

            InputStream local = multipartFile.getInputStream();
            String name = multipartFile.getOriginalFilename();
            String uploadName = new String(name.getBytes("UTF-8"), "iso-8859-1");
            String suffix = uploadName.substring(uploadName.lastIndexOf(".") + 1);
            uploadName = UUID.randomUUID().toString().replace("-", "") + "." + suffix;
            success = ftpClient.storeFile(uploadName, local);
            local.close();
            ftpClient.logout();
            map.put("uploadName", url + "/" + uploadName);
            map.put("success", success);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            return map;
        }finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //上传合同
    public static Map<String, Object> uploadInvoice(InputStream inputStream, String url) {
        Map<String, Object> map = new HashMap<>();
        FTPClient ftpClient = getInstance();
        try {
            boolean success;
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(url);


            String uploadName = UUID.randomUUID().toString().replace("-", "") + ".doc";
            success = ftpClient.storeFile(uploadName, inputStream);
            inputStream.close();
            map.put("uploadName", url + "/" + uploadName);
            map.put("success", success);
            return map;
        } catch (Exception e) {
            map.put("success", false);
            return map;
        }finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
