package com.soufang.controller;


import com.soufang.Vo.product.ProductManageVo;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.page.PageHelp;
import com.soufang.feign.AdminAssortFeign;
import com.soufang.feign.AdminCommonPullDownFeign;
import com.soufang.feign.AdminProductManageFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/productManage")
public class ProductManageController {

    @Autowired
    AdminProductManageFeign adminProductManageFeign;

    @Autowired
    AdminAssortFeign adminAssortFeign;

    @Autowired
    AdminCommonPullDownFeign adminCommonPullDownFeign;

    @RequestMapping(value = "initPage", method = RequestMethod.GET)
    public String initPage() {
        return "/product/list";
    }


    // 店铺id，产品名称，所属分类id，是否上架
    @ResponseBody
    @RequestMapping(value = "productList", method = RequestMethod.GET)
    public ProductManageVo list(Integer limit, Integer page, Long shopId, String productName, Long assortId, Integer isUpper) {
        ProductManageVo vo = new ProductManageVo();
        ProductDto reqVo = new ProductDto();

        // 分类id
        if (assortId != null && !"".equals(assortId)) {
            AssortDto detail = adminAssortFeign.detail(assortId);
            List<AssortDto> tempList;
            StringBuilder stringBuilder;
            switch (detail.getAssortLevel()) {
                case 1:
                    // 选一级分类
                    tempList = adminCommonPullDownFeign.getAssortByParentId(detail.getAssortId());
                    stringBuilder = new StringBuilder();
                    if (tempList.size() != 0) {
                        for (AssortDto tempDto : tempList) {
                            if (tempDto.getChildren().size() != 0) {
                                for (AssortDto children : tempDto.getChildren()) {
                                    stringBuilder.append("," + children.getAssortId());
                                }
                            } else {
                                stringBuilder.append("," + tempDto.getAssortId());
                            }
                        }
                        reqVo.setAssortIds(stringBuilder.toString().substring(1));
                    } else {
                        reqVo.setAssortId(assortId);
                    }
                    break;
                case 2:
                    // 选二级分类
                    tempList = adminCommonPullDownFeign.getAssortAByParentId(detail.getAssortId());
                    stringBuilder = new StringBuilder();
                    if (tempList.size() != 0) {
                        for (AssortDto tempDto : tempList) {
                            if (tempDto.getChildren().size() != 0) {
                                for (AssortDto children : tempDto.getChildren()) {
                                    stringBuilder.append("," + children.getAssortId());
                                }
                                reqVo.setAssortIds(stringBuilder.toString().substring(1));
                            } else {
                                stringBuilder.append("," + tempDto.getAssortId());
                            }
                        }
                        reqVo.setAssortIds(stringBuilder.toString().substring(1));
                    } else {
                        reqVo.setAssortId(assortId);
                    }
                    break;
                case 3:
                    // 选三级分类
                    reqVo.setAssortIds(assortId + "");
                    break;
            }
        }

        reqVo.setLimit(limit);
        reqVo.setPage(page);
        // 店铺id
        if (shopId != null && !"".equals(shopId))
            reqVo.setShopId(shopId);
        // 是否上架
        if (isUpper != null)
            reqVo.setIsUpper(isUpper);
        // 产品名称
        if (StringUtils.isNotBlank(productName))
            reqVo.setProductName(productName);
        PageHelp<ProductDto> list = adminProductManageFeign.list(reqVo);
        vo.setData(list.getData());
        vo.setCount(list.getCount());
        return vo;
    }

    @RequestMapping(value = "openDetail/{id}", method = RequestMethod.GET)
    public String getDetail(@PathVariable Long id, ModelMap model) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(id);
        model.put("productDetail", adminProductManageFeign.getDetail(productDto));
        return "/product/detail";
    }

}
