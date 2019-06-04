package com.soufang.Vo.product;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.product.ProductDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ProductManageVo extends AdminVo {

    List<ProductDto> data;


}
