/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: EnquiryVo
 * Author:   小三
 * Date:     2019/5/20 10:03
 * Description: 求购
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.vo.Enquiry;

import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.vo.BaseVo;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈求购〉
 *
 * @author 小三
 * @create 2019/5/20
 * @since 1.0.0
 */
public class EnquiryVo extends BaseVo {
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
