package com.soufang.controller;


import com.soufang.base.Result;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.storeConstruction.*;
import com.soufang.base.enums.StoreConstructionSortEnum;
import com.soufang.base.utils.FtpClient;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.StoreConstructionFeign;
import com.soufang.vo.BaseVo;
import com.soufang.vo.StoreContruction.DetailStoreProductSortReqVo;
import com.soufang.vo.StoreContruction.ListStoreProductReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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


    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "saveShopChart", method = RequestMethod.POST)
    public BaseVo saveShopChart(MultipartFile[] file, String[] chartLink, HttpServletRequest request) {
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
        BaseVo baseVo = save(file, chartLink, dto.getStoreConstructionId());
        return baseVo;

    }

    /**
     * 添加轮播图
     *
     * @param file
     * @param chartLink
     * @return
     */
    private BaseVo save(MultipartFile[] file, String[] chartLink, Long storeConstructionId) {

        BaseVo baseVo = new BaseVo();
        Map<String, Object> map;
        StoreCurouselMapDto storeCurouselMapDto;
        List<StoreCurouselMapDto> storeCurouselMapDtoList = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {
            storeCurouselMapDto = new StoreCurouselMapDto();
            map = FtpClient.uploadImage(file[i], uploadUrl);
            if ((boolean) map.get("success")) {
                storeCurouselMapDto.setCurouselMapUrl(map.get("uploadName").toString());
            } else {
                baseVo.setSuccess(false);
                return baseVo;
            }
            storeCurouselMapDto.setStoreCurouselMapLink(chartLink[i]);
            storeCurouselMapDto.setStoreConstructionId(storeConstructionId);
            storeCurouselMapDtoList.add(storeCurouselMapDto);
        }
        StoreCurouselMapList list = new StoreCurouselMapList();
        list.setData(storeCurouselMapDtoList);
        Result result = storeConstructionFeign.saveChart(list);
        if (result.isSuccess()) {
            baseVo.setSuccess(true);
        } else {
            baseVo.setSuccess(false);
        }
        return baseVo;

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
    public BaseVo showAssort(HttpServletRequest request, @PathVariable String assortIds) {
        // 封装数据
        String ids = assortIds.substring(1);
        StoreExclusiveAssortDto storeExclusiveAssortDto = new StoreExclusiveAssortDto();
        storeExclusiveAssortDto.setExclusiveAssortIds(ids);
        ShopDto shopInfo = getShopInfo(request);
        storeExclusiveAssortDto.setShopId(shopInfo.getShopId());
        storeExclusiveAssortDto.setIsShow(1);
        // 更新数据
        Result result = storeConstructionFeign.updExclusiveAssort(storeExclusiveAssortDto);
        BaseVo baseVo = new BaseVo();
        if (result.isSuccess()) {
            baseVo.setSuccess(true);
        } else {
            baseVo.setSuccess(false);
        }
        return baseVo;
    }

    /**
     * 保存选中分类
     *
     * @param request
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "saveProductSort", method = RequestMethod.POST)
    @MemberAccess
    @ResponseBody
    public BaseVo saveProductSort(HttpServletRequest request, @RequestBody DetailStoreProductSortReqVo reqVo) {
        // 封装数据
        ShopDto shopInfo = getShopInfo(request);
        StoreExclusiveAssortDto storeExclusiveAssortDto = new StoreExclusiveAssortDto();
        storeExclusiveAssortDto.setExclusiveAssortId(reqVo.getExclusiveAssortId());
        storeExclusiveAssortDto.setSortName(StoreConstructionSortEnum.getByKey(reqVo.getSortName()));
        storeExclusiveAssortDto.setShopId(shopInfo.getShopId());


        // 更新数据
        Result result = new Result();
        BaseVo baseVo = new BaseVo();
        if (result.isSuccess()) {
            baseVo.setSuccess(true);
        } else {
            baseVo.setSuccess(false);
        }
        return baseVo;
    }
}








































