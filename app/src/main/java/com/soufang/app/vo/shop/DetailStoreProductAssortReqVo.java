package com.soufang.app.vo.shop;

import com.soufang.base.PageBase;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailStoreProductAssortReqVo extends PageBase {
    private Long shopId;
    private Long assortId;

}
