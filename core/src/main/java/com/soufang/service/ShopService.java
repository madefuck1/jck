package com.soufang.service;

import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.search.shop.ShopSo;


import java.util.List;


public interface ShopService {

    /**
     * 由id拿店铺信息
     * @param id
     * @return
     */
    ShopDto getById(Long id);

    /**
     * 按条件查询店铺集合
     * @param shopSo
     * @return
     */
    List<ShopDto> getList(ShopSo shopSo);

    /**
     * 按条件查询店铺数量
     * @param shopSo
     * @return
     */
    int getCount(ShopSo shopSo);

    /**
     * 由id删除店铺
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 添加店铺
     * @param shopDto
     * @return
     */
    int addShop(ShopDto shopDto);

    /**
     * 审核店铺：通过
     * @param id
     * @return
     */
    int reviewYes(Long id);

    /**
     * 审核店铺：不通过
     * @param  shopDto
     * @return
     */
    int reviewNo(ShopDto shopDto);

    //通过userId获取店铺信息
    ShopDto getByUserId(Long userId);

    List<ShopDto> appGetList(ShopSo shopSo);

    void updateShop(ShopDto shopDto);
}
