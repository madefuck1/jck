package com.soufang.service;

import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.favorite.FavoriteSo;

public interface FavoriteService {

    PageHelp<FavoriteDto> getFavoriteList(FavoriteSo favoriteSo);

    boolean addFavorite(FavoriteDto dto);

    boolean removeFavorite(Long favoriteId);

    boolean isFavorite(FavoriteDto dto);

    long iSExistFavoriteId(FavoriteDto dto);
}
