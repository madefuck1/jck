package com.soufang.controller;


import com.soufang.Vo.product.ProductManageVo;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.page.PageHelp;
import com.soufang.feign.AdminProductManageFeign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin/productManage")
public class ProductManageController {

    @Autowired
    AdminProductManageFeign adminProductManageFeign;

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
        reqVo.setLimit(limit);
        reqVo.setPage(page);
        // 店铺id
        if (shopId != null && !"".equals(shopId))
            reqVo.setShopId(shopId);
        // 分类id
        if (assortId != null && !"".equals(assortId))
            reqVo.setAssortId(assortId);
        // 是否上架
        if (isUpper != null)
            reqVo.setIsUpper(isUpper);
        // 产品名称
        if (StringUtils.isNotBlank(productName))
            reqVo.setProductName(productName);
        PageHelp<ProductDto> list = adminProductManageFeign.list(reqVo);
        vo.setData(list.getData());
        return vo;
    }

    @RequestMapping(value = "openDetail/{id}", method = RequestMethod.GET)
    public String getDetail(@PathVariable Long id, ModelMap model) {
        model.put("productDetail", adminProductManageFeign.getDetail(id));
        return "/product/detail";
    }

}
