package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.AppShopFeign;
import com.soufang.app.vo.productManage.ListProductVo;
import com.soufang.app.vo.shop.DetailVo;
import com.soufang.app.vo.shop.ListShopReqVo;
import com.soufang.app.vo.shop.ListShopVo;
import com.soufang.base.PageBase;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.shop.ShopStatisticsDto;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.base.utils.FtpClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/app/shop")
public class AppShopController extends AppBaseController {

    @Autowired
    AppShopFeign appShopFeign;

    @Value("${upload.company}")
    private String companyUrl;

    /**
     * 获取店铺列表
     *
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "getList", method = RequestMethod.POST)
    public ListShopVo getList(@RequestBody ListShopReqVo reqVo) {
        //  封装数据
        ShopSo so = new ShopSo();
        so.setShopName(reqVo.getShopName());
        so.setPage(reqVo.getPage());
        so.setLimit(reqVo.getLimit());
        // 查询数据
        List<ShopDto> shopDtos = appShopFeign.appGetList(so).getData();
        ListShopVo vo = new ListShopVo();
        vo.setData(shopDtos);
        return vo;
    }

    /**
     * 获取店铺详情
     *
     * @return
     */
    @RequestMapping(value = "getShopDetail", method = RequestMethod.POST)
    public DetailVo getShopDetail(HttpServletRequest request) {
        DetailVo vo = new DetailVo();
        ShopDto shopDetail = getShopInfo(request);
        CompanyDto companyDto = appUserFeign.companyInfo(shopDetail.getUserId());
        shopDetail.setCompanyDto(companyDto);
        vo.setData(shopDetail);
        return vo;
    }


    /**
     * 更新 店铺信息
     *
     * @param request
     * @param avatarUrl
     * @return
     */
    @RequestMapping(value = "updateInfo", method = RequestMethod.POST)
    public DetailVo updateInfo(HttpServletRequest request, MultipartFile avatarUrl) {
        DetailVo vo = new DetailVo();
        ShopDto shopDto = getShopInfo(request);
        if (avatarUrl != null) {
            Map<String, Object> map = FtpClient.uploadImage(avatarUrl, companyUrl);
            if ((boolean) map.get("success")) {
                shopDto.setAvatarUrl(map.get("uploadName").toString());
            } else {
                vo.setSuccess(false);
                vo.setMessage("图片上传失败");
                return vo;
            }
        }
        if(StringUtils.isNotBlank(request.getParameter("shopIntroduce"))){
            shopDto.setShopIntroduce(request.getParameter("shopIntroduce"));
        }
        if(StringUtils.isNotBlank(request.getParameter("mainProducts"))){
            shopDto.setMainProducts(request.getParameter("mainProducts"));
        }
        appShopFeign.updateShop(shopDto);
        CompanyDto companyDto = appUserFeign.companyInfo(shopDto.getUserId());
        if(StringUtils.isNotBlank(request.getParameter("compPhone"))){
            companyDto.setCompPhone(request.getParameter("compPhone"));
        }
        if(StringUtils.isNotBlank(request.getParameter("compAddress"))){
            companyDto.setCompAddress(request.getParameter("compAddress"));
        }
        appUserFeign.updateCompany(companyDto);
        ShopDto shopDetail = getShopInfo(request);
        vo.setData(shopDetail);
        return vo;
    }

    /**
     * 店铺管理查看店铺信息
     *
     * @param request
     */
    @AppMemberAccess
    @RequestMapping(value = "getShopManageInfo", method = RequestMethod.POST)
    public DetailVo getShopManageInfo(HttpServletRequest request, @RequestBody PageBase pageBase) {
        DetailVo vo = new DetailVo();
        ShopDto shopInfo = getShopInfo(request);

        // 店铺统计信息
        ShopStatisticsDto statisticsDto = appShopFeign.getShopStatisticsInfo(shopInfo.getShopId());
        shopInfo.setShopStatisticsDto(statisticsDto);
        //判断店铺是否存在

        //店铺产品信息
        ProductDto productDto = new ProductDto();
        productDto.setShopId(shopInfo.getShopId());
        // 处理page
        int page = pageBase.getPage();
        if (page == 0) {
            productDto.setPage(page);
        } else {
            productDto.setPage((page - 1) * pageBase.getLimit());
        }
        productDto.setLimit(pageBase.getLimit());
        List<ProductDto> shopProductManaList = appShopFeign.getShopProductManaList(productDto);
        shopInfo.setProductDtoList(shopProductManaList);
        vo.setData(shopInfo);
        return vo;

    }

    /**
     * 店铺管理查看店铺信息--最新近更新
     *
     * @param request
     */
    @AppMemberAccess
    @RequestMapping(value = "getShopProOnRecentlyUpdate", method = RequestMethod.POST)
    public ListProductVo getShopProOnRecentlyUpdate(HttpServletRequest request, @RequestBody PageBase pageBase) {
        ListProductVo vo = new ListProductVo();
        ShopDto shopInfo = getShopInfo(request);
        //判断店铺是否存在

        //店铺产品信息
        ProductDto productDto = new ProductDto();
        productDto.setShopId(shopInfo.getShopId());
        // 处理page
        int page = pageBase.getPage();
        if (page == 0) {
            productDto.setPage(page);
        } else {
            productDto.setPage((page - 1) * pageBase.getLimit());
        }
        productDto.setLimit(pageBase.getLimit());
        productDto.setSort(1);
        List<ProductDto> shopProductManaList = appShopFeign.getShopProductManaList(productDto);
        vo.setData(shopProductManaList);
        return vo;
    }


}
