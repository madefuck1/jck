/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: purchseUseRefusedVo
 * Author:   小三
 * Date:     2019/6/3 7:31
 * Description: 报价
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.vo.purchase;

import lombok.Getter;
import lombok.Setter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈报价〉
 *
 * @author 小三
 * @create 2019/6/3
 * @since 1.0.0
 */
@Getter
@Setter
public class PurchseUseRefusedVo {

    private String purchaseNumber;
    private String enquiryProductId;

    private int offerStatus;

    private String enquiryNumber;


}
