package com.soufang.app.vo.productManage;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.product.ProductDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListHotProductReqVo extends AppVo {
    private List<ProductDto> data;



}
