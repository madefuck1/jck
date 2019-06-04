package com.soufang.mapper;

import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.search.product.ProductManageSo;
import com.soufang.model.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<ProductDto> getList(ProductDto so);

    int getCount(ProductDto so);

    ProductDto getDetail(ProductDto dto);

    List<ProductDto> getAllProduct();

    List<ProductDto> getHotList();

    List<ProductDto> getFootPrintList(ProductDto dto);

    ProductDto getProductDetailBySpec_Number(ProductDto dto);

    List<ProductDto> getShopProductList(ProductManageSo productSo);

    int getShopProductCount(ProductManageSo productSo);

    int getDown(List<Integer> productIds);

    int putUp(List<Integer> productIds);

    int deleteProduct(List<Integer> productIds);
}