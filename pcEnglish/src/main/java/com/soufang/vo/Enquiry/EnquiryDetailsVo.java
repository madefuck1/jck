/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: EnquiryDetailsVo
 * Author:   小三
 * Date:     2019/6/9 17:25
 * Description: 查询详情
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.vo.Enquiry;

import lombok.Getter;
import lombok.Setter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈查询详情〉
 *
 * @author 小三
 * @create 2019/6/9
 * @since 1.0.0
 */
@Getter
@Setter
public class EnquiryDetailsVo {
    //询价单号
    private String enquiryNumber;

    private String enquiryProductId;
}
