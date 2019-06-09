package com.soufang.controller;

import com.soufang.base.PropertiesParam;
import com.soufang.base.Result;
import com.soufang.base.dto.address.AddressDto;
import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.dto.footprint.FootPrintDto;
import com.soufang.base.dto.invoice.InvoiceDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.suggest.SuggestDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.search.favorite.FavoriteSo;
import com.soufang.base.search.footPrint.FootPrintSo;
import com.soufang.base.search.order.OrderSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.utils.FtpClient;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.*;
import com.soufang.vo.BaseVo;
import com.soufang.vo.Enquiry.EnquiryAddVo;
import com.soufang.vo.Enquiry.EnquiryUpdateVo;
import com.soufang.vo.Enquiry.EnquiryVo;
import com.soufang.vo.address.AddressAddVo;
import com.soufang.vo.address.AddressVo;
import com.soufang.vo.address.UpdateAddressVo;
import com.soufang.vo.assess.AddAssessDetailVo;
import com.soufang.vo.assess.AddAssessVo;
import com.soufang.vo.favorite.FavoeiteVo;
import com.soufang.vo.favorite.FavoriteAddVo;
import com.soufang.vo.footPrint.FootPrintAddVo;
import com.soufang.vo.footPrint.FootPrintVo;
import com.soufang.vo.invoice.InvoiceAddVo;
import com.soufang.vo.invoice.InvoiceVo;
import com.soufang.vo.invoice.UpdateInvoiceVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "personalCenter")
public class PersonalCenterController extends BaseController {

    @Autowired
    PcAddressFeign pcAddressFeign;

    @Autowired
    PcInvoiceFeign pcInvoiceFeign;

    @Autowired
    FavoriteFeign favoriteFeign;

    @Autowired
    OrderFeign orderFeign;

    @Autowired
    EnquiryFeign enquiryFeign;

    @Autowired
    FootPrintFeign footPrintFeign;

    @Autowired
    PcUserFeign pcUserFeign;

    @Autowired
    AssessFeign assessFeign;

    @Value("${upload.user}")
    private String uploadUrl;//用户头像上传地址
    @Value("${ftp.host}")
    private String port;//图片上传端口号


    @MemberAccess
    @RequestMapping(value = "/toPutAssess/{orderNumber}", method = RequestMethod.GET)
    public String toPutAssess(@PathVariable String orderNumber,ModelMap modelMap){
        OrderShopDto orderShopDto = assessFeign.getOrderProduct(orderNumber);
        modelMap.put("orderShop",orderShopDto);
        return "personalCenter/commentList";
    }
    @MemberAccess
    @RequestMapping(value = "/putAssess", method = RequestMethod.POST)
    public String toPutAssess(@ModelAttribute AddAssessVo addAssessVo,ModelMap model,HttpServletRequest request){
        BaseVo vo = new BaseVo();
        UserDto userInfo = this.getUserInfo(request);
        List<AssessDto> assessDtos = new ArrayList<>();
        for (AddAssessDetailVo a: addAssessVo.getList()) {
            AssessDto assessDto = new AssessDto();
            assessDto.setAssessUserId(userInfo.getUserId());
            assessDto.setOrderNumber(addAssessVo.getOrderNumber());
            assessDto.setShopId(addAssessVo.getShopId());
            assessDto.setProductId(a.getProductId());
            assessDto.setAssessType(a.getAssessType());
            assessDto.setAssessContent(a.getAssessContent());
            assessDto.setProductColor(a.getProductColor().substring(3));
            assessDto.setProductSpec(a.getProductSpec().substring(3));
            assessDtos.add(assessDto);
        }
        Long orderShopId = assessFeign.putAssess(assessDtos);
        OrderShopDto orderShopDto = orderFeign.getDetail(orderShopId);
        for (OrderProductDto orderProductDto : orderShopDto.getOrderProducts()) {
            orderProductDto.setProductImage(orderProductDto.getProductImage());
        }
        model.put("order", orderShopDto);
        model.put("user", getUserInfo(request));
        return "personalCenter/orderDetail";
    }


