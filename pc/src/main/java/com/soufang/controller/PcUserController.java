package com.soufang.controller;

import com.soufang.base.RedisConstants;
import com.soufang.base.Result;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.sms.SmsSendResponse;
import com.soufang.base.utils.*;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.PcUserFeign;
import com.soufang.vo.BaseVo;
import com.soufang.vo.User.LoginReqVo;
import com.soufang.vo.User.RegisterReqVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
@Controller
@RequestMapping("user/")
public class PcUserController extends BaseController{
    @Value("${upload.company}")
    private String uploadUrl;//公司营业照片上传地址
    private static int code_time = 60*3 ;//验证码有效时间

    @Autowired
    PcUserFeign pcUserFeign;

    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    public String toLogin(){
        return "/login/login";
    }

    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public BaseVo login(@RequestBody LoginReqVo loginReqVo , HttpServletResponse response){
        BaseVo baseVo = new BaseVo();
        UserDto userDto = new UserDto();
        String loginName = loginReqVo.getLoginName();
        if(loginName == null){
            baseVo.setMessage("用户名栏不能为空");
            baseVo.setSuccess(false);
            return baseVo;
        }
        userDto.setPhone(loginName);
        userDto.setEmail(loginName);
        userDto.setUserName(loginName);
        if(StringUtils.isNotBlank(loginReqVo.getPassword())){
            userDto.setPassWord(loginReqVo.getPassword());
        }
        Result result = pcUserFeign.login(userDto);
        if(result.isSuccess()){
            setToken(Long.valueOf(result.getMessage()),response);
            baseVo.setMessage("登录成功");
        } else {
            baseVo.setMessage("登录失败:"+result.getMessage());
            baseVo.setSuccess(false);
        }
        return baseVo;
    }

    @RequestMapping(value = "toRegister", method = RequestMethod.GET)
    public String toRegister(){
        return "/register/register";
    }

    //发送手机或者邮箱验证码
    @ResponseBody
    @RequestMapping(value = "sendCode",method = RequestMethod.POST)
    public BaseVo getCode(@RequestBody RegisterReqVo registerReqVo){
        BaseVo baseVo = new BaseVo();
        Result result = new Result();
        String VerCode = GetRandomUtils.getRandom();
        if(StringUtils.isNotBlank(registerReqVo.getPhone())){
           //发送手机验证码
            String phone = registerReqVo.getPhone();
            SmsSendResponse smsSendResponse = MessageUtil.setMessage("【可可西里】验证码："+VerCode,phone);
            if(smsSendResponse.getCode().equals("0")){
                //短信发送后，将信息保存在数据库t_message
                MessageDto messageDto = new MessageDto(phone,VerCode,0,DateUtils.getToday(),1);
                result = pcUserFeign.addMessage(messageDto);
                RedisUtils.setString(RedisConstants.verfity_code +phone,VerCode,code_time);
            }
        }else if(StringUtils.isNotBlank(registerReqVo.getEmail())){
            //发送邮箱验证码
            String email = registerReqVo.getEmail();
            MailUtils.sendHtmlMail(email,"邮箱验证码",VerCode);
            //邮件发送后，将信息保存在数据库t_message
            MessageDto messageDto = new MessageDto(email,VerCode,0,DateUtils.getToday(),1);
            result = pcUserFeign.addMessage(messageDto);
            RedisUtils.setString(RedisConstants.verfity_code+email,VerCode,code_time);
        }else {
            baseVo.setMessage("请输入手机号或邮箱");
            baseVo.setSuccess(false);
            return baseVo;
        }
        if(result.isSuccess()){
            baseVo.setMessage("发送成功");
        }else {
            baseVo.setMessage("发送失败");
            baseVo.setSuccess(false);
        }
        return baseVo;
    }
    //仅注册账号和密码
    @ResponseBody
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public BaseVo register(@RequestBody RegisterReqVo registerReqVo, HttpServletResponse response) {
        BaseVo baseVo = new BaseVo();
        UserDto userDto = new UserDto();
        Result result = new Result();
        String code = null;
        if(StringUtils.isNotBlank(registerReqVo.getEmail())){
            code = RedisUtils.getString(RedisConstants.verfity_code+registerReqVo.getEmail());
            userDto.setEmail(registerReqVo.getEmail());
        }else {
            code = RedisUtils.getString(RedisConstants.verfity_code+registerReqVo.getPhone());
            userDto.setPhone(registerReqVo.getPhone());
        }
        baseVo = verfityCode(code,registerReqVo.getCode());
        if(baseVo.isSuccess()){
            userDto.setPassWord(registerReqVo.getPassword());
            result = pcUserFeign.register(userDto);
            if(result.isSuccess()){
                this.setToken(Long.valueOf(result.getMessage()),response);
                baseVo.setMessage("注册成功");
                return baseVo;
            }else {
                baseVo.setSuccess(false);
                baseVo.setMessage("注册失败:"+result.getMessage());
                return baseVo;
            }
        }else {
            return baseVo;
        }
    }

    private BaseVo verfityCode(String code,String reCode){
        BaseVo baseVo = new BaseVo();
        if(code == null){
            baseVo.setSuccess(false);
            baseVo.setMessage("验证码过期");
        }else if(!code.equals(reCode)){
            baseVo.setSuccess(false);
            baseVo.setMessage("验证码错误");
        }
        return baseVo;
    }
    @MemberAccess
    @RequestMapping(value = "toAddInformation",method = RequestMethod.GET)
    public String toAddInformation(){
        return "register/addInformation";
    }

    //补充信息
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "addInformation",method = RequestMethod.POST)
    public BaseVo uploadImg(MultipartFile file,HttpServletRequest request){
        BaseVo baseVo = new BaseVo();
        UserDto userDto = this.getUserInfo(request);
        String loginName = request.getParameter("loginName");
        String vip = request.getParameter("vip");
        userDto.setUserName(loginName);
        CompanyDto companyDto = new CompanyDto();
        //修改个人登录名
        Result result  = pcUserFeign.update(userDto);
        //判断是否是供应商
        if(vip.equals("supplier")){
            //供应商注册
            companyDto.setUserId(userDto.getUserId());
            companyDto.setCompPhone(request.getParameter("companyPhone"));
            companyDto.setCompName(request.getParameter("companyName"));
            companyDto.setCompAddress(request.getParameter("companyAddress"));
            companyDto.setCompCorporate(request.getParameter("companyCorporate"));
            Map<String,Object> map = FtpClient.uploadImage(file,uploadUrl);
            if((boolean)map.get("success")){
                companyDto.setCompUrls(String.valueOf(map.get("uploadName")));
                result = pcUserFeign.addCompany(companyDto);
            }else {
                baseVo.setSuccess(false);
                baseVo.setMessage("营业执照上传失败");
                return baseVo;
            }
        }
        if(result.isSuccess()){
            baseVo.setMessage("注册成功");
        }else {
            baseVo.setSuccess(false);
            baseVo.setMessage("注册失败");
        }
        return baseVo;
    }

}
