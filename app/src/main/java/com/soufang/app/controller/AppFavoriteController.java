/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: FavoriteController
 * Author:   小三
 * Date:     2019/5/23 10:53
 * Description: 我的收藏
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.controller;

import com.soufang.app.config.interceptor.AppMemberAccess;
import com.soufang.app.feign.FavoriteFeign;
import com.soufang.app.vo.favorite.*;
import com.soufang.base.Result;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.favorite.FavoriteSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈我的收藏〉
 *
 * @author 小三
 * @create 2019/5/23
 * @since 1.0.0
 */
@Controller
@RequestMapping("app/favorite/")
public class AppFavoriteController extends AppBaseController{
    @Autowired
    FavoriteFeign favoriteFeign;


    /**
     * 店铺收藏列表
     * @param favoriteSo
     * @param request
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getShopList",method = RequestMethod.POST)
    public FavoriteListVo getShopList(@RequestBody FavoriteSo favoriteSo , HttpServletRequest request){
        FavoriteListVo favoriteListVo = new FavoriteListVo();
        //获取用户信息
        UserDto userInfo = this.getUserInfo(request);
        FavoriteDto favoriteDto = new FavoriteDto();
        //用户ID
        favoriteDto.setUserId(userInfo.getUserId());
        //页码
        favoriteDto.setPage(favoriteSo.getPage());
        //每页显示数量
        favoriteDto.setLimit(favoriteSo.getLimit());
        //1产品 2店铺
        favoriteDto.setFavoriteTargetType(2);
        PageHelp<FavoriteDto> pageHelp=favoriteFeign.getFavoriteList(favoriteDto);
        favoriteListVo.setData(pageHelp.getData());
        return favoriteListVo;
    }

    /**
     * 产品收藏列表
     * @param request
     * @returnk
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "getProductList",method = RequestMethod.POST)
    public FavoriteListVo getProductList(@RequestBody FavoriteSo favoriteSo ,HttpServletRequest request){
        FavoriteListVo favoriteListVo = new FavoriteListVo();
        //获取用户信息
        UserDto userInfo = this.getUserInfo(request);
        FavoriteDto favoriteDto = new FavoriteDto();
        //用户ID
        favoriteDto.setUserId(userInfo.getUserId());
        //页码
        favoriteDto.setPage(favoriteSo.getPage());
        //每页显示数量
        favoriteDto.setLimit(favoriteSo.getLimit());
        //1产品 2店铺
        favoriteDto.setFavoriteTargetType(1);
        PageHelp<FavoriteDto> pageHelp=favoriteFeign.getFavoriteList(favoriteDto);
        favoriteListVo.setData(pageHelp.getData());
        return favoriteListVo;
    }


    /**
     * 新增收藏
     * @param favoriteAddVo
     * @param request
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "saveFavorite",method = RequestMethod.POST)
    public FavoriteVo saveFavorite(@RequestBody FavoriteAddVo favoriteAddVo, HttpServletRequest request){
        Result result= new Result();
        //获取用户信息
        UserDto userInfo = this.getUserInfo(request);
        favoriteAddVo.setUserId(userInfo.getUserId());
        //需要对参数判断/任意为空则不插入数据-给出提示
        if("".equals(favoriteAddVo.getFavoriteTargetType())||"".equals(favoriteAddVo.getFavoriteTargetId())||"".equals(favoriteAddVo.getFavoriteTargetName())){
            result.setSuccess(false);
            result.setMessage("收藏失败");
        }else {
            //第一次为新增/二次为取消
            FavoriteDto favoriteDto = new FavoriteDto();
            favoriteDto.setFavoriteTargetType(favoriteAddVo.getFavoriteTargetType());
            favoriteDto.setFavoriteTargetId(favoriteAddVo.getFavoriteTargetId());
            favoriteDto.setFavoriteTargetName(favoriteAddVo.getFavoriteTargetName());
            favoriteDto.setUserId(userInfo.getUserId());
            Long favoriteId= favoriteFeign.iSExistFavoriteId(favoriteDto);
            if("".equals(favoriteId)||favoriteId==null){
                //新增
                result = favoriteFeign.addFavorite(favoriteDto);
                result.setSuccess(false);
                result.setMessage("收藏成功");
            }else{
                //删除
                result= favoriteFeign.removeFavorite(favoriteId);
                result.setSuccess(true);
                result.setMessage("取消收藏");
            }
        }
            return judgefavoeiteVo(result);
    }

    private FavoriteVo judgefavoeiteVo(Result result){
        FavoriteVo FavoriteVo = new FavoriteVo();
        if(result.isSuccess()){
            FavoriteVo.setMessage(result.getMessage());
            FavoriteVo.setData(result.isSuccess());
        }else {
            FavoriteVo.setData(result.isSuccess());
            FavoriteVo.setMessage(result.getMessage());
        }
        return FavoriteVo;
    }

    /**
     * 是否被收藏
     * @param request
     * @param reqVo
     * @return
     */
  /*  @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "checkFavorite",method = RequestMethod.POST)
    public IsFavoriteVo isFavorite(HttpServletRequest request, @RequestBody FavoriteCReqVo reqVo ){
        IsFavoriteVo appVo = new IsFavoriteVo();
        FavoriteDto dto = new FavoriteDto();
        UserDto userInfo = getUserInfo(request);
        // 封装数据 （入参 收藏类型 、收藏目标id、 用户id）
        dto.setUserId(userInfo.getUserId());
        dto.setFavoriteTargetType(reqVo.getFavoriteTargetType());
        dto.setFavoriteTargetId(reqVo.getFavoriteTargetId());
        if(favoriteFeign.isFavorite(dto)){
            appVo.setSuccess(true);
            appVo.setMessage("收藏成功！");
            appVo.setData(true);
        }else{
            appVo.setSuccess(false);
            appVo.setMessage("收藏失败");
            appVo.setData(false);
        }
        return appVo;
    }*/
    /**
     * 是否被收藏
     * @param request
     * @param favoriteAddVo
     * @return
     */
    @AppMemberAccess
    @ResponseBody
    @RequestMapping(value = "checkFavorite",method = RequestMethod.POST)
    public FavoriteVo isFavorite(@RequestBody FavoriteAddVo favoriteAddVo, HttpServletRequest request){
        Result result= new Result();
        //获取用户信息
        UserDto userInfo = this.getUserInfo(request);
        favoriteAddVo.setUserId(userInfo.getUserId());
        FavoriteDto favoriteDto = new FavoriteDto();
        favoriteDto.setFavoriteTargetType(favoriteAddVo.getFavoriteTargetType());
        favoriteDto.setFavoriteTargetId(favoriteAddVo.getFavoriteTargetId());
        favoriteDto.setFavoriteTargetName(favoriteAddVo.getFavoriteTargetName());
        favoriteDto.setUserId(userInfo.getUserId());
        Long favoriteId= favoriteFeign.iSExistFavoriteId(favoriteDto);
        if("".equals(favoriteId)||favoriteId==null){
            result.setSuccess(true);
            result.setMessage("没有收藏");
        }else{
            result.setSuccess(false);
            result.setMessage("已收藏");
        }
        return judgefavoeiteVo(result);
    }

}
