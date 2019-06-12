package com.soufang.controller;

import com.soufang.base.PropertiesParam;
import com.soufang.base.Result;
import com.soufang.base.dto.assess.AssessDto;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.order.OrderProductDto;
import com.soufang.base.dto.order.OrderShopDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import com.soufang.base.search.order.OrderSo;
import com.soufang.base.search.product.ProductManageSo;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.AssessFeign;
import com.soufang.feign.OrderFeign;
import com.soufang.feign.ProductFeign;
import com.soufang.feign.ShopFeign;
import com.soufang.vo.BaseVo;
import com.soufang.vo.User.UpdateInformation;
import com.soufang.vo.assess.AssessVo;
import com.soufang.vo.company.UpdtaeCompany;
import com.soufang.vo.order.UpActualPrice;
import com.soufang.vo.orderShop.OrderShopVo;
import com.soufang.vo.product.ListProductVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "sellerCenter")
public class SellerController extends BaseController {

    @Autowired
    OrderFeign orderFeign;

    @Autowired
    ProductFeign productFeign;

    @Autowired
    AssessFeign assessFeign;

    @Autowired
    ShopFeign shopFeign;

    //卖家首页
    @MemberAccess
    @RequestMapping(value = "/toIndex", method = RequestMethod.GET)
    public String indexHtml(HttpServletRequest request, ModelMap map) {
        // 跳转先判定是否是有卖家权限，没有就直接跳转申请成为卖家页面
        UserDto userInfo = this.getUserInfo(request);
        ShopDto shopDto = shopFeign.getByUserId(userInfo.getUserId());
        if(StringUtils.isBlank(shopDto.getShopName())){
            //map.put("noShopInfo","您还不是卖家，请填写公司信息");
            return "defaultPage/noCompanyInfo";
        }else {
            return "sellerCenter/index";
        }

    }

    //查看个人信息
    @MemberAccess
    @RequestMapping(value = "/toSellerCenter", method = RequestMethod.GET)
    public String toSellerCenter(HttpServletRequest request, ModelMap map) {
        // 跳转先判定是否是有卖家权限，没有就直接跳转申请成为卖家页面
        UserDto userInfo = this.getUserInfo(request);
        ShopDto shopDto = shopFeign.getByUserId(userInfo.getUserId());
        if(StringUtils.isBlank(shopDto.getShopName())){
            return "defaultPage/noCompanyInfo";
        }else {
//        String idCard[] = userInfo.getIdCardUrl().split(";");
//        userInfo.setIdCardUrl(PropertiesParam.file_pre+idCard[0]);
//        userInfo.setrIdCardUrl(PropertiesParam.file_pre+idCard[1]);
            CompanyDto companyDto = pcUserFeign.getCompany(userInfo.getUserId());
            companyDto.setCompUrls(PropertiesParam.file_pre + companyDto.getCompUrls());
            userInfo.setCompanyDto(companyDto);
            map.put("userInfo", userInfo);
            return "sellerCenter/information";
        }

    }

