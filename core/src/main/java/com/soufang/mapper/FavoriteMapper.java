package com.soufang.mapper;

import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.search.favorite.FavoriteSo;
import com.soufang.model.Favorite;

import java.util.List;

public interface FavoriteMapper {
    int deleteByPrimaryKey(Long favoriteId);

    int insert(Favorite record);

    Long iSExistFavoriteId(FavoriteDto dto);

    int insertSelective(Favorite record);

    Favorite selectByPrimaryKey(Long favoriteId);

    int updateByPrimaryKeySelective(Favorite record);

    int updateByPrimaryKey(Favorite record);

    //获取列表信息
    List<FavoriteDto> getFavoriteList(FavoriteSo favoriteSo);
    //店铺查询
    List<FavoriteDto> getFavoriteShopList(FavoriteSo favoriteSo);


    int getFavoriteCount(FavoriteSo favoriteSo);

    int isFavorite(FavoriteDto dto);

    int delFavorite(FavoriteDto favoriteDto);
}