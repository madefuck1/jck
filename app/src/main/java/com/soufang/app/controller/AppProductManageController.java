package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppAddressFeign;
import com.soufang.app.feign.AppProductManageFeign;
import com.soufang.app.vo.AppVo;
import com.soufang.app.vo.productManage.*;
import com.soufang.base.Result;
import com.soufang.base.dto.address.AddressDto;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.product.DetailSubmitProductDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/app/product")
public class AppProductManageController extends AppBaseController {

    @Autowired
    AppProductManageFeign appProductManageFeign;

    @Autowired
    AppAddressFeign appAddressFeign;

    //以下为买家接口 --------------------------------------------------------------------------------------------

    /**
     * 产品搜索列表
     *
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public ListProductVo getProductList(HttpServletRequest request, @RequestBody ListProductReqVo reqVo) {
        ListProductVo vo = new ListProductVo();
        ProductDto dto = new ProductDto();

        // 获取用户信息 查询用户是否已经登录
        UserDto userInfo = getUserInfo(request);
        if (userInfo != null) {
            // 已登录 添加用户信息
            dto.setUserId(userInfo.getUserId());
        }
        //  封装入参数据
        dto.setLimit(reqVo.getLimit());
        dto.setPage(reqVo.getPage());
        if (StringUtils.isNotBlank(reqVo.getProductName())) {
            dto.setProductName(reqVo.getProductName());
        }
        if (reqVo.getAssortId() != null && reqVo.getAssortId().compareTo(0l) != 0) {
            dto.setAssortId(reqVo.getAssortId());
        }
        if (reqVo.getSortType() != null) {
            dto.setSortType(reqVo.getSortType());
            dto.setSort(reqVo.getSort());
        }
//        if (reqVo.getSellSort() != null) {
//            dto.setSellSort(reqVo.getSellSort());
//        }
        // 获取检索数据
        PageHelp<ProductDto> pageHelp = appProductManageFeign.getProductList(dto);
        // 封装vo
        vo.setMessage("查询成功");
        vo.setData(pageHelp.getData());
        vo.setCount(pageHelp.getCount());
        return vo;

    }

    /**
     * 热门产品搜索列表 取前10
     * todo  入参  出参封装 补充
     *
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "getHotProductList", method = RequestMethod.POST)
    public ListHotProductReqVo getHotProductList(@RequestBody ListHotProductReqVo reqVo) {
        ListHotProductReqVo vo = new ListHotProductReqVo();
        ProductDto dto = new ProductDto();
        //  todo   封装入参数据
        // 获取检索数据
        PageHelp<ProductDto> productList = appProductManageFeign.getProductList(dto);
        // 封装vo  todo
        return vo;
    }

    /**
     * 产品明细
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "getDetail/{productId}", method = RequestMethod.POST)
    public DetailProductVo getProductDetail(HttpServletRequest request, @PathVariable Long productId) {
        DetailProductVo vo = new DetailProductVo();
        // 获取检索数据
        ProductDto dto = new ProductDto();
        // 获取 token
        try {
            String token = request.getHeader("Authorization");
            if (!"".equals(token) && token != null) {
                // token存在 添加浏览足迹
                Long userId = Long.valueOf(RedisUtils.getString(token));
                dto.setUserId(userId);
            }
        } catch (Exception e) {

        }
        dto.setProductId(productId);
        ProductDto productDto = appProductManageFeign.getProductDetail(dto);
        // 封装vo
        vo.setData(productDto);
        vo.setMessage("查询成功");
        return vo;
    }

    /**
     * 产品-右侧搜索框
     *
     * @return
     */
    @RequestMapping(value = "getAssort/{parentId}", method = RequestMethod.POST)
    public ListAssrotVo getAllAssort(@PathVariable Long parentId) {
        ListAssrotVo vo = new ListAssrotVo();
        List<AssortDto> list = appProductManageFeign.getDownList(parentId);
        vo.setMessage("查询成功！");
        vo.setData(list);
        return vo;
    }

