/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: PurchaseListVo
 * Author:   小三
 * Date:     2019/5/29 11:27
 * Description: 报价
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.purchase;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.purchase.PurchaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈报价〉
 *
 * @author 小三
 * @create 2019/5/29
 * @since 1.0.0
 */
@Getter
@Setter
public class PurchaseListVo extends AppVo {

    private List<PurchaseDto> data;
    private int page ;
    private int limit ;
}
