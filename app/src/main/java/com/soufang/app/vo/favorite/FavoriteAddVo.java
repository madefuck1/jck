/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: FavoriteVo
 * Author:   小三
 * Date:     2019/5/17 10:32
 * Description: 收藏VO
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.favorite;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈收藏VO〉
 *
 * @author 小三
 * @create 2019/5/17
 * @since 1.0.0
 */
public class FavoriteAddVo{


    private Long userId;

    private Long favoriteTargetId;

    private String favoriteTargetName;

    private Integer favoriteTargetType;

    private Date createTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFavoriteTargetId() {
        return favoriteTargetId;
    }

    public void setFavoriteTargetId(Long favoriteTargetId) {
        this.favoriteTargetId = favoriteTargetId;
    }

    public String getFavoriteTargetName() {
        return favoriteTargetName;
    }

    public void setFavoriteTargetName(String favoriteTargetName) {
        this.favoriteTargetName = favoriteTargetName;
    }

    public Integer getFavoriteTargetType() {
        return favoriteTargetType;
    }

    public void setFavoriteTargetType(Integer favoriteTargetType) {
        this.favoriteTargetType = favoriteTargetType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
