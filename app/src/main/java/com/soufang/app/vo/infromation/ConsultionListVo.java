/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: EnquiryListVo
 * Author:   小三
 * Date:     2019/5/29 9:41
 * Description: 询盘列表
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.infromation;

import com.soufang.app.vo.AppVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈询盘列表〉
 *
 * @author 小三
 * @create 2019/5/29
 * @since 1.0.0
 */
@Getter
@Setter
public class ConsultionListVo extends AppVo {
    private Map data;
    private int page ;
    private int limit ;
}