    //去修改个人信息
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/toUpdate", method = RequestMethod.POST)
    public UserDto toUpdate(HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);
        return userInfo;
    }

    //修改个人信息
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/updateInformation", method = RequestMethod.POST)
    public BaseVo getinformation(HttpServletRequest request, @RequestBody UpdateInformation information) {
        UserDto userInfo = this.getUserInfo(request);
        UserDto userDto = new UserDto();
        userDto.setUserId(userInfo.getUserId());
        userDto.setEmail(information.getEmail());
        userDto.setPhone(information.getPhone());
        userDto.setUserName(information.getUserName());
        Result result = pcUserFeign.update(userDto);
        return judge(result);
    }

    //去修改公司信息
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/toUpdateCompany", method = RequestMethod.POST)
    public UserDto toUpdateCompany(HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);
        CompanyDto companyDto = pcUserFeign.getCompany(userInfo.getUserId());
        companyDto.setCompUrls(PropertiesParam.file_pre + companyDto.getCompUrls());
        userInfo.setCompanyDto(companyDto);
        return userInfo;
    }

    //修改公司信息
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
    public BaseVo updateCompany(HttpServletRequest request, @RequestBody UpdtaeCompany updtaeCompany) {
        UserDto userInfo = this.getUserInfo(request);
        CompanyDto companyDto = new CompanyDto();
        companyDto.setUserId(userInfo.getUserId());
        BeanUtils.copyProperties(updtaeCompany, companyDto);
        Result result = pcUserFeign.updateCompany(companyDto);
        return judge(result);
    }

    private BaseVo judge(Result result) {
        BaseVo vo = new BaseVo();
        if (result.isSuccess()) {
            vo.setSuccess(result.isSuccess());
            vo.setMessage(result.getMessage());
        } else {
            vo.setSuccess(result.isSuccess());
            vo.setMessage(result.getMessage());
        }
        return vo;
    }

    //卖家查看已卖出的产品  两个接口一个页面一起用
    @MemberAccess
    @RequestMapping(value = "toSoldProduct", method = RequestMethod.GET)
    public String toOrderManager() {
        return "sellerCenter/soldProduct";
    }
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "getSoldProduct", method = RequestMethod.POST)
    public OrderShopVo getSoldProduct(HttpServletRequest request, @RequestBody OrderSo orderSo) {
        ShopDto shopInfo = this.getShopInfo(request);
        orderSo.setShopId(shopInfo.getShopId());
        if (orderSo.getOrderStatus() != null && orderSo.getOrderStatus() != 0) {
            orderSo.setOrderStatus(orderSo.getOrderStatus());
        }
        if (StringUtils.isBlank(orderSo.getOrderNumber())) {
            orderSo.setOrderNumber(null);
        }
        orderSo.setPage(orderSo.getPage());
        orderSo.setLimit(5);
        PageHelp<OrderShopDto> list = orderFeign.getList(orderSo);
        OrderShopVo vo = new OrderShopVo();
        vo.setCount(list.getCount());
        vo.setData(list.getData());
        return vo;
    }

    //卖家订单详情
    @MemberAccess
    //@ResponseBody
    @RequestMapping(value = "toDetail/{orderShopId}", method = RequestMethod.GET)
    public String toDetail(HttpServletRequest request, ModelMap model, @PathVariable Long orderShopId) {
        OrderShopDto orderShopDto = orderFeign.getDetail(orderShopId);
        for (OrderProductDto orderProductDto : orderShopDto.getOrderProducts()) {
            orderProductDto.setProductImage(orderProductDto.getProductImage());
        }
        model.put("order", orderShopDto);
        return "sellerCenter/orderDetail";
    }

    //卖家修改价格
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "updateSumPrice", method = RequestMethod.POST)
    public BaseVo updateActualPrice(@RequestBody UpActualPrice upActualPrice) {
        BaseVo vo = new BaseVo();
        OrderShopDto shopDto = new OrderShopDto();
        if (StringUtils.isBlank(String.valueOf(upActualPrice.getActualPrice()))) {
            vo.setMessage("请输入价格");
            vo.setSuccess(false);
            return vo;
        }
        BeanUtils.copyProperties(upActualPrice, shopDto);
        Result result = pcUserFeign.updateActualPrice(shopDto);
        vo.setMessage(result.getMessage());
        vo.setSuccess(result.isSuccess());
        return vo;
    }


    //评价管理
    @MemberAccess
    @RequestMapping(value = "/toAssessManagement", method = RequestMethod.GET)
    public String toEvaluationManagement() {
        return "sellerCenter/assessManagement";
    }
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/getAssess", method = RequestMethod.POST)
    public AssessVo getAssess(HttpServletRequest request, @RequestBody AssessSo assessSo){
        AssessVo vo = new AssessVo();
        //获取产品的评价只需要产品ID 产品名相同店铺不同的产品id也不同
        /*ShopDto shopInfo = this.getShopInfo(request);
        assessSo.setShopId(shopInfo.getShopId());*/
        if(assessSo.getLimit() == null){
            assessSo.setLimit(5);
        }
        PageHelp<AssessDto> pageHelp = assessFeign.getList(assessSo);
        vo.setData(pageHelp.getData());
        vo.setCount(pageHelp.getCount());
        return vo;
    }

    //产品列表
    @MemberAccess
    @RequestMapping(value = "/toProductList", method = RequestMethod.GET)
    public String toProductList() {
        return "sellerCenter/productList";
    }

    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/getProductList", method = RequestMethod.POST)
    public ListProductVo getProductList(HttpServletRequest request, @RequestBody ProductManageSo productSo) {
        ListProductVo vo = new ListProductVo();
        ShopDto shopInfo = this.getShopInfo(request);

        productSo.setShopId(shopInfo.getShopId());
        productSo.setLimit(5);
        PageHelp<ProductDto> pageHelp = productFeign.getShopProductList(productSo);

        vo.setCount(pageHelp.getCount());
        vo.setData(pageHelp.getData());
        return vo;
    }

    //产品下架
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/getDown", method = RequestMethod.POST)
    public BaseVo getDown(String[] ids) {
        BaseVo vo = new BaseVo();
        Result result = productFeign.getDown(ids);
        vo.setMessage(result.getMessage());
        vo.setSuccess(result.isSuccess());
        return vo;
    }

    //产品上架
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/putUp", method = RequestMethod.POST)
    public BaseVo putUp(String[] ids) {
        BaseVo vo = new BaseVo();
        Result result = productFeign.putUp(ids);
        vo.setMessage(result.getMessage());
        vo.setSuccess(result.isSuccess());
        return vo;
    }

    //删除产品
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public BaseVo deleteProduct(String[] ids) {
        BaseVo vo = new BaseVo();
        Result result = productFeign.deleteProduct(ids);
        vo.setMessage(result.getMessage());
        vo.setSuccess(result.isSuccess());
        return vo;
    }


    //发布产品
    @MemberAccess
    @RequestMapping(value = "/toReleaseProduct", method = RequestMethod.GET)
    public String toReleaseProduct(HttpServletRequest request) {
        UserDto userInfo = this.getUserInfo(request);

        return "sellerCenter/releaseProduct";
    }


    // begin-------------------- 店铺装修 -----------------------

    /**
     * 店铺装修
     *
     * @param request
     * @param model
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "toStoreConstruction", method = RequestMethod.GET)
    public String toStoreConstruction(HttpServletRequest request, ModelMap model) {
        return "/sellerCenter/StoreConstruction";
    }

    /**
     * 分类管理
     *
     * @param request
     * @param model
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "toStoreAssortManage", method = RequestMethod.GET)
    public String toStoreAssortManage(HttpServletRequest request, ModelMap model) {
        return "/sellerCenter/storeAssortManage";
    }

    /**
     * 产品分类
     *
     * @param request
     * @param model
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "toStoreProductAssortManage", method = RequestMethod.GET)
    public String toStoreProductAssortManage(HttpServletRequest request, ModelMap model) {
        return "/sellerCenter/storeProductAssortManage";
    }

    // end---------------------------------------------------
}
