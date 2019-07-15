package com.soufang.controller;


import com.soufang.base.Result;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.storeConstruction.*;
import com.soufang.base.enums.StoreConstructionSortEnum;
import com.soufang.base.page.PageHelp;
import com.soufang.base.utils.FtpClient;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.PcUserFeign;
import com.soufang.feign.StoreConstructionFeign;
import com.soufang.vo.BaseVo;
import com.soufang.vo.StoreConstruction.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 店铺装修
 */
@Controller
@RequestMapping(value = "/store")
public class StoreConstructionController extends BaseController {
    @Value("${upload.logoPic}")
    private String uploadUrl;//店铺装修logo照片上传地址

    @Autowired
    StoreConstructionFeign storeConstructionFeign;

    @Autowired
    PcUserFeign pcUserFeign;

    /**
     * 上传logo图片
     *
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value = "logoPicUpload", method = RequestMethod.POST)
    @MemberAccess
    @ResponseBody
    public BaseVo logoPicUpload(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
        BaseVo vo = new BaseVo();
        Result result;
        ShopDto shopInfo = getShopInfo(request);
        Map<String, Object> map = FtpClient.uploadImage(file, uploadUrl);
        if ((boolean) map.get("success")) {
            StoreConstructionDto dto = storeConstructionFeign.getStoreInfo(shopInfo.getShopId());
            // 查询是否已经存在店铺装修主体信息
            if (dto == null) {
                // 不存在，封装数据
                dto = new StoreConstructionDto();
                dto.setShopId(shopInfo.getShopId());
                dto.setStoreLogo(map.get("uploadName").toString());
                dto.setStoreStatus(0);
                // 插入
                result = storeConstructionFeign.register(dto);
                if (result.isSuccess()) {
                    vo.setSuccess(true);
                } else {
                    vo.setSuccess(false);
                    vo.setMessage("logo更换失败，请重新更换！");
                }
            } else {
                dto.setStoreLogo(map.get("uploadName").toString());
                // 更新
                result = storeConstructionFeign.update(dto);
                if (result.isSuccess()) {
                    vo.setSuccess(true);
                } else {
                    vo.setSuccess(false);
                    vo.setMessage("logo更换失败，请重新更换！");
                }
            }
        } else {
            vo.setSuccess(false);
            vo.setMessage("logo图片上传失败");
            return vo;
        }
        return vo;
    }

    /**
     * 店铺装修改变导航（首页/公司概况/视频中心）栏颜色
     *
     * @param request
     * @param background
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "upd/{background}", method = RequestMethod.GET)
    public BaseVo update(HttpServletRequest request, @PathVariable String background) {
        BaseVo baseVo = new BaseVo();
        Result result;
        ShopDto shopInfo = getShopInfo(request);
        StoreConstructionDto storeInfo = storeConstructionFeign.getStoreInfo(shopInfo.getShopId());
        // 查询是否已经存在店铺装修主体信息
        if (storeInfo == null) {
            // 不存在，封装数据
            storeInfo = new StoreConstructionDto();
            storeInfo.setShopId(shopInfo.getShopId());
            storeInfo.setStoreStatus(0);
            storeInfo.setStoreNavColor(background);
            // 插入
            result = storeConstructionFeign.register(storeInfo);
            if (result.isSuccess()) {
                baseVo.setSuccess(true);
            } else {
                baseVo.setSuccess(false);
                baseVo.setMessage("背景色更换失败，请重新更换！");
            }
        } else {
            storeInfo.setStoreNavColor(background);
            // 更新
            result = storeConstructionFeign.update(storeInfo);
            if (result.isSuccess()) {
                baseVo.setSuccess(true);
            } else {
                baseVo.setSuccess(false);
                baseVo.setMessage("背景色更换失败，请重新更换！");
            }
        }
        return baseVo;
    }

    /**
     * 店铺专属分类
     *
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "storeAssort/{isShow}", method = RequestMethod.POST)
    public List<StoreExclusiveAssortDto> getStoreAssort(HttpServletRequest request, @PathVariable Integer isShow) {
        ShopDto shopInfo = getShopInfo(request);
        List<StoreExclusiveAssortDto> list = new ArrayList<>();
        if (shopInfo.getShopId() == null) {
            return list;
        }
        // 数据封装
        StoreExclusiveAssortDto dto = new StoreExclusiveAssortDto();
        dto.setShopId(shopInfo.getShopId());
        if (isShow == 1) {
            dto.setIsShow(isShow);
        }
        // 查询
        list = storeConstructionFeign.getStoreAssort(dto);
        return list;
    }

    /**
     * 轮播图下 产品检索
     *
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "searchProduct", method = RequestMethod.POST)
    public List<StoreProductAssortDto> searchProduct(HttpServletRequest request, @RequestBody ListStoreProductReqVo reqVo) {
        ShopDto shopInfo = getShopInfo(request);
        StoreProductAssortDto storeProductAssortDto = new StoreProductAssortDto();
        storeProductAssortDto.setShopId(shopInfo.getShopId());
        storeProductAssortDto.setProductName(reqVo.getProductName());
        storeProductAssortDto.setExclusiveAssortId(reqVo.getExclusiveAssortId());
        List<StoreProductAssortDto> list = storeConstructionFeign.searchProduct(storeProductAssortDto);
        return list;
    }

    /**
     * 保存轮播图
     *
     * @param file
     * @param chartLink
     * @param request
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "saveShopChart", method = RequestMethod.POST)
    public ListStoreMapVo saveShopChart(MultipartFile[] file, String[] chartLink, HttpServletRequest request) {
        ShopDto shopInfo = getShopInfo(request);
        Result result;
        StoreConstructionDto dto = storeConstructionFeign.getStoreInfo(shopInfo.getShopId());
        // 查询是否已经存在店铺装修主体信息
        if (dto == null) {
            // 不存在，封装数据
            dto = new StoreConstructionDto();
            dto.setShopId(shopInfo.getShopId());
            dto.setStoreStatus(0);
            // 插入
            result = storeConstructionFeign.register(dto);
            // 清空轮播图
            storeConstructionFeign.delAllChart(Long.valueOf(result.getMessage()));
        } else {
            // 清空轮播图
            storeConstructionFeign.delAllChart(dto.getStoreConstructionId());
        }
        ListStoreMapVo baseVo = save(file, chartLink, dto.getStoreConstructionId());
        return baseVo;

    }

    /**
     * 添加轮播图
     *
     * @param file
     * @param chartLink
     * @return
     */
    private ListStoreMapVo save(MultipartFile[] file, String[] chartLink, Long storeConstructionId) {

        ListStoreMapVo vo = new ListStoreMapVo();
        Map<String, Object> map;
        StoreCurouselMapDto storeCurouselMapDto;
        List<StoreCurouselMapDto> storeCurouselMapDtoList = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {
            storeCurouselMapDto = new StoreCurouselMapDto();
            map = FtpClient.uploadImage(file[i], uploadUrl);
            if ((boolean) map.get("success")) {
                storeCurouselMapDto.setCurouselMapUrl(map.get("uploadName").toString());
            } else {
                vo.setSuccess(false);
                return vo;
            }
            storeCurouselMapDto.setStoreCurouselMapLink(chartLink[i]);
            storeCurouselMapDto.setStoreConstructionId(storeConstructionId);
            storeCurouselMapDtoList.add(storeCurouselMapDto);
        }
        StoreCurouselMapList list = new StoreCurouselMapList();
        list.setData(storeCurouselMapDtoList);
        Result result = storeConstructionFeign.saveChart(list);
        if (result.isSuccess()) {
            vo.setSuccess(true);
            vo.setList(storeCurouselMapDtoList);
        } else {
            vo.setSuccess(false);
        }
        return vo;

    }

