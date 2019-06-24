/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: ExhibitionListVo
 * Author:   小三
 * Date:     2019/6/24 16:27
 * Description: 展会列表
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.exhibition;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.exhibition.ExhibitionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈展会列表〉
 *
 * @author 小三
 * @create 2019/6/24
 * @since 1.0.0
 */
@Getter
@Setter
public class ExhibitionListVo extends AppVo {
    private List<ExhibitionDto> data;
    private int page ;
    private int limit ;
}
