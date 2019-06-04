package com.soufang.app.vo.productManage;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.product.ProductDto;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListProductVo extends AppVo {

    List<ProductDto> data;
    private Integer count;

}
