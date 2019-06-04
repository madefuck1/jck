/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: FavoriteListVo
 * Author:   小三
 * Date:     2019/5/23 16:17
 * Description: 列表VO
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.favorite;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.favorite.FavoriteDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈列表VO〉
 *
 * @author 小三
 * @create 2019/5/23
 * @since 1.0.0
 */
@Getter
@Setter
public class FavoriteListVo extends AppVo{

    private List<FavoriteDto> data;
    private int page ;
    private int limit ;

}
