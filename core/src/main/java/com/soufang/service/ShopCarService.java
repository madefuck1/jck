package com.soufang.service;

import com.soufang.base.dto.shopCar.ShopCarDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.page.PageHelp;

import java.util.List;

public interface ShopCarService {

    PageHelp<ShopCarDto> getShopCarList(Long userId);

    void addToShopCar(ShopCarDto dto);

    void delShopCar(ShopCarDto dto);

    void editShopCar(ShopCarProductDto dto);

    int getShopCarCountByUserId(Long userId);

    ShopCarDto getShopCarDtoById(Long shopCarId);

    ShopCarProductDto getShopCarProductDtoById(Long shopCarProductId);

    boolean delShopCarAfterOrderGenerate(List<ShopCarDto> dtoList);
}
