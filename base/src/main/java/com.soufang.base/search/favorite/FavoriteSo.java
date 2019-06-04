/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: FavoriteSo
 * Author:   小三
 * Date:     2019/5/24 15:16
 * Description: 我的收藏
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.base.search.favorite;

/**
 * 〈一句话功能简述〉<br> 
 * 〈我的收藏〉
 *
 * @author 小三
 * @create 2019/5/24
 * @since 1.0.0
 */
public class FavoriteSo {
    private Integer page ;
    private Integer limit ;
    private Long userId;
    private Integer favoriteTargetType;

    public Integer getFavoriteTargetType() {
        return favoriteTargetType;
    }

    public void setFavoriteTargetType(Integer favoriteTargetType) {
        this.favoriteTargetType = favoriteTargetType;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getLimit() {
        return limit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
