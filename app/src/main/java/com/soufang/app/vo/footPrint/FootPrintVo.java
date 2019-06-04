/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: FootPrintVo
 * Author:   小三
 * Date:     2019/5/20 16:52
 * Description: 我的足迹
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.footPrint;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.footprint.FootPrintDto;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈我的足迹〉
 *
 * @author 小三
 * @create 2019/5/20
 * @since 1.0.0
 */
public class FootPrintVo extends AppVo {
    private List<FootPrintDto> data;

    public List<FootPrintDto> getData() {
        return data;
    }

    public void setData(List<FootPrintDto> data) {
        this.data = data;
    }
}
