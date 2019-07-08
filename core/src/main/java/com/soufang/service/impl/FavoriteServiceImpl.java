package com.soufang.service.impl;

import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.favorite.FavoriteSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.FavoriteMapper;
import com.soufang.model.Favorite;
import com.soufang.service.FavoriteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    /**
     * 获取收藏列表信息-店铺和产品-类型字段的区分
     *
     * @param favoriteSo
     * @return
     */
    @Override
    public PageHelp<FavoriteDto> getFavoriteList(FavoriteSo favoriteSo) {
        PageHelp<FavoriteDto> favorite = new PageHelp<>();
        //根据页数查信息/根据页数查信息
        favoriteSo.setPage((favoriteSo.getPage() - 1) * 5);
        // 查询对应用户的收藏数量
        int count = favoriteMapper.getFavoriteCount(favoriteSo);
        favorite.setCount(count);
        List<FavoriteDto> lists = new ArrayList<>();
        if(favoriteSo.getFavoriteTargetType()==1){
             lists = favoriteMapper.getFavoriteList(favoriteSo);
            //更改图片地址
            for (int i = 0; i < lists.size(); i++) {
                FavoriteDto favoriteDto = lists.get(i);
                ProductDto productDto = favoriteDto.getProductDto();
                productDto.setProductImage(PropertiesParam.file_pre + productDto.getProductImage());
                productDto.setUrl(productDto.getProductImage().split(";")[0]);
            }
        }else{
            lists=favoriteMapper.getFavoriteShopList(favoriteSo);
            //更改店铺头像地址
            for (int i = 0; i < lists.size(); i++) {
                FavoriteDto favoriteDto = lists.get(i);
                ShopDto shopDto = favoriteDto.getShopDto();
                shopDto.setAvatarUrl(PropertiesParam.file_pre + shopDto.getAvatarUrl());
            }
        }

        favorite.setData(lists);
        return favorite;
    }

    /**
     * 新增收藏-店铺和产品-类型字段的区分
     *
     * @param dto
     * @return
     */
    @Override
    public boolean addFavorite(FavoriteDto dto) {
        Favorite favorite = new Favorite();
        //收藏时间
        favorite.setCreateTime(DateUtils.getToday());
        // 从 FavoriteDto 拷贝到 Favorite
        BeanUtils.copyProperties(dto, favorite);
        favorite.setCreateTime(DateUtils.getToday());
        int I = favoriteMapper.insert(favorite);
        return I > 0 ? true : false;
    }

    //判断是否收藏-返回收藏ID
    public Long iSExistFavoriteId(FavoriteDto dto) {
        return favoriteMapper.iSExistFavoriteId(dto);
    }

    /**
     * 删除收藏-店铺和产品公用
     *
     * @param favoriteId
     * @return
     */
    @Override
    public boolean removeFavorite(Long favoriteId) {
        // 删除对应的收藏信息
        int I = favoriteMapper.deleteByPrimaryKey(favoriteId);
        return I > 0 ? true : false;
    }

    @Override
    public boolean isFavorite(FavoriteDto dto) {
        int I = favoriteMapper.isFavorite(dto);
        return I > 0 ? true : false;
    }

    @Override
    public void delFavorite(FavoriteDto favoriteDto) {
        favoriteMapper.delFavorite(favoriteDto);
    }
}
