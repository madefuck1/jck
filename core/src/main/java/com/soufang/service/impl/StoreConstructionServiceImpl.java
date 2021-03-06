package com.soufang.service.impl;

import com.soufang.base.PropertiesParam;
import com.soufang.base.Result;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.storeConstruction.*;
import com.soufang.base.page.PageHelp;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.*;
import com.soufang.model.*;
import com.soufang.service.StoreConstructionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreConstructionServiceImpl implements StoreConstructionService {
    @Autowired
    StoreConstructionMapper storeConstructionMapper;

    @Autowired
    StoreExclusiveAssortMapper storeExclusiveAssortMapper;

    @Autowired
    StoreProductAssortMapper storeProductAssortMapper;

    @Autowired
    StoreCurouselMapMapper storeCurouselMapMapper;

    @Autowired
    StoreViewMapper storeViewMapper;

    @Override
    public Boolean isExistStoreInfo(Long shopId) {
        StoreConstructionDto storeCInfo = storeConstructionMapper.getStoreCInfo(shopId);
        if (storeCInfo != null && storeCInfo.getStoreStatus() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Result saveStoreView(StoreViewDto dto) {
        Result result = new Result();
        StoreView storeView = new StoreView();
        BeanUtils.copyProperties(dto,storeView);
        int i = storeViewMapper.insertSelective(storeView);
        if(i>0){
            result.setSuccess(true);
        }else {
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<StoreViewDto> getStoreViews(Long shopId) {
        List<StoreViewDto> viewDtos = storeViewMapper.getStoreViews(shopId);
        for (StoreViewDto dto:viewDtos) {
            dto.setViewurl(PropertiesParam.file_pre+dto.getViewurl());
        }
        return viewDtos;
    }

    @Override
    public StoreConstructionDto getStoreInfo(Long shopId) {

        StoreConstructionDto storeConstructionDto;
        storeConstructionDto = storeConstructionMapper.getStoreCInfo(shopId);
        if (storeConstructionDto != null) {
            StoreExclusiveAssortDto tempDto = new StoreExclusiveAssortDto();
            tempDto.setShopId(shopId);
            // 店铺装修页面初始化默认选显示的分类
            tempDto.setIsShow(1);
            List<StoreExclusiveAssortDto> storeExclusiveAssortDtoList = storeExclusiveAssortMapper.getStoreAssort(tempDto);
            storeConstructionDto.setStoreExclusiveAssortDtoList(storeExclusiveAssortDtoList);
            List<StoreCurouselMapDto> storeCurouselMapDtoList = storeCurouselMapMapper.getMapList(storeConstructionDto.getStoreConstructionId());
            storeConstructionDto.setStoreCurouselMapDtoList(storeCurouselMapDtoList);
        }
        return storeConstructionDto;
    }

    @Override
    public Result insert(StoreConstruction storeConstruction) {

        Result result = new Result();
        result.setSuccess(storeConstructionMapper.insert(storeConstruction) > 0 ? true : false);
        result.setMessage(storeConstruction.getStoreConstructionId() + "");
        return result;
    }

    @Override
    public Result update(StoreConstruction storeConstruction) {
        Result result = new Result();
        result.setSuccess(storeConstructionMapper.updateByPrimaryKeySelective(storeConstruction) > 0 ? true : false);
        return result;
    }

    @Override
    public List<StoreExclusiveAssortDto> getStoreAssort(StoreExclusiveAssortDto dto) {
        return storeExclusiveAssortMapper.getStoreAssort(dto);
    }

    @Override
    public Result delAllChart(Long storeConstructionId) {
        Result result = new Result();
        if (storeCurouselMapMapper.delAllChart(storeConstructionId) > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<StoreProductAssortDto> searchProduct(StoreProductAssortDto storeProductAssortDto) {
        return storeProductAssortMapper.searchProduct(storeProductAssortDto);
    }

    @Override
    public Result saveChart(List<StoreCurouselMapDto> storeCurouselMapDtoList) {
        StoreCurouselMap storeCurouselMap = new StoreCurouselMap();
        if (storeCurouselMapDtoList.size() == 0) {
            Result result = new Result();
            result.setSuccess(false);
            return result;
        }
        for (StoreCurouselMapDto temp : storeCurouselMapDtoList) {
            BeanUtils.copyProperties(temp, storeCurouselMap);
            storeCurouselMapMapper.insert(storeCurouselMap);
        }
        return new Result();
    }

    @Override
    public Result updExclusiveAssort(StoreExclusiveAssortDto storeExclusiveAssortDto) {
        Result result = new Result();
        StoreExclusiveAssortDto temp;
        // 根据店铺id全部更新为不显示
        temp = new StoreExclusiveAssortDto();
        temp.setShopId(storeExclusiveAssortDto.getShopId());
        temp.setIsShow(0);
        storeExclusiveAssortMapper.updExclusiveAssort(temp);
        // 更新称显示
        if (storeExclusiveAssortMapper.updExclusiveAssort(storeExclusiveAssortDto) > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }


    @Override
    public Result saveProductSort(StoreExclusiveAssortDto storeExclusiveAssortDto) {
        StoreExclusiveAssort storeExclusiveAssort = new StoreExclusiveAssort();
        BeanUtils.copyProperties(storeExclusiveAssortDto, storeExclusiveAssort);
        Result result = new Result();
        if (storeExclusiveAssortMapper.updateByPrimaryKeySelective(storeExclusiveAssort) > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result publish(Long shopId) {
        Result result = new Result();
        if (storeConstructionMapper.publish(shopId) > 0 ? true : false) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result delAssortByKey(Long assortId) {
        Result result = new Result();
        result.setSuccess(storeExclusiveAssortMapper.deleteByPrimaryKey(assortId) > 0 ? true : false);
        return result;
    }

    @Override
    public Result registerAssort(StoreExclusiveAssortDto storeExclusiveAssortDto) {
        StoreExclusiveAssort storeExclusiveAssort = new StoreExclusiveAssort();
        BeanUtils.copyProperties(storeExclusiveAssortDto, storeExclusiveAssort);
        storeExclusiveAssort.setCreateTime(DateUtils.getToday());
        Result result = new Result();
        result.setSuccess(storeExclusiveAssortMapper.insert(storeExclusiveAssort) > 0 ? true : false);
        return result;
    }

    @Override
    public Result updAssort(StoreExclusiveAssortDto storeExclusiveAssortDto) {
        StoreExclusiveAssort storeExclusiveAssort = new StoreExclusiveAssort();
        BeanUtils.copyProperties(storeExclusiveAssortDto, storeExclusiveAssort);
        Result result = new Result();
        result.setSuccess(storeExclusiveAssortMapper.updateByPrimaryKeySelective(storeExclusiveAssort) > 0 ? true : false);
        return result;
    }

    @Override
    public PageHelp<ProductDto> initProduct(ProductDto productDto) {
        PageHelp<ProductDto> pageHelp = new PageHelp<>();
        int page = productDto.getPage();
        if (page != 0) {
            productDto.setPage(page * productDto.getLimit());
        }
        List<ProductDto> list = storeProductAssortMapper.initProduct(productDto);
        int count = storeProductAssortMapper.initProductCount(productDto);

        pageHelp.setData(list);
        pageHelp.setCount(count);
        return pageHelp;
    }

    @Override
    public Result saveProductAssort(StoreProductAssortDto productAssortDto) {

//         先根据店铺和产品id清空产品分类
        storeProductAssortMapper.delProductAssort(productAssortDto);

        // 再重新添加产品分类
        StoreProductAssortDto tempDto = new StoreProductAssortDto();
        StoreProductAssort storeProductAssort;
        tempDto.setShopId(productAssortDto.getShopId());
        tempDto.setProductId(productAssortDto.getProductId());
        for (String temp : productAssortDto.getExclusiveAssortIds()) {
            tempDto.setExclusiveAssortId(Long.valueOf(temp));
            storeProductAssort = new StoreProductAssort();
            BeanUtils.copyProperties(tempDto, storeProductAssort);
            storeProductAssortMapper.insert(storeProductAssort);
        }
        return new Result();
    }
}