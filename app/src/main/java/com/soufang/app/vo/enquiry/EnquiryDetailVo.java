/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: EnquiryDetailVo
 * Author:   小三
 * Date:     2019/5/29 11:02
 * Description: 询盘详情
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.enquiry;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.enquiry.EnquiryDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 〈一句话功能简述〉<br> 
 * 〈询盘详情〉
 *
 * @author 小三
 * @create 2019/5/29
 * @since 1.0.0
 */
@Setter
@Getter
public class EnquiryDetailVo extends AppVo {

    private EnquiryDto dto;

    private String data;

    private Integer count;



}
