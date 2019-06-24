package com.soufang.app.vo.productManage;

import com.soufang.base.dto.product.ProductDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListHotProductReqVo {
    private List<ProductDto> data;



}
