package com.soufang.app.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.page.PageHelp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("core")
public interface FavoriteFeign {

    //所有数据
    @RequestMapping(value = "core/favorite/getFavoriteList",method = RequestMethod.POST)
    PageHelp<FavoriteDto> getFavoriteList(@RequestBody FavoriteDto dto);

    //新增收藏
    @RequestMapping(value = "core/favorite/addFavorite",method = RequestMethod.POST)
    Result addFavorite(@RequestBody FavoriteDto dto);

    //删除收藏
    @RequestMapping(value = "core/favorite/removeFavorite",method = RequestMethod.POST)
    Result removeFavorite(@RequestBody Long favoriteId);

    /**
     * 是否收藏
     * @param dto
     * @return
     */
    @RequestMapping(value = "core/favorite/isFavorite",method = RequestMethod.POST)
    Boolean isFavorite(FavoriteDto dto);

    /**
     * 是否收藏 -返回ID
     * @param dto
     * @return
     */
    @RequestMapping(value = "core/favorite/iSExistFavoriteId",method = RequestMethod.POST)
   long iSExistFavoriteId(FavoriteDto dto);

}
