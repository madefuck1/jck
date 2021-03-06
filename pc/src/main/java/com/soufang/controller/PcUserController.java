package com.soufang.controller;

import com.alibaba.fastjson.JSONObject;
import com.soufang.base.PropertiesParam;
import com.soufang.base.RedisConstants;
import com.soufang.base.Result;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.message.MessageDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.enums.OauthTypeEnum;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/5/6
 * @Description:
 */
@Controller
@RequestMapping("user/")
public class PcUserController extends BaseController {
    @Value("${upload.company}")
    private String uploadUrl;//公司营业照片上传地址
    @Value("${upload.user}")
    private String userUrl;

    private static String appid="wxf01a70ce62c5ac8e";
    private static String secret = "1edffbeab1166899c06595b21eacea1a";
    private static int code_time = 60 * 3;//验证码有效时间
    private static String QQ_pc_appid = "101762207";
    private static String QQ_pc_appkey = "887b2ac3b14fb75cea4bf7f35438f3ab";

    @Autowired
    PcUserFeign pcUserFeign;

    //微信登录
    @RequestMapping(value = "weChatLogin", method = RequestMethod.GET)
    public String Login(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
        String code = request.getParameter("code");
        Map<Object,Object> map = new HashMap<>();
        /*String state = request.getParameter("state");*/
        if(code==null){
            //用户未授权,直接返回登录页面
            return "redirect:/user/toLogin";
        }else {
            //用户同意授权，获取access_token，
            String openid = OauthUtils.getOauth_openid("weChat","pc",code);
            /*//拿个人信息
             String access_token = jsonObject.getString("access_token");
            String userInfoUrl = "https://api.weixin.qq.com/sns/auth?access_token="+token+"&openid="+openid;
            JSONObject userInfo = JSONObject.parseObject(userInfoUrl);
            map.put("userInfo",userInfo);*/
            map.put("openid",openid);
            map.put("oauthType",OauthTypeEnum.weChat.getValue());
            //通过openid拿到用户信息
            UserDto userInfo = pcUserFeign.getUserByOpenId(map);
            if(StringUtils.isNotBlank(userInfo.getPhone())&&userInfo.getOauthType()==1){
               //用户已绑定微信
                setToken(userInfo.getUserId(), response);//保存token
                return "redirect:/index";//跳转到首页
            }else {
                //用户未绑定微信，现在开始第一次绑定微信
                modelMap.put("openid",openid);
                modelMap.put("oauthType",OauthTypeEnum.weChat.getValue());
                return  "/register/register";//重定向到注册页面
            }
        }
    }
    //绑定微信号
    @MemberAccess
    @RequestMapping(value = "BindWeChat", method = RequestMethod.GET)
    public String BindWeChat(HttpServletRequest request,ModelMap map) {
        UserDto userInfo = this.getUserInfo(request);
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if(code==null){
            //用户未授权,直接返回个人信息首页
            return "redirect:/personalCenter/toPersonalCenter?isBind=false";//重定向
            /*return "/personalCenter/index";*/
        }else {
            String openid = OauthUtils.getOauth_openid("weChat","pc",code);
            /*String token = jsonObject.getString("access_token");*/
            userInfo.setOauthType(OauthTypeEnum.weChat.getValue());//类型1 表示微信
            userInfo.setOauthId(openid);//保存 openid
            Result result = pcUserFeign.bindThirdInfo(userInfo);//Result
            return "redirect:/personalCenter/toPersonalCenter?isBind=true";//重定向
        }
    }
    //QQ登录
    @RequestMapping(value = "QQLogin", method = RequestMethod.GET)
    public String QQLogin(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
        String code = request.getParameter("code");
        Map<Object,Object> map = new HashMap<>();
        if(code==null){
            //用户未授权,返回登录
            return "redirect:/user/toLogin" ;
        }
        //用户同意授权，先获取access_token
        String url="https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="+QQ_pc_appid+"&client_secret="+QQ_pc_appkey+"&code="+code+"&redirect_uri=http://www.enterprise-china.com/user/QQLogin";
        String resultStr = OauthUtils.SendGet(url);
        //access_token=29EDD2139957FA22FD0A5A17FC983770&expires_in=7776000&refresh_token=B2401721EFE276873E64BC1C21D1E891
        //将字符串分割
        String access_token = StringUtils.substringBefore(resultStr.substring(resultStr.indexOf("access_token")+13),"&");
        String getOpenidUrl = "https://graph.qq.com/oauth2.0/me?access_token="+access_token;//获取QQ的openid
        String jsonOpenid = OauthUtils.splitData(OauthUtils.SendGet(getOpenidUrl),"(",")");
        JSONObject jsonObject = JSONObject.parseObject(jsonOpenid);
        String openid = jsonObject.getString("openid");
        map.put("openid",openid);
        map.put("oauthType",OauthTypeEnum.QQ.getValue());
        //通过openid拿到用户信息
        UserDto userInfo = pcUserFeign.getUserByOpenId(map);
        if(StringUtils.isNotBlank(userInfo.getPhone())&&userInfo.getOauthType()==2){
            //用户已绑定QQ
            setToken(userInfo.getUserId(), response);//保存token
            return "redirect:/index";//跳转到首页
        }else {
            //用户未绑定QQ,跳转到注册界面
            /*modelMap.put("openid",openid);
            modelMap.put("oauthType",OauthTypeEnum.QQ.getValue());*/
            return  "/register/register";//重定向到注册页面
        }
    }

