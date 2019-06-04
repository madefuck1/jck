/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: EnquiryAddVo
 * Author:   小三
 * Date:     2019/5/31 11:24
 * Description: 增加询盘
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.app.vo.enquiry;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈增加询盘〉
 *
 * @author 小三
 * @create 2019/5/31
 * @since 1.0.0
 */
@Setter
@Getter
public class EnquiryAddVo extends AppVo {
    private List<EnquiryProductDto> enquiryProductDto;

    private String enquiryNumber;

    private Long userId;

    private String enquiryTitle;

    private Integer enquiryType;
    private String strEnquiryType;

    private Integer enquiryStatus;

    //询盘状态-字符解释
    private String successMessage;

    private String strEnquiryStatus;

    private Date takeDate;
    private String strTakeDate;

    private String takeAddress;

    private String takeName;

    private String takePhone;

    private Date endTime;
    private String strEndTime;

    private Date createTime;
    private String strCreateTime;

    private String enquiryRemark;

    private String productUnit;

    private String product_detail;

    private String productSendCountry;

    private String productSendProvince;

    private String productSendCity;

    private Integer page;

    private Integer limit;
}
