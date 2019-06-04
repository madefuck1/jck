package com.soufang.mapper;

import com.soufang.base.dto.shopCar.ShopCarDto;
import com.soufang.model.ShopCar;

import java.util.List;

public interface ShopCarMapper {
    int deleteByPrimaryKey(Long shopCarId);

    int insert(ShopCar record);

    int insertSelective(ShopCar record);

    ShopCar selectByPrimaryKey(Long shopCarId);

    int updateByPrimaryKeySelective(ShopCar record);

    int updateByPrimaryKey(ShopCar record);

    ShopCar isExistByShop(ShopCarDto dto);

    List<ShopCarDto> selectShopCarWithProductIsNotExist(Long userId);

    List<ShopCarDto> getShopCarList(Long userId);

    int getShopCarListCount(Long userId);

    int getShopCarCountByUserId(Long userId);

    ShopCarDto getShopCarDto(Long shopCarId);

}