    /**
     * 个人中心首页
     *
     * @param request
     * @param modelMap
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "/toPersonalCenter", method = RequestMethod.GET)
    public String personalCenterIndexHtml(HttpServletRequest request, ModelMap modelMap) {
        UserDto userInfo = getUserInfo(request);
        Map<String, String> userMap = new HashMap<>();
        // 用户名称
        userMap.put("userName", userInfo.getUserName());
        // 用户头像
        userMap.put("avatar", PropertiesParam.file_pre + userInfo.getUserAvatar());
        // 用户金额
        userMap.put("totalMoney", userInfo.getAccountDto().getTotalMoney() + "");
        OrderSo orderSo = new OrderSo();
        orderSo.setPage(1);
        orderSo.setLimit(2);
        orderSo.setUserId(userInfo.getUserId());
        PageHelp<OrderShopDto> pageHelp = orderFeign.getList(orderSo);
        // 用户信息
        modelMap.put("userInfo", userMap);
        // 我的订单(个人中心2条)
        modelMap.put("orderList", pageHelp.getData());
        // 商品收藏
        FavoriteSo favoriteSo = new FavoriteSo();
        favoriteSo.setUserId(userInfo.getUserId());
        favoriteSo.setFavoriteTargetType(1);// 产品
        favoriteSo.setPage(1);
        favoriteSo.setLimit(12);
        PageHelp<FavoriteDto> favoriteList = favoriteFeign.getFavoriteList(favoriteSo);
        modelMap.put("favoriteList", favoriteList.getData());
        // 购物足迹
        FootPrintSo footPrintSo = new FootPrintSo();
        footPrintSo.setUserId(userInfo.getUserId());
        footPrintSo.setPage(1);
        footPrintSo.setLimit(12);
        PageHelp<FootPrintDto> footPrintList = footPrintFeign.getFootPrintList(footPrintSo);
        modelMap.put("footPrintList", footPrintList.getData());
        return "personalCenter/index";
    }

    /**
     * 收获地址页面跳转
     *
     * @param map
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "/toAddress", method = RequestMethod.GET)
    public String addressHtml(ModelMap map,HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);
        AddressDto addressDto = new AddressDto();
        List<AddressDto> list = pcAddressFeign.getList(userInfo.getUserId());
        map.put("addressList",list);
        map.put("addressDto",addressDto);
        return "personalCenter/address";
    }

    @ResponseBody
    @RequestMapping(value = "/getAddressList", method = RequestMethod.GET)
    public AddressVo getAddressList(HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);
        List<AddressDto> list = pcAddressFeign.getList(userInfo.getUserId());
        AddressVo addressVo = new AddressVo();
        addressVo.setData(list);
        return addressVo;
    }

    //新增地址
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "saveAddress",method = RequestMethod.POST)
    public BaseVo saveAddress(HttpServletRequest request , @RequestBody AddressAddVo addressAddVo){
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        AddressDto addressDto = new AddressDto();
        addressDto.setUserId(userInfo.getUserId());//设置用户id
        addressDto.setCountry(addressAddVo.getCountry());
        addressDto.setDetailAddress(addressAddVo.getDetailAddress());
        addressDto.setLinkName(addressAddVo.getLinkName());
        addressDto.setLinkPhone(addressAddVo.getLinkPhone());
        addressDto.setIsDefaultAddress(addressAddVo.getIsDefaultAddress());
        Result result = pcAddressFeign.saveAddress(addressDto);
        return judge(result);
    }
    //请求编辑地址
    @MemberAccess
    @RequestMapping(value = "toUpdateAddress/{addressId}", method = RequestMethod.GET)
    public String toUpdate(@PathVariable long addressId,ModelMap map,HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);
        AddressDto addressDto = pcAddressFeign.getAddressById(addressId);
        List<AddressDto> list = pcAddressFeign.getList(userInfo.getUserId());
        map.put("addressList",list);
        map.put("addressDto",addressDto);
        return "personalCenter/address";
    }
    //编辑地址
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "toUpdateAddress/update",method = RequestMethod.POST)
    public BaseVo updateAddress(HttpServletRequest request , @RequestBody UpdateAddressVo updateAddressVo){
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(updateAddressVo.getAddressId());//要更新的地址id
        addressDto.setUserId(userInfo.getUserId());//设置用户id
        addressDto.setCountry(updateAddressVo.getCountry());
        addressDto.setDetailAddress(updateAddressVo.getDetailAddress());
        addressDto.setLinkName(updateAddressVo.getLinkName());
        addressDto.setLinkPhone(updateAddressVo.getLinkPhone());
        addressDto.setIsDefaultAddress(updateAddressVo.getIsDefaultAddress());
        Result result = pcAddressFeign.update(addressDto);
        return judge(result);
    }
    //设置默认地址
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "default/{addressId}",method = RequestMethod.GET)
    public BaseVo updatedDefaultAddress(@PathVariable Long addressId){
        Result result = pcAddressFeign.updatedDefaultAddress(addressId);
        return judge(result);
    }
    //删除地址
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "delete/{addressId}",method = RequestMethod.GET)
    public BaseVo delete(@PathVariable Long addressId ){
        Result result = pcAddressFeign.delete(addressId);
        return judge(result);
    }
    private BaseVo judge(Result result){
        BaseVo vo = new BaseVo();
        if(result.isSuccess()){
            vo.setSuccess(result.isSuccess());
            vo.setMessage(result.getMessage());
        }else {
            vo.setSuccess(result.isSuccess());
            vo.setMessage(result.getMessage());
        }
        return vo;
    }
    /**
     * 收获地址页面跳转
     *
     * @param map
     * @return
     */
//    @MemberAccess
    @RequestMapping(value = "/toSafety", method = RequestMethod.GET)
    public String safetyHtml(ModelMap map) {
        return "personalCenter/safety";
    }

