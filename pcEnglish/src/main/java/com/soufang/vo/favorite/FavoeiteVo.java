/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: FavoeiteVo
 * Author:   小三
 * Date:     2019/5/17 11:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.vo.favorite;

import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.vo.BaseVo;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 小三
 * @create 2019/5/17
 * @since 1.0.0
 */
public class FavoeiteVo extends BaseVo {

    private List<FavoriteDto> data;

    public List<FavoriteDto> getData() {
        return data;
    }

    public void setData(List<FavoriteDto> data) {
        this.data = data;
    }
}
