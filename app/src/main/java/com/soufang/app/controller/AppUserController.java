package com.soufang.app.controller;


import com.alibaba.fastjson.JSONObject;
import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.vo.AppVo;
import com.soufang.app.vo.shop.DetailVo;
import com.soufang.app.vo.suggest.SuggestVo;
import com.soufang.app.vo.user.*;
import com.soufang.base.RedisConstants;
import com.soufang.base.Result;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.suggest.SuggestDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.enums.OauthTypeEnum;
import com.soufang.base.sms.SmsSendResponse;
import com.soufang.base.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("app/user/")
public class AppUserController extends AppBaseController {

    @Value("${upload.company}")
    private String uploadUrl;//公司营业照片上传地址
    @Value("${upload.user}")
    private String userAvatar;//用户头像上传地址
    private static int code_time = 60*3 ;//验证码有效时间
    private static int register_time = 60*10;//注册时token有效时间
    private static int login_time = 60*60*24*7 ;//登录有效时间

    //微信登录
    @ResponseBody
    @RequestMapping(value = "weChatLogin", method = RequestMethod.POST)
    public UserVo weChatLogin(@RequestBody OauthVo oauthVo) {
        Map<Object,Object> map = new HashMap<>();
        String openid = OauthUtils.getOauth_openid("weChat","app",oauthVo.getCode());
        /*String token = jsonObject.getString("access_token");*/
        map.put("openid",openid);
        map.put("oauthType",OauthTypeEnum.weChat.getValue());
        Map<String,Object> map1 = new HashMap<>();
        UserVo userVo = new UserVo();
        //通过openid拿到用户信息
        UserDto userInfo = appUserFeign.getUserByOpenId(map);
        if(StringUtils.isNotBlank(userInfo.getPhone())&&userInfo.getOauthType()==1){
            //用户已绑定微信,直接登录
            String token = UUID.randomUUID().toString().replace("-", "");
            RedisUtils.setString(token,String.valueOf(userInfo.getUserId()),login_time);
            userVo.setSuccess(true);
            userVo.setMessage("登录成功");
            userVo.setCode("100");
            map1.put("token",token);
            map1.put("alias","yhkj_"+userInfo.getUserId());
            userVo.setData(map1);
            return userVo;
        }else {
            //用户未绑定微信，现在开始第一次绑定微信
            userVo.setMessage("微信登录失败:请确认是否已绑定微信号！");
            userVo.setSuccess(false);
            userVo.setCode("1");
            return userVo;
        }
    }
    //绑定微信
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "bindWeChat", method = RequestMethod.POST)
    public UserVo BindWeChat(@RequestBody OauthVo oauthVo,HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);
        UserVo userVo = new UserVo();
        String openid = OauthUtils.getOauth_openid("weChat","app",oauthVo.getCode());
        /*String token = jsonObject.getString("access_token");*/
        userInfo.setOauthType(OauthTypeEnum.weChat.getValue());
        userInfo.setOauthId(openid);
        Map<Object,Object> map = new HashMap<>();
        map.put("openid",openid);
        map.put("oauthType",OauthTypeEnum.weChat.getValue());
        UserDto user = appUserFeign.getUserByOpenId(map);
        if(StringUtils.isNotBlank(user.getPhone())){
            //微信号已经绑定过,不能再绑定
            userVo.setCode("1");
            userVo.setSuccess(false);
            userVo.setMessage("微信号已经绑定过");
            return userVo;
        }
        Result result = appUserFeign.bindThirdInfo(userInfo);//Result
        if(result.isSuccess()){
            userVo.setCode("100");
        }else {
            userVo.setCode("1");
        }
        userVo.setSuccess(result.isSuccess());
        userVo.setMessage(result.getMessage());
        return userVo;
    }

    //绑定QQ
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "bindQQ", method = RequestMethod.POST)
    public UserVo bindQQ(@RequestBody OauthVo oauthVo,HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);
        UserVo userVo = new UserVo();
        String openid = oauthVo.getOpenid();
        userInfo.setOauthType(OauthTypeEnum.QQ.getValue());//类型2 表示QQ
        userInfo.setOauthId(openid);//保存 openid
        Result result = appUserFeign.bindThirdInfo(userInfo);//Result
        if(result.isSuccess()){
            userVo.setCode("100");
            userVo.setMessage("绑定成功");
        }else {
            userVo.setCode("1");
            userVo.setMessage("绑定失败");
        }
        userVo.setSuccess(result.isSuccess());
        return userVo;
    }

    //QQ登录
    @ResponseBody
    @RequestMapping(value = "QQLogin", method = RequestMethod.POST)
    public UserVo QQLogin(@RequestBody OauthVo oauthVo) {
        Map<Object,Object> map = new HashMap<>();
        String openid = oauthVo.getOpenid();
        map.put("openid",openid);
        map.put("oauthType",OauthTypeEnum.QQ.getValue());
        Map<String,Object> map1 = new HashMap<>();
        UserVo userVo = new UserVo();
        //通过openid拿到用户信息
        UserDto userInfo = appUserFeign.getUserByOpenId(map);
        if(StringUtils.isNotBlank(userInfo.getPhone())&&userInfo.getOauthType()==2){
            //用户已绑定QQ,直接登录
            String token = UUID.randomUUID().toString().replace("-", "");
            RedisUtils.setString(token,String.valueOf(userInfo.getUserId()),login_time);
            userVo.setSuccess(true);
            userVo.setMessage("登录成功");
            userVo.setCode("100");
            map1.put("token",token);
            map1.put("alias","yhkj_"+userInfo.getUserId());
            userVo.setData(map1);
            return userVo;
        }else {
            //用户未绑定微信，现在开始第一次绑定微信
            userVo.setMessage("QQ登录失败:请确认是否已绑定QQ号！");
            userVo.setSuccess(false);
            userVo.setCode("1");
            return userVo;
        }
    }

    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public UserVo login(@RequestBody LoginReqVo loginReqVo){
        UserVo userVo = new UserVo();
        UserDto userDto = new UserDto();
        Map<String,Object> map = new HashMap<>();
        if(!(StringUtils.isNotBlank(loginReqVo.getPhone()) )){
            userVo.setMessage("用户名栏不能为空");
            userVo.setSuccess(false);
            return userVo;
        }
        if(StringUtils.isNotBlank(loginReqVo.getPassword())){
            userDto.setPhone(loginReqVo.getPhone());
            userDto.setEmail(loginReqVo.getPhone());
            userDto.setUserName(loginReqVo.getPhone());
            userDto.setPassWord(loginReqVo.getPassword());
        }
        Result result = appUserFeign.login(userDto);
        if(result.isSuccess()){
            String token = UUID.randomUUID().toString().replace("-", "");
            RedisUtils.setString(token,String.valueOf(result.getMessage()),login_time);
            userVo.setSuccess(true);
            userVo.setMessage("登录成功");
            userVo.setCode("100");
            map.put("token",token);
            map.put("alias","yhkj_"+result.getMessage());
            userVo.setData(map);
        } else {
            userVo.setMessage("登录失败:"+result.getMessage());
            userVo.setSuccess(false);
            userVo.setCode("1");
        }
        return userVo;
    }
    //发送手机验证码
    @ResponseBody
    @RequestMapping(value = "sendCodeByPhone",method = RequestMethod.POST)
    public UserVo sendCodeByPhone(@RequestBody RegisterReqVo registerReqVo){
        String VerCode = GetRandomUtils.getRandom();
        UserVo userVo = new UserVo();
        String phone = registerReqVo.getPhone();
        MessageDto messageDto = new MessageDto();
        if(StringUtils.isNotBlank(phone)){
            //发送短信验证码
            SmsSendResponse smsSendResponse = MessageUtil.setMessage("【进出口产品交易网】验证码："+VerCode,phone);
            if(smsSendResponse == null || smsSendResponse.getCode() == null){
                userVo.setMessage("网络错误");
                userVo.setSuccess(false);
                return userVo;
            }else if(smsSendResponse.getCode().equals("4")){
                userVo.setMessage("手机号无效");
                userVo.setSuccess(false);
                return userVo;
            }else if(smsSendResponse.getCode().equals("0")){
                //短信发送后，将信息保存在数据库t_message
                messageDto.setPhone(phone);
                messageDto.setContent(VerCode);
                messageDto.setMesStatus(0);//发送成功
                messageDto.setMesType(1);//公司
                messageDto.setCreateTime(DateUtils.getToday());
                appUserFeign.addMessage(messageDto);
                RedisUtils.setString(RedisConstants.verfity_code +phone,VerCode,code_time);
                userVo.setSuccess(true);
                userVo.setCode("100");
                userVo.setMessage("发送成功");
                return userVo;
            }else {
                userVo.setMessage("发送失败");
                userVo.setSuccess(false);
                return userVo;
            }
        }else {
            userVo.setMessage("手机号不能为空");
            userVo.setSuccess(false);
            return userVo;
        }
    }

    //发送邮箱验证码
    @ResponseBody
    @RequestMapping(value = "sendCodeByEmail",method = RequestMethod.POST)
    public UserVo sendCodeByEmail(@RequestBody RegisterReqVo registerReqVo){
        String VerCode = GetRandomUtils.getRandom();
        Result result ;
        UserVo userVo = new UserVo();
        String email = registerReqVo.getEmail();
        if(email != null && email != ""){
            //发送邮箱验证码
            MailUtils.sendHtmlMail(email,"邮箱验证码",VerCode);
            //邮件发送后，将信息保存在数据库t_message
            MessageDto messageDto = new MessageDto();
            messageDto.setPhone(email);
            messageDto.setContent(VerCode);
            messageDto.setMesType(1);//公司
            messageDto.setMesStatus(0);//发送成功
            messageDto.setCreateTime(DateUtils.getToday());
            result =  appUserFeign.addMessage(messageDto);
            RedisUtils.setString(RedisConstants.verfity_code+email,VerCode,code_time);
            if(result.isSuccess()){
                userVo.setSuccess(true);
                userVo.setCode("100");
                userVo.setMessage("发送成功");
            }else {
                userVo.setSuccess(false);
                userVo.setMessage("发送失败");
                userVo.setCode("1");
            }
            return userVo;
        }else {
            userVo.setSuccess(false);
            userVo.setMessage("邮箱不能为空");
            userVo.setCode("1");
            return userVo;
        }
    }
    //手机注册
    @ResponseBody
    @RequestMapping(value = "registerByPhone",method = RequestMethod.POST)
    public UserVo registerByPhone(@RequestBody RegisterReqVo registerReqVo){
        UserDto userDto = new UserDto();
        Map<String,Object> map = new HashMap<>();
        UserVo userVo = new UserVo();
        if(StringUtils.isNotBlank(registerReqVo.getPhone())){
            userDto.setPhone(registerReqVo.getPhone());
        }
        if(StringUtils.isNotBlank(registerReqVo.getPassword())){
            userDto.setPassWord(registerReqVo.getPassword());
        }
        //如果验证码正确，则添加用户信息
        String code = null;
        if(RedisUtils.existsObject(RedisConstants.verfity_code+registerReqVo.getPhone())){
            code = RedisUtils.getString(RedisConstants.verfity_code+registerReqVo.getPhone());
        }else {
            userVo.setMessage("验证码过期");
            userVo.setSuccess(false);
            return userVo;
        }
        if(registerReqVo.getCode().equals(code)){
            Result result = appUserFeign.register(userDto);
            if(result.isSuccess()){
                String token = UUID.randomUUID().toString().replace("-", "");
                RedisUtils.setString(token, String.valueOf(result.getMessage()),register_time);
                userVo.setSuccess(true);
                userVo.setCode("100");
                userVo.setMessage("注册成功");
                map.put("token",token);
                map.put("alias","yhkj_"+result.getMessage());   //result.getMessage()保存的是注册用户的ID
                userVo.setData(map);
                RedisUtils.delkeyObject(RedisConstants.verfity_code+registerReqVo.getPhone());
                return userVo;
            }else {
                userVo.setSuccess(false);
                userVo.setMessage("注册失败："+result.getMessage());
                userVo.setCode("1");
                return userVo;
            }
        }else {
            userVo.setSuccess(false);
            userVo.setMessage("验证码不正确");
            userVo.setCode("1");
            return userVo;
        }
    }

    //邮箱注册
    @ResponseBody
    @RequestMapping(value = "registerByEmail",method = RequestMethod.POST)
    public UserVo registerByEmail(@RequestBody RegisterReqVo registerReqVo){
        UserVo userVo = new UserVo();
        UserDto userDto = new UserDto();
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(registerReqVo.getEmail())){
            userDto.setEmail(registerReqVo.getEmail());
        }
        if(StringUtils.isNotBlank(registerReqVo.getPassword())){
            userDto.setPassWord(registerReqVo.getPassword());
        }
        String code = null;
        if(RedisUtils.getString(RedisConstants.verfity_code+registerReqVo.getEmail()) != null){
            code = RedisUtils.getString(RedisConstants.verfity_code+registerReqVo.getEmail());
        }
        else {
            userVo.setMessage("验证码过期");
            userVo.setSuccess(false);
            return userVo;
        }
        //查看验证码是否正确
        if(registerReqVo.getCode().equals(code)){
            //添加用户信息
            Result result = appUserFeign.register(userDto);
            if(result.isSuccess()){
                String token = UUID.randomUUID().toString().replace("-", "");
                RedisUtils.setString(token, String.valueOf(result.getMessage()),register_time);
                userVo.setSuccess(true);
                userVo.setCode("100");
                map.put("token",token);
                map.put("alias","yhkj_"+result.getMessage());//result.getMessage()保存的是注册用户的ID
                userVo.setData(map);
                userVo.setMessage("注册成功");//成功后删除键值
                RedisUtils.delkeyObject(RedisConstants.verfity_code+registerReqVo.getEmail());
                return userVo;
            }else {
                userVo.setSuccess(false);
                userVo.setMessage("注册失败:"+result.getMessage());
                userVo.setCode("1");
                return userVo;
            }
        }else {
            userVo.setMessage("验证码不正确");
            userVo.setSuccess(false);
            return userVo;
        }
    }


    //补充信息
    @ResponseBody
    @RequestMapping(value = "updateCompany",method = RequestMethod.POST)
    public UserVo register(MultipartHttpServletRequest requestFile, HttpServletRequest request) {
        UserVo userVo = new UserVo();
        UserDto userDto = this.getUserInfo(request);
        CompanyDto companyDto = new CompanyDto();
        String loginName = request.getParameter("loginName");
        String companyName = request.getParameter("companyName");
        userDto.setUserName(loginName);
        if(companyName != null){
            //供应商注册
            companyDto.setCompName(companyName);
            companyDto.setCompAddress(request.getParameter("companyAddress"));
            companyDto.setCompLinker(request.getParameter("companyLinker"));
            companyDto.setCompCorporate(request.getParameter("companyCorporate"));
            companyDto.setUserId(userDto.getUserId());
            //List< MultipartFile > files = requestFile.getFiles("companyImage");
            MultipartFile file = requestFile.getFile("companyImage");
            Map<String, Object> map = new HashMap<>();
            if(file != null){
                map = FtpClient.uploadImage(file, uploadUrl);
            }else {
                userVo.setSuccess(false);
                userVo.setMessage("图片上传失败");
                userVo.setCode("1");
                return userVo;
            }
            companyDto.setCompUrls((String) map.get("uploadName"));
            Result result = appUserFeign.update(userDto);
            if(result.isSuccess()){
                //用户信息注册后，再注册公司信息
                if(appUserFeign.addCompany(companyDto).isSuccess()){
                    userVo.setMessage("注册共公司成功");
                    userVo.setSuccess(true);
                    userVo.setCode("100");
                    return userVo;
                }else {
                    userVo.setMessage("注册公司失败");
                    userVo.setSuccess(false);
                    userVo.setCode("1");
                    return userVo;
                }
            }else {
                userVo.setMessage("跟新个人用户名失败");
                userVo.setSuccess(false);
                userVo.setCode("1");
                return userVo;
            }
        }else {
            //采购商注册
            if(appUserFeign.update(userDto).isSuccess()){
                userVo.setSuccess(true);
                userVo.setMessage("采购商注册成功");
                userVo.setCode("100");
                return userVo;
            }else {
                userVo.setSuccess(false);
                userVo.setMessage("采购商注册失败");
                userVo.setCode("1");
                return userVo;
            }
        }
    }

    //忘记密码,通过手机号码找回
    @ResponseBody
    @RequestMapping(value = "forgetPasswordByPhone",method = RequestMethod.POST)
    public UserVo forgetPassWord(@RequestBody RegisterReqVo registerReqVo){
        String VerCode = GetRandomUtils.getRandom();
        Result result = new Result();
        UserVo userVo = new UserVo();
        String phone = registerReqVo.getPhone();
        if(StringUtils.isNotBlank(phone)){
            //发送短信验证码
            SmsSendResponse smsSendResponse = MessageUtil.setMessage("【进出口产品交易】验证码："+VerCode,phone);
            if(smsSendResponse.getCode().equals("0")){
                //短信发送后，将信息保存在数据库t_message
                MessageDto messageDto = new MessageDto();
                messageDto.setPhone(phone);
                messageDto.setContent(VerCode);
                messageDto.setCreateTime(DateUtils.getToday());
                result =  appUserFeign.addMessage(messageDto);
                RedisUtils.setString(RedisConstants.forget_code+phone,VerCode,code_time);
            }
        }
        if(result.isSuccess()){
            userVo.setSuccess(true);
            userVo.setMessage("发送成功");
            userVo.setCode("100");
        }else {
            userVo.setMessage("发送失败");
            userVo.setSuccess(false);
            userVo.setCode("1");
        }
        return userVo;
    }

    //验证验证码是否正确并通过手机号或者邮箱重新设置密码
    @ResponseBody
    @RequestMapping(value = "updatePasswordByPhone",method = RequestMethod.POST)
    public UserVo verfityCode(@RequestBody RegisterReqVo registerReqVo){
        UserVo userVo = new UserVo();
        String vCode = RedisUtils.getString(RedisConstants.forget_code+registerReqVo.getPhone());
        String code = null;
        UserDto userDto = new UserDto();
        if(StringUtils.isNotBlank(registerReqVo.getPhone())){
            userDto.setPhone(registerReqVo.getPhone());
        }
        if(StringUtils.isNotBlank(vCode)){
            code = vCode;
        }
        else {
            userVo.setMessage("验证码过期");
            userVo.setSuccess(false);
            return userVo;
        }
        //验证验证码是否正确
        if(registerReqVo.getCode().equals(code)){
            //验证码正确则修改用户密码
            userDto.setPassWord(MD5Utils.md5(registerReqVo.getPassword()));
            Result result = appUserFeign.update(userDto);
            if(result.isSuccess()){
                userVo.setSuccess(true);
                userVo.setMessage("修改密码成功");
                userVo.setCode("100");
            }else {
                userVo.setSuccess(false);
                userVo.setMessage("修改密码失败");
                userVo.setCode("1");
            }
        }else {
            userVo.setSuccess(false);
            userVo.setMessage("验证码错误");
            userVo.setCode("1");
        }
        return userVo;
    }

    //忘记密码,通过邮箱找回
    @ResponseBody
    @RequestMapping(value = "forgetPasswordByEmail",method = RequestMethod.POST)
    public UserVo forgetPasswordByEmail(@RequestBody RegisterReqVo registerReqVo){
        String VerCode = GetRandomUtils.getRandom();
        Result result = new Result();
        UserVo userVo = new UserVo();
        String email = registerReqVo.getEmail();
        if(StringUtils.isNotBlank(email)){
            //发送邮箱验证码
            MailUtils.sendHtmlMail(email,"邮箱验证码",VerCode);
            //邮箱发送后，将信息保存在数据库t_message
            MessageDto messageDto = new MessageDto();
            messageDto.setPhone(email);
            messageDto.setContent(VerCode);
            messageDto.setCreateTime(DateUtils.getToday());
            result =  appUserFeign.addMessage(messageDto);
            RedisUtils.setString(RedisConstants.forget_code+email,VerCode,code_time);
        }
        if(result.isSuccess()){
            userVo.setMessage("发送成功");
            userVo.setSuccess(true);
            userVo.setCode("100");
        }else {
            userVo.setMessage("发送失败");
            userVo.setSuccess(false);
            userVo.setCode("1");
        }
        return userVo;
    }

    //验证验证码是否正确并通邮箱重新设置密码
    @ResponseBody
    @RequestMapping(value = "updatePasswordByEmail",method = RequestMethod.POST)
    public UserVo updatePasswordByEmail(@RequestBody RegisterReqVo registerReqVo){
        UserVo userVo = new UserVo();
        String vCode = RedisUtils.getString(RedisConstants.forget_code+registerReqVo.getEmail());
        String code = null;
        UserDto userDto = new UserDto();
        if(StringUtils.isNotBlank(registerReqVo.getEmail())){
            userDto.setEmail(registerReqVo.getEmail());
        }
        if(StringUtils.isNotBlank(vCode)){
            code = vCode;
        }
        else {
            userVo.setMessage("验证码过期");
            userVo.setSuccess(false);
            return userVo;
        }
        //验证验证码是否正确
        if(registerReqVo.getCode().equals(code)){
            //验证码正确则修改用户密码
            userDto.setPassWord(MD5Utils.md5(registerReqVo.getPassword()));
            Result result = appUserFeign.update(userDto);
            if(result.isSuccess()){
                userVo.setMessage("修改密码成功");
                userVo.setSuccess(true);
                userVo.setCode("100");
            }else {
                userVo.setSuccess(false);
                userVo.setMessage("修改密码失败");
                userVo.setCode("1");
            }
        }else {
            userVo.setSuccess(false);
            userVo.setMessage("验证码错误");
            userVo.setCode("1");
        }
        return userVo;
    }

    //个人资料中得到个人资料
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public Information informationHtml(HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);
        Information information = new Information();
        information.setData(userInfo);
        information.setMessage("获取成功");
        information.setSuccess(true);
        return information;
    }
    //个人资料中个人信息修改
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    public AppVo updateInformation(MultipartHttpServletRequest requestFile, HttpServletRequest request){
        UserDto userInfo = this.getUserInfo(request);
        AppVo vo = new AppVo();
        UserDto userDto = new UserDto();
        Result result = new Result();
        String username = request.getParameter("realName");
        userDto.setUserId(userInfo.getUserId());
        userDto.setPhone("phone_");
        userDto.setEmail("email_");
        //判断是否要修改用户名
        if(StringUtils.isNotBlank(username)){
            //如果有，判断是否是第一次修改用户名
            if(!StringUtils.isNotBlank(userInfo.getUserName())){
                //如果是，判断用户名是否存在
                userDto.setUserName(username);
                Result result1 = appUserFeign.login(userDto);
                if(result1.isSuccess()){
                    vo.setMessage("用户名已存在，请换个用户名！");
                    vo.setSuccess(false);
                    return vo;
                }
            }else {
                //用户名已存在，必须换名
                vo.setSuccess(false);
                vo.setMessage("当前用户名已被修改过，不能再次修改！！！");
                return vo;
            }
        }
        userDto.setPhone(null);
        userDto.setEmail(null);
        MultipartFile file = requestFile.getFile("userAvatar");
        Map<String,Object> map = new HashMap<>();
        if(file != null){
            map = FtpClient.uploadImage(file,userAvatar);
            userDto.setUserAvatar(String.valueOf(map.get("uploadName")));
            if((boolean)map.get("success")){
                userDto.setUserAvatar(String.valueOf(map.get("uploadName")));
                result = appUserFeign.update(userDto);
            }else {
                result.setMessage("头像上传失败");
                result.setSuccess(false);
            }
        }else {
            result = appUserFeign.update(userDto);
        }
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }

    //绑定邮箱
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "BindEmail",method = RequestMethod.POST)
    public AppVo updateEmail(HttpServletRequest request,@RequestBody RegisterReqVo registerReqVo){
        AppVo vo = new AppVo();
        UserDto userInfo = this.getUserInfo(request);
        UserDto userDto = new UserDto();
        Result result = new Result();
        userDto.setUserId(userInfo.getUserId());
        if(StringUtils.isBlank(userInfo.getPhone())){
            vo.setMessage("请先完善个人信息，填写手机号");
            vo.setSuccess(false);
            return vo;
        }if(userInfo.getEmail().equals(registerReqVo.getEmail())){
            //该邮箱已经注册过用户，不能作为绑定邮箱
            vo.setMessage("该邮箱已经注册过用户，不能作为绑定邮箱");
            vo.setSuccess(false);
            return vo;
        }
        String code = null ;
        if(RedisUtils.getString(RedisConstants.verfity_code+registerReqVo.getEmail()) != null){
            code = RedisUtils.getString(RedisConstants.verfity_code+registerReqVo.getEmail());
        } else {
            vo.setMessage("验证码过期");
            vo.setSuccess(false);
            return vo;
        }
        if(code.equals(registerReqVo.getCode())){
            userDto.setEmail(registerReqVo.getEmail());
            result = appUserFeign.update(userDto);
        }else {
            vo.setMessage("验证码错误");
            vo.setSuccess(false);
            return vo;
        }
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }


    //修改密码
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "updatePassword",method = RequestMethod.POST)
    public AppVo updateInformation(HttpServletRequest request, @RequestBody UpdatePassword updatePassword){
        UserDto userInfo = this.getUserInfo(request);
        AppVo vo = new AppVo();
        UserDto userDto = new UserDto();
        Result result;
        userDto.setUserId(userInfo.getUserId());
        if(!(MD5Utils.md5(updatePassword.getOldPassword()).equals(userInfo.getPassWord()))){
            vo.setMessage("旧密码输入错误");
            vo.setSuccess(false);
            return vo;
        }else {
            userDto.setPassWord(MD5Utils.md5(updatePassword.getNewPassword()));
            result = appUserFeign.updatePassword(userDto);
        }
        vo.setSuccess(result.isSuccess());
        vo.setMessage(result.getMessage());
        return vo;
    }

    //用户反馈
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "saveSuggest", method = RequestMethod.POST)
    public Result saveSuggest(@RequestBody SuggestVo suggestVo, HttpServletRequest request) {;
        Result result = new Result();
        SuggestDto suggestDto = new SuggestDto();
        suggestDto.setCreateTime(DateUtils.getToday());
        suggestDto.setUserId(this.getUserInfo(request).getUserId());
        if(StringUtils.isBlank(suggestVo.getContent())){
            result.setSuccess(false);
            result.setMessage("反馈内容不能为空");
            return result;
        }
        suggestDto.setSugContent(suggestVo.getContent());
        result = appUserFeign.addSuggest(suggestDto);
        return result;
    }

    //判断用户是否有店铺
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "isHaveShop", method = RequestMethod.POST)
    public DetailVo isHaveShop(HttpServletRequest request) {
        DetailVo vo = new DetailVo();
        ShopDto shopInfo = this.getShopInfo(request);
        if(StringUtils.isNotBlank(shopInfo.getShopName())){
            vo.setSuccess(true);
            vo.setMessage("有相关的店铺信息");
            vo.setData(shopInfo);
            return vo;
        }else {
            vo.setSuccess(false);
            vo.setMessage("没有相关的店铺信息");
            vo.setData(null);
            return vo;
        }
    }

}
