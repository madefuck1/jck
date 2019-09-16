package com.soufang.service;

import com.soufang.base.Result;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.storeConstruction.*;
import com.soufang.base.page.PageHelp;
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

    Result publish(Long shopId);

    Result delAssortByKey(Long assortId);

    Result registerAssort(StoreExclusiveAssortDto storeExclusiveAssortDto);

    Result updAssort(StoreExclusiveAssortDto storeExclusiveAssortDto);

    PageHelp<ProductDto> initProduct(ProductDto productDto);

    Result saveProductAssort(StoreProductAssortDto productAssortDto);

    Boolean isExistStoreInfo(Long shopId);

    //保存店铺视频
    Result saveStoreView(StoreViewDto dto);

    List<StoreViewDto> getStoreViews(Long shopId);
}
