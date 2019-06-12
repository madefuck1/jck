package com.soufang.mapper;

import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.model.Shop;

import java.util.List;
import java.util.Map;

public interface ShopMapper {

    /**
     * 由id拿店铺信息
     *
     * @param id
     * @return
     */
    Shop getById(Long id);


    /**
     * 按条件查询店铺集合
     *
     * @param shop
     * @return
     */
    List<Shop> getList(Shop shop);

    /**
     * 按条件查询店铺数量
     *
     * @param shop
     * @return
     */
    int getCount(Shop shop);

    /**
     * 由id删除店铺
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 添加店铺
     *
     * @param shop
     * @return
     */
    int addShop(Shop shop);

    /**
     * 审核店铺：通过
     *
     * @param id
     * @return
     */
    int reviewYes(Long id);

    /**
     * 审核店铺：通过
     *
     * @param
     * @return
     */
    int reviewNo(Shop shop);

    /**
     * 店铺名称下拉选择属性
     *
     * @return
     */
    List<Map<String, Object>> getShopIdAndNameOption();

    Shop getByUserId(Long userId);

    /**
     * app 获取店铺列表
     *
     * @param shopSo
     * @return
     */
    List<ShopDto> appGetList(ShopSo shopSo);

    int appGetCount(ShopSo shopSo);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
}
