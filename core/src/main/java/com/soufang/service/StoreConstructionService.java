package com.soufang.service;

import com.soufang.base.Result;
import com.soufang.base.dto.storeConstruction.StoreConstructionDto;
import com.soufang.base.dto.storeConstruction.StoreCurouselMapDto;
import com.soufang.base.dto.storeConstruction.StoreExclusiveAssortDto;
import com.soufang.base.dto.storeConstruction.StoreProductAssortDto;
import com.soufang.model.StoreConstruction;

import java.util.List;

public interface StoreConstructionService {

    StoreConstructionDto getStoreInfo(Long shopId);

    Result insert(StoreConstruction storeConstruction);

    Result update(StoreConstruction storeConstruction);

    List<StoreExclusiveAssortDto> getStoreAssort(StoreExclusiveAssortDto dto);

    List<StoreProductAssortDto> searchProduct(StoreProductAssortDto storeProductAssortDto);

    Result delAllChart(Long storeConstructionId);

    Result saveChart(List<StoreCurouselMapDto> storeCurouselMapDtoList);

    Result updExclusiveAssort(StoreExclusiveAssortDto storeExclusiveAssortDto);

    Result saveProductSort(StoreExclusiveAssortDto storeExclusiveAssortDto);
}
