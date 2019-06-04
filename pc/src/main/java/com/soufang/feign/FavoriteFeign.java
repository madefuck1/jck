package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.favorite.FavoriteSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface FavoriteFeign {

    @RequestMapping(value = "core/favorite/getFavoriteList",method = RequestMethod.POST)
    PageHelp<FavoriteDto> getFavoriteList(@RequestBody FavoriteSo favoriteSo);

    @RequestMapping(value = "core/favorite/addFavorite",method = RequestMethod.POST)
    Result addFavorite(@RequestBody FavoriteDto dto);

    @RequestMapping(value = "core/favorite/removeFavorite",method = RequestMethod.POST)
    Result removeFavorite(@RequestBody Long favoriteId);
}