    @RequestMapping(value = "getAssort", method = RequestMethod.POST)
    public ListAssrotVo getAllAssort() {
        ListAssrotVo vo = new ListAssrotVo();
        List<AssortDto> list = appProductManageFeign.getAllAssort();
        vo.setMessage("查询成功！");
        vo.setData(list);
        return vo;
    }


    /**
     * 立即购买跳转到提交订单页面
     *
     * @param request
     * @param reqVo
     * @return
     */
    @AppMemberAccess
    @RequestMapping(value = "toSubmitOrder", method = RequestMethod.POST)
    public DetailSubmitProductVo toSubmitOrder(HttpServletRequest request, @RequestBody DetailSubmitProductReqVo reqVo) {
        DetailSubmitProductVo vo = new DetailSubmitProductVo();
        ProductDto so = new ProductDto();
        so.setProductId(reqVo.getProductId());
        so.setProductNumber(reqVo.getProductNum());
        so.setProductSpec(reqVo.getProductSpec());
        ProductDto productDto = appProductManageFeign.getProductDetailBySpec_Number(so);
        // 封装出参
        DetailSubmitProductDto detailSubmitProductDto = new DetailSubmitProductDto();
        detailSubmitProductDto.setProductId(productDto.getProductId());
        detailSubmitProductDto.setProductName(productDto.getProductName());
        detailSubmitProductDto.setShopId(productDto.getShopId());
        detailSubmitProductDto.setShopName(productDto.getShopName());
        detailSubmitProductDto.setProductSpecName(reqVo.getProductSpec());
        detailSubmitProductDto.setProductImageUrl(productDto.getProductUrl());
        detailSubmitProductDto.setProductNumber(reqVo.getProductNum());
        detailSubmitProductDto.setProductPrice(productDto.getProductSpecDto().getSpecNumber());
        // 用户默认地址
        UserDto userInfo = getUserInfo(request);
        AddressDto aDefault = appAddressFeign.getDefault(userInfo.getUserId());
        detailSubmitProductDto.setAddressDto(aDefault);
        vo.setData(detailSubmitProductDto);
        return vo;
    }


    // 以下为卖家接口 --------------------------------------------------------------------------------------------

    /**
     * 产品列表
     *
     * @param request
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "sellGetList", method = RequestMethod.POST)
    @AppMemberAccess
    public ListProductVo sellGetList(HttpServletRequest request, @RequestBody ListProductReqVo reqVo) {

        ListProductVo vo = new ListProductVo();
        ProductDto dto = new ProductDto();

        // 获取用户信息 查询用户是否已经登录
        ShopDto shopDto = getShopInfo(request);
        dto.setShopId(shopDto.getShopId());

        //  封装入参数据
        dto.setLimit(reqVo.getLimit());
        dto.setPage(reqVo.getPage());
        if (StringUtils.isNotBlank(reqVo.getProductName())) {
            dto.setProductName(reqVo.getProductName());
        }
        PageHelp<ProductDto> pageHelp = appProductManageFeign.getProductList(dto);
        // 封装vo
        vo.setMessage("查询成功");
        vo.setData(pageHelp.getData());
        vo.setCount(pageHelp.getCount());
        return vo;
    }

    /**
     * 产品上下架
     *
     * @param request
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "sellUpOrDownOrDelProduct", method = RequestMethod.POST)
    @AppMemberAccess
    public DetailUpOrDownOrDelVo sellUpOrDownOrDelProduct(HttpServletRequest request, @RequestBody DetailUpOrDownOrDelProductReqVo reqVo) {
        DetailUpOrDownOrDelVo appVo = new DetailUpOrDownOrDelVo();
        ProductDto dto = new ProductDto();
        dto.setProductId(reqVo.getProductId());
        ShopDto shopDto = getShopInfo(request);
        dto.setShopId(shopDto.getShopId());
        dto.setIsUpper(reqVo.getIsUpper());
        Result result = appProductManageFeign.sellDownProduct(dto);
        if (result.isSuccess()) {
            appVo.setSuccess(true);
            appVo.setMessage("成功！");
            appVo.setData(reqVo.getIsUpper());
        } else {
            appVo.setSuccess(false);
            appVo.setMessage("失败！");
        }
        return appVo;
    }


}
