package com.soufang.service;

import com.soufang.base.Result;
import com.soufang.base.dto.product.ProductColorDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.product.ProductSpecDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.product.ProductManageSo;

import java.math.BigDecimal;
import java.util.List;

public interface ProductManageService {


    PageHelp<ProductDto> list(ProductDto dto);

    List<ProductDto> allProduct();

    ProductDto getDetail(ProductDto dto);

    boolean updateProduct(ProductDto dto);

    boolean updateProductColor(ProductColorDto dto);

    boolean updateProductSpec(ProductSpecDto dto);

    /**
     * 更新对产品浏览、收藏、交易操作次数纪录信息
     *
     * @param productId,type
     * @return
     */
    boolean updateProductStatistics(Long productId,Integer type);

    void registerProduct(ProductDto dto);

    PageHelp<ProductDto> hotList( );

    List<ProductDto> getFootPrintList(ProductDto dto);

    List<ProductSpecDto> getProductSpecList(ProductSpecDto dto);

    BigDecimal selectPrice(ShopCarProductDto shopCarProductDto);

    ProductDto getProductDetailBySpec_Number(ProductDto dto);

    PageHelp<ProductDto> getShopProductList(ProductManageSo productSo);

    Result getDown(String[] ids);

    Result putUp(String[] ids);

    Result deleteProduct(String[] ids);

    Result createProductFirst(ProductDto productDto);
}
