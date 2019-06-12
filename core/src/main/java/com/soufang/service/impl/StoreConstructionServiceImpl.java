package com.soufang.service.impl;

import com.soufang.base.Result;
import com.soufang.base.dto.storeConstruction.StoreConstructionDto;
import com.soufang.base.dto.storeConstruction.StoreCurouselMapDto;
import com.soufang.base.dto.storeConstruction.StoreExclusiveAssortDto;
import com.soufang.base.dto.storeConstruction.StoreProductAssortDto;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.StoreConstructionMapper;
import com.soufang.mapper.StoreCurouselMapMapper;
import com.soufang.mapper.StoreExclusiveAssortMapper;
import com.soufang.mapper.StoreProductAssortMapper;
import com.soufang.model.StoreConstruction;
import com.soufang.model.StoreCurouselMap;
import com.soufang.model.StoreExclusiveAssort;
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
}