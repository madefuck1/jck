//package com.soufang.controller;
//
//import com.soufang.base.Result;
//import com.soufang.base.dto.company.CompanyDto;
//import com.soufang.base.dto.product.ProductColorDto;
//import com.soufang.base.dto.product.ProductDto;
//import com.soufang.base.dto.product.ProductSpecDto;
//import com.soufang.base.dto.product.ProductStatisticsDto;
//import com.soufang.base.dto.shop.ShopDto;
//import com.soufang.base.dto.user.UserDto;
//import com.soufang.base.page.PageHelp;
//import com.soufang.base.utils.DateUtils;
//import com.soufang.feign.PcUserFeign;
//import com.soufang.feign.ProductFeign;
//import com.soufang.feign.ShopFeign;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Auther: chen
// * @Date: 2019/6/16
// * @Description:
// */
//@Controller
//public class NewController {
//
//    @Autowired
//    PcUserFeign pcUserFeign;
//
//    @Autowired
//    ProductFeign productFeign;
//
//    @Autowired
//    ShopFeign shopFeign;
//
//    @ResponseBody
//    @RequestMapping(value = "new" ,method = RequestMethod.GET)
//    public String newShopAndProduct(String sourceName , String targetName){
//        CompanyDto temp = pcUserFeign.getCompany(sourceName);
//        if(temp == null){
//            return "没有找到源公司";
//        }
//        System.out.println(sourceName);
//        Long userId =temp.getUserId();
//        UserDto userDto = pcUserFeign.getUserById(userId);
//        userDto.setEmail(DateUtils.getDate() +"@qq.com");
//        userDto.setPhone(DateUtils.getDate() +"@qq.com");
//        userDto.setUserId(null);
//        Result  result = pcUserFeign.register(userDto);
//        if(result.isSuccess()){
//            userDto.setUserId(Long.valueOf(result.getMessage()));
//
//            CompanyDto companyDto = pcUserFeign.getCompany(userId);
//            ShopDto shopDto = shopFeign.getByUserId(userId);
//            Long shopId = shopDto.getShopId();
//
//            companyDto.setCompId(null);
//            companyDto.setUserId(userDto.getUserId());
//            String info = companyDto.getCompanyInfo().replaceAll(companyDto.getCompName(),targetName);
//            companyDto.setCompName(targetName);
//            companyDto.setCompanyInfo(info);
//            shopDto.setShopId(null);
//            shopDto.setUserId(userDto.getUserId());
//            shopDto.setShopName(targetName);
//            userDto.setCompanyDto(companyDto);
//            userDto.setShopDto(shopDto);
//            Result userResult = pcUserFeign.settleShop(userDto);
//            if(userResult.isSuccess()){
//                ShopDto newShop = shopFeign.getByUserId(userDto.getUserId());
//                ProductDto productDto = new ProductDto();
//                productDto.setPage(1);
//                productDto.setLimit(20);
//                productDto.setShopId(shopId);
//                PageHelp<ProductDto> pageHelp = productFeign.getProductList(productDto);
//                for(ProductDto product : pageHelp.getData()){
//                    product.setShopName(targetName);
//                    product.setShopId(newShop.getShopId());
//                    product.setProductId(null);
//                    product.setProductLevel(1);
//                    List<ProductSpecDto> specList = new ArrayList<>();
//                    for(ProductSpecDto productSpecDto : product.getProductSpecDtoList()){
//                        productSpecDto.setProductId(null);
//                        productSpecDto.setProductSpecId(null);
//                        productSpecDto.setSpecNumber(productSpecDto.getSpecNumber().add(BigDecimal.ONE));
//                        specList.add(productSpecDto);
//                    }
//                    product.setProductSpecDtoList(specList);
//                    List<ProductColorDto> colorList = new ArrayList<>();
//                    for(ProductColorDto productColorDto : product.getProductColorDtoList()){
//                        productColorDto.setProductId(null);
//                        productColorDto.setProductColorId(null);
//                        colorList.add(productColorDto);
//                    }
//                    product.setProductColorDtoList(colorList);
//                    ProductStatisticsDto statisticsDto = product.getProductStatisticsDto();
//                    statisticsDto.setProductId(null);
//                    statisticsDto.setProductStatisticsId(null);
//                    product.setProductStatisticsDto(statisticsDto);
//                    Result productResult = productFeign.createProductFirst(product);
//                    if(!productResult.isSuccess()){
//                        return "false";
//                    }
//                }
//            }
//        }
//
//        return "true";
//    }
//}
