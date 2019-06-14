package com.soufang.controller;

import com.soufang.base.PropertiesParam;
import com.soufang.base.RedisConstants;
import com.soufang.base.Result;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.enums.ShopStatusEnum;
import com.soufang.base.sms.SmsSendResponse;
import com.soufang.base.utils.*;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.PcUserFeign;
import com.soufang.vo.BaseVo;
import com.soufang.vo.User.LoginReqVo;
import com.soufang.vo.User.RegisterReqVo;
import com.soufang.vo.User.SettleVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    @Value("${upload.user}")
    private String userUrl ;

    private static int code_time = 60*3 ;//验证码有效时间

    @Autowired
    PcUserFeign pcUserFeign;

    @RequestMapping(value = "signOut", method = RequestMethod.GET)
    public String signOut(HttpServletRequest request ){
        this.deletetoken(request);
        return "/login/login";
    }

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
                RedisUtils.setString(RedisConstants.verfity_code +phone,VerCode,code_time);
            }
            MessageDto messageDto = new MessageDto(phone,VerCode,0,DateUtils.getToday(),1);
            result = pcUserFeign.addMessage(messageDto);
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
            userDto.setPhone(registerReqVo.getEmail());
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
        UserDto userDto;
        try {
            userDto = this.getUserInfo(request);
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
        }catch (Exception e){
            baseVo.setSuccess(false);
            baseVo.setMessage("请先登录,才能注册店铺");
            return baseVo;
        }
    }

    /**
     * 跳转商铺信息增加页面
     * @param map
     * @param request
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "toSettle",method = RequestMethod.GET)
    public String toSettle(ModelMap map , HttpServletRequest request){
        map.put("userInfo",getUserInfo(request));
        if(getShopInfo(request) == null){
            //已经是商家，不能再入驻
            return "404";
        }else {
            return "sellerCenter/settle/first";
        }
    }

    @MemberAccess
    @RequestMapping(value = "settle" , method = RequestMethod.POST)
    public String toSettleSecond(@ModelAttribute SettleVo settleVo , HttpServletRequest request,ModelMap map){
        UserDto userDto = getUserInfo(request);
        userDto.setRealName(settleVo.getRealName());
        userDto.setFaxNumber(settleVo.getFaxCountry()+"-"+settleVo.getFaxCity()+"-"+settleVo.getFaxNumber());
        userDto.setFixedPhone(settleVo.getFixedCity()+"-"+settleVo.getFixedCity()+"-"+settleVo.getFixedNumber());
        Result result = pcUserFeign.updateUserInfo(userDto);
        CompanyDto companyDto = pcUserFeign.getCompany(userDto.getUserId());
        companyDto.setCompUrls(PropertiesParam.file_pre+companyDto.getCompUrls());
        map.put("companyInfo",companyDto);
//        String[] idCards = userDto.getIdCardUrl().split(";");
//        String idCardList = "";
//        for (int i = 0; i < idCards.length; i++) {
//            idCardList += PropertiesParam.file_pre + idCards[i]+ ";";
//        }
//        userDto.setIdCardUrl(idCardList);
        map.put("userInfo",userDto);
        if(result.isSuccess()){
            return  "sellerCenter/settle/second";
        }else {
            return "404";
        }
    }


    /**
     * 商铺信息提交
     * @param request
     * @param companyFile
     * @param idCardFile
     * @return
     */
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "settleInfo" , method = RequestMethod.POST)
    public BaseVo settleInfo(HttpServletRequest request ,
                             MultipartFile companyFile , MultipartFile[] idCardFile){
        BaseVo baseVo = new BaseVo();
        UserDto userDto = getUserInfo(request);
        CompanyDto companyDto = pcUserFeign.getCompany(getUserInfo(request).getUserId());
        companyDto.setCompLinker(userDto.getRealName());
        companyDto.setCompPhone(userDto.getPhone());
        companyDto.setUserId(userDto.getUserId());
        companyDto.setCompName(request.getParameter("compName"));
        companyDto.setCompType(Integer.valueOf(request.getParameter("compType")));
        companyDto.setCompanyInfo(request.getParameter("compInfo"));
        companyDto.setCompAddress(request.getParameter("compAddress"));
        companyDto.setBank(request.getParameter("bank"));
        companyDto.setBankNumber(request.getParameter("bankNumber"));
        if(companyFile != null){
            Map<String , Object> companyResult = FtpClient.uploadImage(companyFile,uploadUrl);
            if((boolean)companyResult.get("success")){
                companyDto.setCompUrls(companyResult.get("uploadName").toString());
            }else {
                baseVo.setSuccess(false);
                baseVo.setMessage("图片上传失败");
                return baseVo;
            }
        }
        String idCards = "";
        if(idCardFile != null){
            for (int i = 0; i < idCardFile.length; i++) {
                Map<String , Object> companyResult = FtpClient.uploadImage(idCardFile[i],userUrl);
                if((boolean)companyResult.get("success")){
                    idCards += companyResult.get("uploadName").toString() +";";
                }else {
                    baseVo.setSuccess(false);
                    baseVo.setMessage("图片上传失败");
                    return baseVo;
                }
            }
        }
        userDto.setIdCardUrl(idCards);
        userDto.setCompanyDto(companyDto);
        ShopDto shopDto = shopFeign.getByUserId(userDto.getUserId());
        shopDto.setMainProducts(request.getParameter("mainProducts"));
        shopDto.setBusinessScope(request.getParameter("businessScope"));
        shopDto.setShopStatus(ShopStatusEnum.ing.getValue());
        shopDto.setShopName(companyDto.getCompName());
        shopDto.setShopIntroduce(companyDto.getCompanyInfo());
        userDto.setShopDto(shopDto);
        pcUserFeign.settleShop(userDto);
        baseVo.setMessage("添加成功");
        baseVo.setSuccess(true);
        return baseVo;
    }

    @MemberAccess
    @RequestMapping(value = "settleFinal",method = RequestMethod.GET)
    public String settleFinal(){
        return "sellerCenter/settle/final";
    }
}
