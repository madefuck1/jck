package com.soufang.feign;

import com.soufang.base.Result;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.product.ProductSpecDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.product.ProductManageSo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("core")
public interface ProductFeign {

    @RequestMapping(value = "core/productManage/getProductList", method = RequestMethod.POST)
    PageHelp<ProductDto> getProductList(ProductDto dto);

    @RequestMapping(value = "core/productManage/getHotProductList", method = RequestMethod.POST)
    PageHelp<ProductDto> getHotProductList();

    @RequestMapping(value = "core/productManage/getProductDetail", method = RequestMethod.POST)
    ProductDto getProductDetail(ProductDto dto);

    @RequestMapping(value = "core/productManage/getFootPrintList", method = RequestMethod.POST)
    List<ProductDto> getFootPrintList(ProductDto temp);

    @RequestMapping(value = "core/productManage/getProductSpecList", method = RequestMethod.POST)
    List<ProductSpecDto> getProductSpecList(ProductSpecDto dto);

    @RequestMapping(value = "core/productManage/getProductDetailBySpec_Number", method = RequestMethod.POST)
    ProductDto getProductDetailBySpec_Number(ProductDto so);

    @RequestMapping(value = "core/productManage/getShopProductList", method = RequestMethod.POST)
    PageHelp<ProductDto> getShopProductList(@RequestBody ProductManageSo productSo);

    @RequestMapping(value = "core/productManage/getDown", method = RequestMethod.POST)
    Result getDown(String[] ids);

    @RequestMapping(value = "core/productManage/putUp", method = RequestMethod.POST)
    Result putUp(String[] ids);

    @RequestMapping(value = "core/productManage/deleteProduct", method = RequestMethod.POST)
    Result deleteProduct(String[] ids);

    @RequestMapping(value = "core/productManage/createProductFirst", method = RequestMethod.POST)
    Result createProductFirst(@RequestBody ProductDto productDto);

    @RequestMapping(value = "core/productManage/updateProduct", method = RequestMethod.POST)
    Result updateProduct(@RequestBody ProductDto dto);
}
