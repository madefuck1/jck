package com.soufang.app.vo.productManage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailSubmitProductReqVo {
    // 产品id
    private Long productId;
    // 产品数量
    private Long productNum;
    // 产品颜色
    private String productColor;
    // 产品规格
    private String productSpec;

}
