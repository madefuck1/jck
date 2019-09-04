package com.soufang.controller;


import com.soufang.base.BusinessException;
import com.soufang.base.PageBase;
import com.soufang.base.Result;
import com.soufang.base.dto.footprint.FootPrintDto;
import com.soufang.base.dto.product.*;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.dto.storeConstruction.StoreProductAssortDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.product.ProductManageSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.service.FootPrintService;
import com.soufang.service.ProductManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "core/productManage")
public class ProductManageController {

    @Autowired
    ProductManageService productManageService;

    @Autowired
    FootPrintService footPrintService;


    @RequestMapping(value = "createProductFirst", method = RequestMethod.POST)
    public Result createProductFirst(@RequestBody ProductDto productDto) {
        Result result = new Result();
        try {
            result = productManageService.createProductFirst(productDto);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }


    /**
     * 产品列表
     *
     * @param dto 入参为ProductDto对象
     * @return PageHelp<ProductDto>   返回产品数组
     */
    @RequestMapping(value = "getProductList", method = RequestMethod.POST)
    public PageHelp<ProductDto> list(@RequestBody ProductDto dto) {
        PageHelp<ProductDto> pageHelp = productManageService.list(dto);
        return pageHelp;
    }


    /**
     * 热门产品列表
     *
     * @return PageHelp<ProductDto>   返回产品数组
     */
    @RequestMapping(value = "getHotProductList", method = RequestMethod.POST)
    public PageHelp<ProductDto> hotList(@RequestBody Integer limit) {
        PageHelp<ProductDto> pageHelp = productManageService.hotList(limit);
        return pageHelp;
    }

    @RequestMapping(value = "getHotProductListPage", method = RequestMethod.POST)
    public PageHelp<ProductDto> hotListPage(@RequestBody PageBase page) {
        PageHelp<ProductDto> pageHelp = productManageService.hotListPage(page);
        return pageHelp;
    }

    /**
     * 产品明细详情(根据规格数量获取明细)
     *
     * @param dto
     * @return ProductDto
     */
    @RequestMapping(value = "getProductDetailBySpec_Number", method = RequestMethod.POST)
    public ProductDto getProductDetailBySpec_Number(@RequestBody ProductDto dto) {
        ProductDto productDto = productManageService.getProductDetailBySpec_Number(dto);
        return productDto;
    }

    /**
     * 产品明细详情
     *
     * @param dto
     * @return ProductDto
     */
    @RequestMapping(value = "getProductDetail", method = RequestMethod.POST)
    public ProductDto getDetail(@RequestBody ProductDto dto) {
        // 修改浏览记录
        productManageService.updateProductStatistics(dto.getProductId(), 3);
        if (dto.getUserId() != null) {
            // 封装浏览足迹数据
            FootPrintDto footPrintDto = new FootPrintDto();
            footPrintDto.setUserId(dto.getUserId());
            footPrintDto.setProductId(dto.getProductId());
            footPrintDto.setCreateTime(DateUtils.getToday());
            footPrintService.insert(footPrintDto);
        }

        ProductDto productDto = productManageService.getDetail(dto);
        productDto.setSpecDtoList(getSpecDtoList(productDto));
        return productDto;
    }

    /**
     * 封装specList
     *
     * @return
     */
    private List<SpecDto> getSpecDtoList(ProductDto productDto) {
        List<SpecDto> specDtos = new ArrayList<>();
        for (ProductSpecDto productSpecDto : productDto.getProductSpecDtoList()) {
            SpecDto spec = new SpecDto();
            int number = -1;
            for (int i = 0; i < specDtos.size(); i++) {
                if (productSpecDto.getSpecName().equals(specDtos.get(i).getSpecName())) {
                    number = i;
                }
            }
            SpecDetailDto specDetailDto = new SpecDetailDto();
            specDetailDto.setProductSpecId(productSpecDto.getProductSpecId());
            specDetailDto.setMinNumber(productSpecDto.getMinNumber());
            specDetailDto.setMaxNumber(productSpecDto.getMaxNumber());
            specDetailDto.setPriceSecret(productSpecDto.getPriceSecret());
            specDetailDto.setSpecNumber(productSpecDto.getSpecNumber());
            if (number < 0) {
                spec.setSpecName(productSpecDto.getSpecName());
                spec.setMin(productSpecDto.getMin());
                spec.setSpecDetailDtoList(new ArrayList<>());
                List<SpecDetailDto> list = new ArrayList<>();
                list.add(specDetailDto);
                spec.setSpecDetailDtoList(list);
                specDtos.add(spec);
            } else {
                List<SpecDetailDto> list = specDtos.get(number).getSpecDetailDtoList();
                list.add(specDetailDto);
                specDtos.get(number).setSpecDetailDtoList(list);
            }
        }
        return specDtos;
    }

    /**
     * 更新产品信息
     *
     * @param dto 入参 ProductDto 对象
     * @return result  result.getSuccess() (true/false)
     */
    @RequestMapping(value = "updateProduct", method = RequestMethod.POST)
    public Result updateProduct(@RequestBody ProductDto dto) {
        Result result = new Result();
        // 更新产品信息
        if (productManageService.updateProduct(dto))
            result.setSuccess(true);
        else
            result.setSuccess(false);
        return result;
    }

    /**
     * 更新产品颜色信息
     *
     * @param dto 入参 ProductColorDto 对象
     * @return result  result.getSuccess() (true/false)
     */
    @RequestMapping(value = "updateProductColor", method = RequestMethod.POST)
    public Result updateProductColor(@RequestBody ProductColorDto dto) {
        Result result = new Result();
        // 更新产品颜色信息
        if (productManageService.updateProductColor(dto))
            result.setSuccess(true);
        else
            result.setSuccess(false);
        return result;
    }

    /**
     * 更新产品规格信息
     *
     * @param dto 入参 ProductSpecDto 对象
     * @return result  result.getSuccess() (true/false)
     */
    @RequestMapping(value = "updateProductSpec", method = RequestMethod.POST)
    public Result updateProductSpec(@RequestBody ProductSpecDto dto) {
        Result result = new Result();
        // 更新产品颜色信息
        if (productManageService.updateProductSpec(dto))
            result.setSuccess(true);
        else
            result.setSuccess(false);
        return result;
    }

    /**
     * 新增产品
     *
     * @param dto 入参 registerProduct 对象
     * @return result  result.getSuccess() (true/false)
     */
    @RequestMapping(value = "registerProduct", method = RequestMethod.POST)
    public Result registerProduct(@RequestBody ProductDto dto) {
        Result result = new Result();
        productManageService.registerProduct(dto);
        result.setSuccess(true);
        return result;
    }

    /**
     * 足迹产品列表
     *
     * @param dto 入参为ProductDto对象
     * @return PageHelp<ProductDto>   返回产品数组
     */
    @RequestMapping(value = "getFootPrintList", method = RequestMethod.POST)
    public List<ProductDto> getFootPrintList(@RequestBody ProductDto dto) {
        List<ProductDto> list = productManageService.getFootPrintList(dto);
        return list;
    }

    /**
     * 通过产品id和规格名获取规格列表
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "getProductSpecList", method = RequestMethod.POST)
    public List<ProductSpecDto> getProductSpecList(@RequestBody ProductSpecDto dto) {
        return productManageService.getProductSpecList(dto);
    }

    /**
     * 根据productId specName number  获取对应价格
     *
     * @param shopCarProductDto
     * @return
     */
    @RequestMapping(value = "selectPrice", method = RequestMethod.POST)
    public BigDecimal selectPrice(@RequestBody ShopCarProductDto shopCarProductDto) {
        return productManageService.selectPrice(shopCarProductDto);
    }

    @RequestMapping(value = "getShopProductList", method = RequestMethod.POST)
    public PageHelp<ProductDto> getShopProductList(@RequestBody ProductManageSo productSo) {
        return productManageService.getShopProductList(productSo);
    }

    @RequestMapping(value = "getDown", method = RequestMethod.POST)
    public Result getDown(@RequestBody String[] ids) {
        Result result = productManageService.getDown(ids);
        return result;
    }

    @RequestMapping(value = "putUp", method = RequestMethod.POST)
    public Result putUp(@RequestBody String[] ids) {
        Result result = productManageService.putUp(ids);
        return result;
    }

    @RequestMapping(value = "deleteProduct", method = RequestMethod.POST)
    public Result deleteProduct(@RequestBody String[] ids) {
        Result result = productManageService.deleteProduct(ids);
        return result;
    }

    @RequestMapping(value = "getAssortProduct", method = RequestMethod.POST)
    public PageHelp<ProductDto> getAssortProduct(@RequestBody ProductManageSo so) {
        PageHelp<ProductDto> pageHelp = productManageService.getAssortProduct(so);
        return pageHelp;
    }

    @RequestMapping(value = "getIndexFootProduct", method = RequestMethod.POST)
    public PageHelp<ProductDto> getIndexFootProduct() {
        return productManageService.getIndexFootProduct();
    }


    @RequestMapping(value = "getProductTop6", method = RequestMethod.POST)
    public List<ProductDto> getProductTop6(@RequestBody Long shopId) {
        ProductDto productDto = new ProductDto();
        productDto.setShopId(shopId);
        return productManageService.getProductTop6(productDto);
    }

    @RequestMapping(value = "getProductByAssortId", method = RequestMethod.POST)
    public PageHelp<ProductDto> getProductByAssortId(@RequestBody StoreProductAssortDto productAssortDto) {
        return productManageService.getProductByAssortId(productAssortDto);
    }
}