    /**
     * 收获地址页面跳转
     *
     * @param map
     * @return
     */
    //@MemberAccess
    @RequestMapping(value = "/toWallet", method = RequestMethod.GET)
    public String walletHtml(ModelMap map) {
        return "personalCenter/wallet";
    }

    /**
     * 发票管理
     * @param
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "/toInvoice", method = RequestMethod.GET)
    public String toInvoice() {
        return "personalCenter/invoice";
    }

    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "getInvoiceList",method = RequestMethod.POST)
    public InvoiceVo  getInvoiceList(HttpServletRequest request,ModelMap model){
        InvoiceVo invoiceVo = new InvoiceVo();
        UserDto userInfo = this.getUserInfo(request);
        List<InvoiceDto> listDto = pcInvoiceFeign.getList(userInfo.getUserId());
        if(listDto != null && listDto.size() > 0){
            invoiceVo.setSuccess(true);
            invoiceVo.setData(listDto);
            invoiceVo.setMessage("查询成功");
        }else {
            invoiceVo.setData(null);
            invoiceVo.setSuccess(false);
            invoiceVo.setMessage("对不起！您没有相关的发票信息");
        }
        model.put("invoiceVo",invoiceVo);
        return invoiceVo;
    }

    //新增发票
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "saveInvoice",method = RequestMethod.POST)
    public BaseVo saveInvoice(HttpServletRequest request , @RequestBody InvoiceAddVo invoiceAddVo){
        InvoiceDto invoiceDto = new InvoiceDto();
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        invoiceDto.setUserId(userInfo.getUserId());//设置用户id
        BeanUtils.copyProperties(invoiceAddVo,invoiceDto);
        Result result = pcInvoiceFeign.saveInvoice(invoiceDto);
        return judge(result);
    }
    //得到一张发票的信息
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "getInvoice/{invoiceId}",method = RequestMethod.POST)
    public InvoiceDto getInvoice(@PathVariable Long invoiceId){
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto = pcInvoiceFeign.getInvoice(invoiceId);
        return invoiceDto;
    }

    //更新发票
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "updateInvoice",method = RequestMethod.POST)
    public BaseVo updateInvoice(HttpServletRequest request , @RequestBody UpdateInvoiceVo updateInvoiceVo){
        InvoiceDto invoiceDto = new InvoiceDto();
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        invoiceDto.setUserId(userInfo.getUserId());//设置用户id
        BeanUtils.copyProperties(updateInvoiceVo,invoiceDto);
        Result result = pcInvoiceFeign.update(invoiceDto);
        return judge(result);
    }
    //删除发票
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "deleteInvoice/{invoiceId}",method = RequestMethod.POST)
    public BaseVo deleteInvoice(@PathVariable Long invoiceId ){
        Result result = pcInvoiceFeign.delete(invoiceId);
        return judge(result);
    }
    //设为默认发票
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "defaultInvoice/{invoiceId}",method = RequestMethod.POST)
    public BaseVo updatedDefaultInvoice(@PathVariable Long invoiceId){
        Result result = pcInvoiceFeign.updatedDefaultInvoice(invoiceId);
        return judge(result);
    }




    /**
     * 消息页面跳转
     *
     * @param map
     * @return
     */
//    @MemberAccess
    @RequestMapping(value = "/toNew", method = RequestMethod.GET)
    public String newHtml(ModelMap map) {
        return "personalCenter/news";
    }

