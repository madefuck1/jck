package com.soufang.feign;

import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.page.PageHelp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("core")
public interface AdminProductManageFeign {

    @RequestMapping(value = "/core/productManage/getProductList", method = RequestMethod.POST)
    PageHelp<ProductDto> list(@RequestBody ProductDto dto);

    @RequestMapping(value = "/core/productManage/getProductDetail", method = RequestMethod.POST)
    ProductDto getDetail(@RequestBody Long productId);
}
