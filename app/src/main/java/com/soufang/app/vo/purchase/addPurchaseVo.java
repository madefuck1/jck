/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: addPurchase
 * Author:   小三
 * Date:     2019/6/4 10:50
 * Description: 新增报价
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.purchase;

import com.soufang.app.vo.AppVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈新增报价〉
 *
 * @author 小三
 * @create 2019/6/4
 * @since 1.0.0
 */
@Getter
@Setter
public class addPurchaseVo extends AppVo {
    private String enquiryNumber;
    private int unitPrice;
    private String remark;

}
