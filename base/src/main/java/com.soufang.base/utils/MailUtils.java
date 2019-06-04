package com.soufang.base.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Properties;

/**
 * @Auther: chen
 * @Date: 2019/5/5
 * @Description:
 */
public class MailUtils {

    public static String fromName = "【远恒科技】" ;
    public static String host = "smtphz.qiye.163.com" ;
    public static String from = "mktg@yuanhenghl.com";
    public static String password = "Yhjs<121";


    public static void main(String[] args){
        sendHtmlMail("504987307@qq.com","验证码","dadd");
    }

    public static void sendHtmlMail(String to , String title , String context) {
        try{
            String[] toList =new String[]{to};
            //设置服务器验证信息
            Properties prop = new Properties();
            prop.setProperty("mail.smtp.auth", "true");
            prop.setProperty("mail.smtp.timeout", "994"); // 加密端口(ssl)  可通过 https://qiye.163.com/help/client-profile.html 进行查询

            MailSSLSocketFactory sf = new MailSSLSocketFactory();// SSL加密
            sf.setTrustAllHosts(true); // 设置信任所有的主机
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            //设置邮件内容
            JavaMailSenderImpl javaMailSend = new JavaMailSenderImpl();
            MimeMessage message = javaMailSend.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
            String nick = MimeUtility.encodeText(fromName);//设置昵称
            messageHelper.setFrom(new InternetAddress(nick + " <"+from+">"));// 邮件发送者
            messageHelper.setTo(toList);  //接收方
            messageHelper.setSubject(title); //标题
            messageHelper.setText(context, true); // 内容

            //设置邮件服务器登录信息
            javaMailSend.setHost(host);
            javaMailSend.setUsername(from);
            javaMailSend.setPassword(password);
            javaMailSend.setJavaMailProperties(prop);
            javaMailSend.send(message);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
