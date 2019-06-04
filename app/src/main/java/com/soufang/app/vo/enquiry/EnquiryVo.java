/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: EnquiryVo
 * Author:   小三
 * Date:     2019/5/29 10:04
 * Description: 询盘VO
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.enquiry;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.enquiry.EnquiryDto;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈询盘VO〉
 *
 * @author 小三
 * @create 2019/5/29
 * @since 1.0.0
 */
public class EnquiryVo extends AppVo {

    private List<EnquiryDto> data;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<EnquiryDto> getData() {
        return data;
    }

    public void setData(List<EnquiryDto> data) {
        this.data = data;
    }
}
