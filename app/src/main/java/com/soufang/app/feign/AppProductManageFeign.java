package com.soufang.app.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.page.PageHelp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(value = "core")
public interface AppProductManageFeign {

    @RequestMapping(value = "/core/productManage/getProductList", method = RequestMethod.POST)
    PageHelp<ProductDto> getProductList(@RequestBody ProductDto dto);

    @RequestMapping(value = "/core/productManage/getProductDetail", method = RequestMethod.POST)
    ProductDto getProductDetail(@RequestBody ProductDto ProductDto);

    @RequestMapping(value = "/core/assort/getAll", method = RequestMethod.POST)
    List<AssortDto> getAllAssort();

    @RequestMapping(value = "/core/commonPullDown/getDownList",method = RequestMethod.POST)
    List<AssortDto> getDownList(@RequestBody Long parentId);

    @RequestMapping(value = "/core/productManage/selectPrice", method = RequestMethod.POST)
    BigDecimal selectPrice(@RequestBody ShopCarProductDto shopCarProductDto);

    @RequestMapping(value = "core/productManage/getProductDetailBySpec_Number", method = RequestMethod.POST)
    ProductDto getProductDetailBySpec_Number(ProductDto so);

    @RequestMapping(value = "core/productManage/updateProduct", method = RequestMethod.POST)
    Result sellDownProduct(ProductDto dto);

    @RequestMapping(value = "core/productManage/getHotProductList", method = RequestMethod.POST)
    PageHelp<ProductDto> getHotProductList() ;

    @RequestMapping(value = "core/productManage/getProductTop6", method = RequestMethod.POST)
    List<ProductDto> getProductTop6(Long shopId);
}
