package com.soufang.app.vo.productManage;


import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.product.ProductDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailProductVo extends AppVo {
    ProductDto data;
}