    /**
     * 个人信息跳转
     *
     * @param map
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "/toInformation", method = RequestMethod.GET)
    public String informationHtml(ModelMap map,HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);
        if(StringUtils.isNotBlank(userInfo.getUserAvatar())){
            userInfo.setUserAvatar(PropertiesParam.file_pre+userInfo.getUserAvatar());
        }
        map.put("userInfo",userInfo);
        return "personalCenter/information";
    }

    //修改个人信息
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/updateInformation",method = RequestMethod.POST)
    public BaseVo updateInformation(MultipartFile file, HttpServletRequest request){
        UserDto userDto = new UserDto();
        Result result = new Result();
        Map<String,Object> map ;
        UserDto userInfo = this.getUserInfo(request);
        userDto.setUserId(userInfo.getUserId());
        userDto.setRealName(request.getParameter("realName"));
        userDto.setUserName(request.getParameter("userName"));
        userDto.setPhone(request.getParameter("phone"));
        userDto.setEmail(request.getParameter("email"));
        if(file != null){
           map = FtpClient.uploadImage(file,uploadUrl);
            if((boolean)map.get("success")){
                userDto.setUserAvatar(String.valueOf(map.get("uploadName")));
                result = pcUserFeign.update(userDto);
            } else {
                result.setSuccess(false);
                result.setMessage("头像上传失败");
            }
        }else {
            result = pcUserFeign.update(userDto);
        }
        return judge(result);
    }



    //我的收藏----------------------------------

    /**
     * 跳转管理页面
     * @param request
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "/toFavoriteManage", method = RequestMethod.GET)
    public String toFavoriteManage(HttpServletRequest request) {
        return "personalCenter/collection";
    }


    /**
     * 查询列表数据
     * @param favoriteAddVo
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "getFavoriteList",method = RequestMethod.POST)
    public PageHelp<FavoriteDto> getFavoriteList(HttpServletRequest request, @RequestBody FavoriteAddVo favoriteAddVo){
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        FavoriteSo favoriteSo=new FavoriteSo();
        favoriteAddVo.setUserId(userInfo.getUserId());
        BeanUtils.copyProperties(favoriteAddVo,favoriteSo);
        PageHelp<FavoriteDto> pageHelp= favoriteFeign.getFavoriteList(favoriteSo);
        return pageHelp;
    }

    /**
     * 新增收藏
     * @param favoriteAddVo
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "addFavorite",method = RequestMethod.POST)
    public FavoeiteVo addFavorite(@RequestBody FavoriteAddVo favoriteAddVo, HttpServletRequest request){
        UserDto userInfo = this.getUserInfo(request);//
        FavoriteDto favoriteDto =new FavoriteDto();
        favoriteDto.setUserId(userInfo.getUserId());
        BeanUtils.copyProperties(favoriteAddVo,favoriteDto);
        Result result=favoriteFeign.addFavorite(favoriteDto);
        return judgeFavoeite(result);
    }

    /**
     * 删除收藏
     * @param favoriteId
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "removeFavorite",method = RequestMethod.POST)
    public  FavoeiteVo removeFavorite(@RequestBody Long favoriteId){
        Result result= favoriteFeign.removeFavorite(favoriteId);
        return judgeFavoeite(result);
    }

    //返回参数设置
    private FavoeiteVo judgeFavoeite(Result result){
        FavoeiteVo vo = new FavoeiteVo();
        if(result.isSuccess()){
            vo.setSuccess(result.isSuccess());
            vo.setMessage(result.getMessage());
        }else {
            vo.setMessage(result.getMessage());
            vo.setSuccess(result.isSuccess());
        }
        return vo;
    }

    //我的足迹---------------------------------

    /**
     * 我的足迹页面跳转
     * @param request
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "/toFootPrintManage", method = RequestMethod.GET)
    public String toFootPrintManage(HttpServletRequest request) {
        return "personalCenter/footPrint";
    }

    //新增足迹
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "addFootPrint",method = RequestMethod.POST)
    public boolean addFootPrint(@RequestBody FootPrintAddVo footPrintAddVo, HttpServletRequest request){
        UserDto userDto = this.getUserInfo(request);
        FootPrintDto footPrintDto = new FootPrintDto();
        footPrintDto.setUserId(userDto.getUserId());
        BeanUtils.copyProperties(footPrintAddVo,footPrintDto);
        return footPrintFeign.addFootPrint(footPrintDto);
    }

    /**
     * 查询展示列表
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "selFootPrintOneWeek",method = RequestMethod.POST)
    public PageHelp<FootPrintDto> selFootPrintOneWeek(HttpServletRequest request,@RequestBody FootPrintAddVo footPrintAddVo){
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        FootPrintSo footPrintSo = new FootPrintSo();
        footPrintAddVo.setUserId(userInfo.getUserId());
        BeanUtils.copyProperties(footPrintAddVo,footPrintSo);
        PageHelp<FootPrintDto> pageHelp = footPrintFeign.selFootPrintOneWeek(footPrintSo);
        return pageHelp;
    }

    /**
     * 删除足迹
     * @param footPrintAddVo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "delFootPrintById",method = RequestMethod.POST)
    public FootPrintVo delFootPrintById(@RequestBody FootPrintAddVo footPrintAddVo){
       Result result = footPrintFeign.delFootPrintById(footPrintAddVo.getFootprintId());
       return judgeFootPrint(result);

    }

    /**
     * 查看详情页面
     * @param footPringtId
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "selFootPrintById",method = RequestMethod.POST)
   public FootPrintDto selFootPrintById(@RequestBody Long footPringtId){
        FootPrintDto footPrintDto=  footPrintFeign.selFootPrintById(footPringtId);
        return footPrintDto;
    }

    //返回参数设置-足迹
    private FootPrintVo judgeFootPrint(Result result){
        FootPrintVo vo = new FootPrintVo();
        if(result.isSuccess()){
            vo.setSuccess(result.isSuccess());
            vo.setMessage(result.getMessage());
        }else {
            vo.setMessage(result.getMessage());
            vo.setSuccess(result.isSuccess());
        }
        return vo;
    }



    //我的求购---------------------------------


    /**
     * 跳转求购管理页面
     * @param request
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "/toEnquiryManage", method = RequestMethod.GET)
    public String toEnquiryManage(HttpServletRequest request) {
        return "personalCenter/MyPurchase";
    }

    /**
     * 跳转到求购新增/修改
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "/toAddPurchase", method = RequestMethod.POST)
    public String toAddPurchase(HttpServletRequest request) {
            // 获取单号并存入
        String newEnquiryNumber = enquiryFeign.toGetEqNumber();
        return newEnquiryNumber;
    }

    @MemberAccess
    @RequestMapping(value = "/toPurchaseHtml", method = RequestMethod.GET)
    public String toPurchaseHtml(String enquiryNumber,String isUpdate,ModelMap map){
        map.put("enquiryNumber",enquiryNumber);
        map.put("isUpdate",isUpdate);
        return "personalCenter/purchase2";
    }
    /**
     * 新增求购信息
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "addEnquiry",method = RequestMethod.POST)
    public FavoeiteVo addEnquiry(MultipartFile file, HttpServletRequest request){
        Result result = new Result();
        Map<String,Object> map ;
        UserDto userInfo = this.getUserInfo(request);//
        EnquiryDto enquiryDto =new EnquiryDto();
        EnquiryProductDto enquiryProductDto= new EnquiryProductDto();
        //对数据捕捉异常
        try {
            //询盘信息
        enquiryDto.setStrEnquiryType("0");
        enquiryDto.setEnquiryNumber(request.getParameter("enquiryNumber"));
        enquiryDto.setUserId(userInfo.getUserId());
        enquiryDto.setEnquiryTitle(request.getParameter("enquiryTitle"));
        enquiryDto.setEnquiryStatus(Integer.valueOf(request.getParameter("enquiryStatus")));
        enquiryDto.setCreateTime(DateUtils.string2Date(request.getParameter("createTime"),DateUtils.format1));
        enquiryDto.setTakeDate(DateUtils.string2Date(request.getParameter("takeDate"),DateUtils.format1));
        enquiryDto.setEnquiryRemark(request.getParameter("enquiryRemark"));
            enquiryDto.setTakeAddress(request.getParameter("detailStreet"));
        //产品详情-描述
        enquiryDto.setProduct_detail(request.getParameter("detailStreet"));
        List<EnquiryProductDto> enquiryProductDtos= new ArrayList<>();
        //询价信息
        enquiryProductDto.setProductNumber(Long.parseLong(request.getParameter("productNumber")));
        enquiryProductDto.setProductName(request.getParameter("productName"));
        enquiryProductDto.setProductUnit(request.getParameter("productUnit"));
        enquiryProductDto.setEnquiryNumber(enquiryDto.getEnquiryNumber());
        enquiryProductDtos.add(enquiryProductDto);
        enquiryDto.setEnquiryProductDto(enquiryProductDtos);
        }catch (Exception e){
            e.printStackTrace();
        }
        //对文件判断
        if(file != null){
            map = FtpClient.uploadImage(file,uploadUrl);
            if((boolean)map.get("success")){
                enquiryProductDto.setProductImage(String.valueOf(map.get("uploadName")));
                result= enquiryFeign.addEnquiry(enquiryDto);
            } else {
                result.setSuccess(false);
                result.setMessage("图片上传失败");
            }
        }else {
            result= enquiryFeign.addEnquiry(enquiryDto);
        }
        return judgeFavoeite(result);

    }

    /**
     * 列表-询盘
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "getEnquiryList",method = RequestMethod.POST)
    public  PageHelp<EnquiryDto> getEnquiryList(HttpServletRequest request,@RequestBody EnquiryAddVo enquiryAddVo){
        UserDto userInfo = this.getUserInfo(request);//拿当前登录的用户信息
        EnquirySo enquirySo =new EnquirySo();
        Long s=userInfo.getUserId();
        enquirySo.setUserId(s);
        enquiryAddVo.setUserId(userInfo.getUserId());
        /*enquirySo.setEnquiryType(enquiryAddVo.getEnquiryType());
        enquirySo.setEnquiryStatus(enquiryAddVo.getEnquiryStatus());*/
        BeanUtils.copyProperties(enquiryAddVo,enquirySo);
        PageHelp<EnquiryDto> pageHelp= enquiryFeign.getList(enquirySo);
        return pageHelp;
    }

    /**
     * 删除-询盘/产品信息
     * @param updateVo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "delEnquiry",method = RequestMethod.POST)
    public EnquiryVo delEnquiry(@RequestBody EnquiryUpdateVo updateVo){
        Result result=  enquiryFeign.delEnquiry(updateVo.getEnquiryNumber());
        return judgeEnquiry(result);
    }

    /**
     * 查看具体详情
     * @param enquirySo
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "selEnquiryByNumber",method = RequestMethod.POST)
    public EnquiryDto selEnquiryByNumber(@RequestBody EnquirySo enquirySo){
        EnquiryDto enquiryDto=  enquiryFeign.selEnquiryByNumber(enquirySo);
        return  enquiryDto;
    }


    //返回参数设置
    private EnquiryVo judgeEnquiry(Result result){
        EnquiryVo vo = new EnquiryVo();
        if(result.isSuccess()){
            vo.setSuccess(result.isSuccess());
            vo.setMessage(result.getMessage());
        }else {
            vo.setMessage(result.getMessage());
            vo.setSuccess(result.isSuccess());
        }
        return vo;
    }

    //意见反馈
    @MemberAccess
    @RequestMapping(value = "toSuggest",method = RequestMethod.GET)
    public String toSuggest(){
        return "/personalCenter/suggest";
    }


    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "saveSuggest", method = RequestMethod.POST)
    public Result saveSuggest(@ModelAttribute SuggestDto suggestDto , HttpServletRequest request){
        suggestDto.setCreateTime(DateUtils.getToday());
        suggestDto.setUserId(getUserInfo(request).getUserId());
        Result result = pcUserFeign.addSuggest(suggestDto);
        return result;
    }
}
