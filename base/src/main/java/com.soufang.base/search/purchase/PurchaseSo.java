/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: PurchaseSo
 * Author:   小三
 * Date:     2019/5/29 11:23
 * Description: 报价表
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.base.search.purchase;

import lombok.Getter;
import lombok.Setter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈报价表〉
 *
 * @author 小三
 * @create 2019/5/29
 * @since 1.0.0
 */
@Setter
@Getter
public class PurchaseSo {
    private Integer page ;
    private Integer limit ;
    private Long userId;
    private String enquiryNumber;
    private String purchaseNumber;

}