    /**
     * 保存选中分类
     *
     * @param request
     * @param assortIds
     * @return
     */
    @RequestMapping(value = "storeASave/{assortIds}", method = RequestMethod.POST)
    @MemberAccess
    @ResponseBody
    public Result showAssort(HttpServletRequest request, @PathVariable String assortIds) {
        // 封装数据
        String ids = assortIds.substring(1);
        StoreExclusiveAssortDto storeExclusiveAssortDto = new StoreExclusiveAssortDto();
        storeExclusiveAssortDto.setExclusiveAssortIds(ids);
        ShopDto shopInfo = getShopInfo(request);
        storeExclusiveAssortDto.setShopId(shopInfo.getShopId());
        storeExclusiveAssortDto.setIsShow(1);
        // 更新数据
        Result result = storeConstructionFeign.updExclusiveAssort(storeExclusiveAssortDto);
        return result;
    }

    /**
     * 更新显示分类排序
     *
     * @param request
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "saveProductSort", method = RequestMethod.POST)
    @MemberAccess
    @ResponseBody
    public Result saveProductSort(HttpServletRequest request, @RequestBody DetailStoreProductSortReqVo reqVo) {
        // 封装数据
        ShopDto shopInfo = getShopInfo(request);
        StoreExclusiveAssortDto storeExclusiveAssortDto = new StoreExclusiveAssortDto();
        storeExclusiveAssortDto.setExclusiveAssortId(reqVo.getExclusiveAssortId());
        storeExclusiveAssortDto.setSortName(StoreConstructionSortEnum.getByKey(reqVo.getSortName()));
        storeExclusiveAssortDto.setShopId(shopInfo.getShopId());
        // 更新数据
        return storeConstructionFeign.saveProductSort(storeExclusiveAssortDto);
    }


    /**
     * 获取店铺装修信息
     */
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "getStoreConstructionInfo", method = RequestMethod.POST)
    public DetailStoreConstructionVo getStoreInfo(HttpServletRequest request) {
        DetailStoreConstructionVo vo = new DetailStoreConstructionVo();
        // 店铺信息
        ShopDto shop = getShopInfo(request);
        vo.setShopDto(shop);
        // 公司信息
        CompanyDto company = pcUserFeign.getCompany(shop.getUserId());
        vo.setCompanyDto(company);
        StoreConstructionDto storeConstructionDto = storeConstructionFeign.getStoreInfo(shop.getShopId());
        vo.setStoreConstructionDto(storeConstructionDto);
        return vo;
    }


    /**
     * 店铺装修预览页面
     * toStorePreview
     */
    @MemberAccess
    @RequestMapping(value = "toStorePreview", method = RequestMethod.GET)
    public String toStorePreview(ModelMap map) {
        return "/sellerCenter/StoreConstruction.preview";
    }


    /**
     * 发布
     */
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "publish", method = RequestMethod.POST)
    public Result publish(HttpServletRequest request) {
        ShopDto shop = getShopInfo(request);
        Result result = storeConstructionFeign.publish(shop.getShopId());
        return result;
    }

    /**
     * 分类删除
     *
     * @param request
     * @param assortId
     * @return
     */
    @RequestMapping(value = "delAssort/{assortId}", method = RequestMethod.POST)
    @MemberAccess
    @ResponseBody
    public Result delAssortByKey(HttpServletRequest request, @PathVariable Long assortId) {
        // 封装数据
        Result result = storeConstructionFeign.delAssortByKey(assortId);
        return result;
    }


    /**
     * 分类添加/更新
     *
     * @param request
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "addOrUpd", method = RequestMethod.POST)
    @MemberAccess
    @ResponseBody
    public Result addOrUpdateAssort(HttpServletRequest request, @RequestBody DetailStoreAssortReqVo reqVo) {
        Result result = new Result();

        ShopDto shopInfo = getShopInfo(request);
        if (StringUtils.isBlank(reqVo.getAssortName())) {
            result.setSuccess(false);
            result.setMessage("分类名称不能为空");
            return result;
        }
        // 封装数据
        StoreExclusiveAssortDto storeExclusiveAssortDto = new StoreExclusiveAssortDto();
        if (reqVo.getExclusiveAssortId() == null) {
            //添加
            storeExclusiveAssortDto.setShopId(shopInfo.getShopId());
            storeExclusiveAssortDto.setAssortName(reqVo.getAssortName());
            storeExclusiveAssortDto.setIsShow(0);
            result = storeConstructionFeign.registerAssort(storeExclusiveAssortDto);
        } else {
            //更新
            storeExclusiveAssortDto.setExclusiveAssortId(reqVo.getExclusiveAssortId());
            storeExclusiveAssortDto.setAssortName(reqVo.getAssortName());
            result = storeConstructionFeign.updAssort(storeExclusiveAssortDto);
        }
        return result;
    }


    /**
     * 分类添加初始化产品列表
     *
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "initProduct", method = RequestMethod.POST)
    @MemberAccess
    @ResponseBody
    public PageHelp<ProductDto> initProduct(HttpServletRequest request, @RequestBody ListStoreProductReqVo reqVo) {
        ShopDto shopInfo = getShopInfo(request);
        ProductDto productDto = new ProductDto();
        productDto.setShopId(shopInfo.getShopId());
        productDto.setProductName(reqVo.getProductName());
        productDto.setAssortType(reqVo.getAssortType());
        productDto.setMinPrice(reqVo.getMinPrice());
        productDto.setMaxPrice(reqVo.getMaxPrice());
        productDto.setPage(reqVo.getPage());
        productDto.setLimit(reqVo.getLimit());
        PageHelp<ProductDto> pageHelp = storeConstructionFeign.initProduct(productDto);
        return pageHelp;
    }


    /**
     * 分类绑定
     *
     * @param request
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "saveProductAssort", method = RequestMethod.POST)
    @MemberAccess
    @ResponseBody
    public Result saveProductAssort(HttpServletRequest request, @RequestBody DetailProductAssortReqVo reqVo) {
        ShopDto shopInfo = getShopInfo(request);
        StoreProductAssortDto productAssortDto = new StoreProductAssortDto();
        productAssortDto.setShopId(shopInfo.getShopId());
        productAssortDto.setProductId(reqVo.getProductId());
        if (reqVo.getExclusiveAssortIds() != null && !"".equals(reqVo.getExclusiveAssortIds())) {
            String[] split = reqVo.getExclusiveAssortIds().split(",");
            List<String> ids = Arrays.asList(split);
            productAssortDto.setExclusiveAssortIds(ids);
        }
        return storeConstructionFeign.saveProductAssort(productAssortDto);

    }


}