    //绑定QQ
    /**
     *qq绑定获取code
     * @param request
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "bindQQ", method = RequestMethod.GET)
    public String bindQQGet(HttpServletRequest request ) {
        UserDto userInfo = this.getUserInfo(request);
        String code = request.getParameter("code");
        if(code==null){
            //用户未授权
            return "redirect:/personalCenter/toPersonalCenter?isBind=false" ;
        }
        String url="https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="+QQ_pc_appid+"&client_secret="+QQ_pc_appkey+"&code="+code+"&redirect_uri=http://www.enterprise-china.com/user/bindQQ";
        String resultStr = OauthUtils.SendGet(url);
        //access_token=29EDD2139957FA22FD0A5A17FC983770&expires_in=7776000&refresh_token=B2401721EFE276873E64BC1C21D1E891
        //将字符串分割
        String access_token = StringUtils.substringBefore(resultStr.substring(resultStr.indexOf("access_token")+13),"&");
        String getOpenidUrl = "https://graph.qq.com/oauth2.0/me?access_token="+access_token;//获取QQ的openid
        String openidStr = OauthUtils.SendGet(getOpenidUrl);
        String jsonOpenid = OauthUtils.splitData(openidStr,"(",")");
        JSONObject jsonObject = JSONObject.parseObject(jsonOpenid);
        String openid = jsonObject.getString("openid");
        userInfo.setOauthType(OauthTypeEnum.QQ.getValue());//类型2 表示QQ
        userInfo.setOauthId(openid);//保存 openid
        Result result = pcUserFeign.bindThirdInfo(userInfo);//Result
        return "redirect:/personalCenter/toPersonalCenter?isBind=true";
    }


    @RequestMapping(value = "signOut", method = RequestMethod.GET)
    public String signOut(HttpServletRequest request) {
        this.deletetoken(request);
        return "/login/login";
    }

    @RequestMapping(value = "toLogin", method = RequestMethod.GET)
    public String toLogin() {
        return "/login/login";
    }

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseVo login(@RequestBody LoginReqVo loginReqVo, HttpServletResponse response) {
        BaseVo baseVo = new BaseVo();
        UserDto userDto = new UserDto();
        String loginName = loginReqVo.getLoginName();
        if (loginName == null) {
            baseVo.setMessage("用户名栏不能为空");
            baseVo.setSuccess(false);
            return baseVo;
        }
        userDto.setPhone(loginName);
        userDto.setEmail(loginName);
        userDto.setUserName(loginName);
        if (StringUtils.isNotBlank(loginReqVo.getPassword())) {
            userDto.setPassWord(loginReqVo.getPassword());
        }
        Result result = pcUserFeign.login(userDto);
        if (result.isSuccess()) {
            setToken(Long.valueOf(result.getMessage()), response);
            baseVo.setMessage("登录成功");
        } else {
            baseVo.setMessage("登录失败:" + result.getMessage());
            baseVo.setSuccess(false);
        }
        return baseVo;
    }

    @RequestMapping(value = "toRegister", method = RequestMethod.GET)
    public String toRegister(ModelMap map) {
        /*map.put("openid","--");
        map.put("oauthType","--");*/
        return "/register/register";
    }

    //发送手机或者邮箱验证码
    @ResponseBody
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    public BaseVo getCode(@RequestBody RegisterReqVo registerReqVo) {
        BaseVo baseVo = new BaseVo();
        Result result;
        String VerCode = GetRandomUtils.getRandom();
        if (StringUtils.isNotBlank(registerReqVo.getPhone())) {
            MessageDto messageDto = new MessageDto();
            //发送手机验证码
            String phone = registerReqVo.getPhone();
            SmsSendResponse smsSendResponse = MessageUtil.setMessage("验证码：" + VerCode, phone);
            if(smsSendResponse == null || smsSendResponse.getCode() == null){
                baseVo.setMessage("网络错误");
                baseVo.setSuccess(false);
                return baseVo;
            }else if(smsSendResponse.getCode().equals("4")){
                baseVo.setMessage("手机号无效");
                baseVo.setSuccess(false);
                return baseVo;
            }else if (smsSendResponse.getCode().equals("0")) {
                //发送成功保存code的值
                RedisUtils.setString(RedisConstants.verfity_code + phone, VerCode, code_time);
                messageDto = new MessageDto(phone, VerCode, 0, DateUtils.getToday(), 1);
            }else {
                //发送失败
                messageDto = new MessageDto(phone, VerCode, Integer.valueOf(smsSendResponse.getCode()), DateUtils.getToday(), 1);
                baseVo.setMessage("验证码发送失败");
                baseVo.setSuccess(false);
                return baseVo;
            }
            result = pcUserFeign.addMessage(messageDto);
        } else if (StringUtils.isNotBlank(registerReqVo.getEmail())) {
            //发送邮箱验证码
            String email = registerReqVo.getEmail();
            MailUtils.sendHtmlMail(email, "邮箱验证码", VerCode);
            //邮件发送后，将信息保存在数据库t_message
            MessageDto messageDto = new MessageDto(email, VerCode, 0, DateUtils.getToday(), 1);
            result = pcUserFeign.addMessage(messageDto);
            RedisUtils.setString(RedisConstants.verfity_code + email, VerCode, code_time);
        } else {
            baseVo.setMessage("请输入手机号或邮箱");
            baseVo.setSuccess(false);
            return baseVo;
        }
        if (result.isSuccess()) {
            baseVo.setMessage("发送成功");
        } else {
            baseVo.setMessage("发送失败");
            baseVo.setSuccess(false);
        }
        return baseVo;
    }

    //临时接口，获取验证码
    @RequestMapping(value = "getCodeTest/{phoneOrEmail}", method = RequestMethod.GET)
    public String getCodeTest(@PathVariable String phoneOrEmail,ModelMap map ) {
        String code = null;
        if (StringUtils.isNotBlank(phoneOrEmail)) {
              code = RedisUtils.getString(RedisConstants.verfity_code + phoneOrEmail);
        } else {
            code= "请输入正确的电话或邮箱";
        }
        map.put("code",code);
        return "testGetCode";
    }

    //仅注册账号和密码
    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseVo register(@RequestBody RegisterReqVo registerReqVo, HttpServletResponse response) {
        BaseVo baseVo;
        UserDto userDto = new UserDto();
        Result result;
        String code;
        if (StringUtils.isNotBlank(registerReqVo.getEmail())) {
            code = RedisUtils.getString(RedisConstants.verfity_code + registerReqVo.getEmail());
            userDto.setEmail(registerReqVo.getEmail());
            userDto.setPhone(registerReqVo.getEmail());
        } else {
            code = RedisUtils.getString(RedisConstants.verfity_code + registerReqVo.getPhone());
            userDto.setPhone(registerReqVo.getPhone());
        }
        baseVo = verfityCode(code, registerReqVo.getCode());
        if (baseVo.isSuccess()) {
            userDto.setPassWord(registerReqVo.getPassword());
            userDto.setOauthId(registerReqVo.getOpenid());
            userDto.setOauthType(registerReqVo.getOauthType());
            result = pcUserFeign.register(userDto);
            if (result.isSuccess()) {
                this.setToken(Long.valueOf(result.getMessage()), response);
                baseVo.setMessage("注册成功");
                return baseVo;
            } else {
                baseVo.setSuccess(false);
                baseVo.setMessage("注册失败:" + result.getMessage());
                return baseVo;
            }
        } else {
            return baseVo;
        }
    }

    private BaseVo verfityCode(String code, String reCode) {
        BaseVo baseVo = new BaseVo();
        if (code == null) {
            baseVo.setSuccess(false);
            baseVo.setMessage("验证码过期");
        }else if(reCode == null){
            baseVo.setSuccess(false);
            baseVo.setMessage("验证码不能为空");
        }
        else if (!code.equals(reCode)) {
            baseVo.setSuccess(false);
            baseVo.setMessage("验证码错误");
        }
        //TODO 开发阶段注释验证码
       /* baseVo.setSuccess(true);*/
        return baseVo;
    }

    @MemberAccess
    @RequestMapping(value = "toAddInformation", method = RequestMethod.GET)
    public String toAddInformation() {
        return "register/addInformation";
    }

    //补充信息
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "addInformation", method = RequestMethod.POST)
    public BaseVo uploadImg(MultipartFile file, HttpServletRequest request) {
        BaseVo baseVo = new BaseVo();
        UserDto userDto;
        try {
            userDto = this.getUserInfo(request);
            String loginName = request.getParameter("loginName");
            String vip = request.getParameter("vip");
            userDto.setUserName(loginName);
            CompanyDto companyDto = new CompanyDto();
            //修改个人登录名
            Result result = pcUserFeign.update(userDto);
            //判断是否是供应商
            if (vip.equals("supplier")) {
                //供应商注册
                companyDto.setUserId(userDto.getUserId());
                companyDto.setCompPhone(request.getParameter("companyPhone"));
                companyDto.setCompName(request.getParameter("companyName"));
                companyDto.setCompAddress(request.getParameter("companyAddress"));
                companyDto.setCompCorporate(request.getParameter("companyCorporate"));
                Map<String, Object> map = FtpClient.uploadImage(file, uploadUrl);
                if ((boolean) map.get("success")) {
                    companyDto.setCompUrls(String.valueOf(map.get("uploadName")));
                    result = pcUserFeign.addCompany(companyDto);
                } else {
                    baseVo.setSuccess(false);
                    baseVo.setMessage("营业执照上传失败");
                    return baseVo;
                }
            }
            if (result.isSuccess()) {
                baseVo.setMessage("注册成功");
            } else {
                baseVo.setSuccess(false);
                baseVo.setMessage("注册失败");
            }
            return baseVo;
        } catch (Exception e) {
            baseVo.setSuccess(false);
            baseVo.setMessage("请先登录,才能注册店铺");
            return baseVo;
        }
    }

    /**
     * 跳转商铺信息增加页面
     *
     * @param map
     * @param request
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "toSettle", method = RequestMethod.GET)
    public String toSettle(ModelMap map, HttpServletRequest request) {
        map.put("userInfo", getUserInfo(request));
        if (isShop(request)) {
            //已经是商家，不能再入驻
            return "404";
        } else {
            return "sellerCenter/settle/first";
        }
    }

    @MemberAccess
    @RequestMapping(value = "settle", method = RequestMethod.POST)
    public String toSettleSecond(@ModelAttribute SettleVo settleVo, HttpServletRequest request, ModelMap map) {
        UserDto userDto = getUserInfo(request);
        userDto.setRealName(settleVo.getRealName());
        userDto.setFaxNumber(settleVo.getFaxCountry() + "-" + settleVo.getFaxCity() + "-" + settleVo.getFaxNumber());
        userDto.setFixedPhone(settleVo.getFixedCity() + "-" + settleVo.getFixedCity() + "-" + settleVo.getFixedNumber());
        if(StringUtils.isNotBlank(userDto.getUserAvatar())){
            String userAvatar = userDto.getUserAvatar();
            String url = userAvatar.substring(userAvatar.indexOf("8000")+4);
            userDto.setUserAvatar(url);
        }
        Result result = pcUserFeign.updateUserInfo(userDto);
        CompanyDto companyDto = pcUserFeign.getCompany(userDto.getUserId());
        companyDto.setCompUrls(PropertiesParam.file_pre + companyDto.getCompUrls());
        map.put("companyInfo", companyDto);
//        String[] idCards = userDto.getIdCardUrl().split(";");
//        String idCardList = "";
//        for (int i = 0; i < idCards.length; i++) {
//            idCardList += PropertiesParam.file_pre + idCards[i]+ ";";
//        }
//        userDto.setIdCardUrl(idCardList);
        map.put("userInfo", userDto);
        if (result.isSuccess()) {
            return "sellerCenter/settle/second";
        } else {
            return "404";
        }
    }


    /**
     * 商铺信息提交
     *
     * @param request
     * @param companyFile
     * @param idCardFile
     * @return
     */
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "settleInfo", method = RequestMethod.POST)
    public BaseVo settleInfo(HttpServletRequest request,
                             MultipartFile companyFile, MultipartFile[] idCardFile) {
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
        if (companyFile != null) {
            Map<String, Object> companyResult = FtpClient.uploadImage(companyFile, uploadUrl);
            if ((boolean) companyResult.get("success")) {
                companyDto.setCompUrls(companyResult.get("uploadName").toString());
            } else {
                baseVo.setSuccess(false);
                baseVo.setMessage("图片上传失败");
                return baseVo;
            }
        }
        String idCards = "";
        if (idCardFile != null) {
            for (int i = 0; i < idCardFile.length; i++) {
                Map<String, Object> companyResult = FtpClient.uploadImage(idCardFile[i], userUrl);
                if ((boolean) companyResult.get("success")) {
                    idCards += companyResult.get("uploadName").toString() + ";";
                } else {
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
    @RequestMapping(value = "settleFinal", method = RequestMethod.GET)
    public String settleFinal() {
        return "sellerCenter/settle/final";
    }
}
