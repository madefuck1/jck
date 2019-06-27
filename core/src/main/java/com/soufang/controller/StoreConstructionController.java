package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.storeConstruction.StoreConstructionDto;
import com.soufang.base.dto.storeConstruction.StoreCurouselMapList;
import com.soufang.base.dto.storeConstruction.StoreExclusiveAssortDto;
import com.soufang.base.dto.storeConstruction.StoreProductAssortDto;
import com.soufang.base.page.PageHelp;
import com.soufang.model.StoreConstruction;
import com.soufang.service.StoreConstructionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/core/store")
@RestController
public class StoreConstructionController {

    @Autowired
    StoreConstructionService storeConstructionService;

    /**
     * 获取店铺装修信息
     *
     * @param shopId
     * @return
     */
    @RequestMapping(value = "getStoreInfo", method = RequestMethod.POST)
    public StoreConstructionDto getStoreInfo(@RequestBody Long shopId) {
        return storeConstructionService.getStoreInfo(shopId);
    }

    /**
     * 注册店铺装修信息
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestBody StoreConstructionDto dto) {
        StoreConstruction storeConstruction = new StoreConstruction();
        // dto复制数据到entity
        BeanUtils.copyProperties(dto, storeConstruction);
        return storeConstructionService.insert(storeConstruction);
    }

    /**
     * 注册店铺装修信息
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestBody StoreConstructionDto dto) {
        StoreConstruction storeConstruction = new StoreConstruction();
        // dto复制数据到entity
        BeanUtils.copyProperties(dto, storeConstruction);
        return storeConstructionService.update(storeConstruction);
    }

    /**
     * 轮播图分类select框
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "storeAssort", method = RequestMethod.POST)
    public List<StoreExclusiveAssortDto> getStoreAssort(@RequestBody StoreExclusiveAssortDto dto) {
        return storeConstructionService.getStoreAssort(dto);
    }

    /**
     * 轮播图下 产品检索
     *
     * @param storeProductAssortDto
     * @return
     */
    @RequestMapping(value = "searchProduct", method = RequestMethod.POST)
    public List<StoreProductAssortDto> searchProduct(@RequestBody StoreProductAssortDto storeProductAssortDto) {
        return storeConstructionService.searchProduct(storeProductAssortDto);
    }

    /**
     * 清空店铺轮播图
     *
     * @param storeConstructionId
     * @return
     */
    @RequestMapping(value = "delAllChart", method = RequestMethod.POST)
    public Result delAllChart(@RequestBody Long storeConstructionId) {
        return storeConstructionService.delAllChart(storeConstructionId);
    }

    /**
     * 添加店铺轮播图
     *
     * @param list
     * @return
     */
    @RequestMapping(value = "saveChart", method = RequestMethod.POST)
    public Result saveChart(@RequestBody StoreCurouselMapList list) {
        return storeConstructionService.saveChart(list.getData());
    }

    /**
     * 保存选中分类
     *
     * @param storeExclusiveAssortDto
     * @return
     */
    @RequestMapping(value = "updExclusiveAssort", method = RequestMethod.POST)
    public Result updExclusiveAssort(@RequestBody StoreExclusiveAssortDto storeExclusiveAssortDto) {
        return storeConstructionService.updExclusiveAssort(storeExclusiveAssortDto);
    }

    /**
     * @param storeExclusiveAssortDto
     * @return
     */
    @RequestMapping(value = "saveProductSort", method = RequestMethod.POST)
    public Result saveProductSort(@RequestBody StoreExclusiveAssortDto storeExclusiveAssortDto) {
        return storeConstructionService.saveProductSort(storeExclusiveAssortDto);
    }


    /**
     * 发布
     */
    @RequestMapping(value = "publish", method = RequestMethod.POST)
    public Result publish(@RequestBody Long shopId) {
        return storeConstructionService.publish(shopId);
    }

    /**
     * 通过key删除分类
     *
     * @param assortId
     * @return
     */
    @RequestMapping(value = "delAssortByKey", method = RequestMethod.POST)
    public Result delAssortByKey(@RequestBody Long assortId) {
        Result result = storeConstructionService.delAssortByKey(assortId);
        return result;
    }

    /**
     * 新增分类
     *
     * @param storeExclusiveAssortDto
     * @return
     */
    @RequestMapping(value = "registerAssort", method = RequestMethod.POST)
    public Result registerAssort(@RequestBody StoreExclusiveAssortDto storeExclusiveAssortDto) {
        return storeConstructionService.registerAssort(storeExclusiveAssortDto);
    }

    /**
     * 更新分类
     *
     * @param storeExclusiveAssortDto
     * @return
     */
    @RequestMapping(value = "updAssort", method = RequestMethod.POST)
    public Result updAssort(@RequestBody StoreExclusiveAssortDto storeExclusiveAssortDto) {
        return storeConstructionService.updAssort(storeExclusiveAssortDto);
    }

    /**
     * 获取店铺产品列表
     *
     * @param productDto
     * @return
     */
    @RequestMapping(value = "initProduct", method = RequestMethod.POST)
    public PageHelp<ProductDto> initProduct(@RequestBody ProductDto productDto) {
        return storeConstructionService.initProduct(productDto);
    }

    @RequestMapping(value = "saveProductAssort", method = RequestMethod.POST)
    public Result saveProductAssort(@RequestBody StoreProductAssortDto productAssortDto) {
        return storeConstructionService.saveProductAssort(productAssortDto);
    }

    /**
     * @param shopId
     * @return
     */
    @RequestMapping(value = "isExistStoreInfo", method = RequestMethod.POST)
    public Boolean isExistStoreInfo(@RequestBody Long shopId) {
        return storeConstructionService.isExistStoreInfo(shopId);
    }

}