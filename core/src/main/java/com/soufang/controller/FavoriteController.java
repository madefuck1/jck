package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.favorite.FavoriteSo;
import com.soufang.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "core/favorite")
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    /**
     * 获取收藏列表信息-店铺和产品-类型字段的区分
     * @param  favoriteSo
     * @return PageHelp<FavoriteDto>
     */
    @RequestMapping(value = "getFavoriteList", method = RequestMethod.POST)
    public PageHelp<FavoriteDto> getFavoriteList(@RequestBody FavoriteSo favoriteSo) {
        // 获取收藏列表信息-店铺和产品-类型字段的区分
        PageHelp<FavoriteDto> pageHelp = favoriteService.getFavoriteList(favoriteSo);
        return pageHelp;
    }

    /**
     * 收藏到收藏夹
     * @param dto FavoriteDto
     * @return Result
     */
    @RequestMapping(value = "addFavorite", method = RequestMethod.POST)
    public Result addFavorite(@RequestBody FavoriteDto dto) {
        Result result = new Result();
        if (favoriteService.addFavorite(dto)) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }


    /**
     * 从收藏夹取消收藏
     * @param favoriteId
     * @return Result
     */
    @RequestMapping(value = "removeFavorite", method = RequestMethod.POST)
    public Result removeFavorite(@RequestBody Long favoriteId) {
        Result result = new Result();
        if (favoriteService.removeFavorite(favoriteId)) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 判断是否已经被收藏
     * @param dto
     * @return Result
     */
    @RequestMapping(value = "isFavorite", method = RequestMethod.POST)
    public Boolean isFavorite(@RequestBody FavoriteDto dto) {
        return favoriteService.isFavorite(dto);
    }


}


