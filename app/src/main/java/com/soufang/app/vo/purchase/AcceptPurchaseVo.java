/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: acceptPurchaseVo
 * Author:   小三
 * Date:     2019/6/3 16:57
 * Description: 接收报价
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.purchase;

import com.soufang.app.vo.AppVo;
import lombok.Getter;
import lombok.Setter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈接收报价〉
 *
 * @author 小三
 * @create 2019/6/3
 * @since 1.0.0
 */
@Getter
@Setter
public class AcceptPurchaseVo extends AppVo {
    private String enquiryStatus;
    private  String statusMessage;
}
