package com.soufang.app.controller;

import com.soufang.app.feign.AppExhibitionFeign;
import com.soufang.app.feign.AppProductManageFeign;
import com.soufang.app.feign.AppShopFeign;
import com.soufang.app.vo.exhibition.ExhibitionVo;
import com.soufang.app.vo.productManage.ListHotProductReqVo;
import com.soufang.app.vo.shop.ListShopVo;
import com.soufang.base.dto.exhibition.ExhibitionDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.exhibition.ExhibitionSo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("app/index/")
public class AppIndexController extends AppBaseController{

    @Autowired
    AppProductManageFeign appProductManageFeign;
    @Autowired
    AppShopFeign shopFeign;

    @Autowired
    AppExhibitionFeign appExhibitionFeign;


    @ResponseBody
    @RequestMapping(value = "getHotProductList",method = RequestMethod.POST)
    public ListHotProductReqVo getHotProductList( ){
        ListHotProductReqVo hotProduct = new ListHotProductReqVo();
        PageHelp<ProductDto> listDto = appProductManageFeign.getHotProductList();
        hotProduct.setData(listDto.getData());
        return hotProduct;
    }


    @ResponseBody
    @RequestMapping(value = "getHotShopList",method = RequestMethod.POST)
    public ListShopVo getHotShopList( ){
        ListShopVo shopVo = new ListShopVo();
        List<ShopDto> shopDtos = shopFeign.getHotShop();
        shopVo.setData(shopDtos);
        return shopVo;
    }

    @ResponseBody
    @RequestMapping(value = "getExhibitionList",method = RequestMethod.POST)
    public ExhibitionVo getExhibitionList(){
        ExhibitionSo so = new ExhibitionSo();
        so.setPage(0);
        so.setLimit(10);
        ExhibitionVo vo = new ExhibitionVo();
        PageHelp<ExhibitionDto> exhibitionDtos = appExhibitionFeign.selExhibitionList(so);
        vo.setData(exhibitionDtos.getData());
        return vo;
    }

    /**
     * 查看详情-展会
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getExhibitionList/{exhibitionId}",method = RequestMethod.POST)
    public ExhibitionVo getExhibitionList(@PathVariable String exhibitionId){
        ExhibitionSo so = new ExhibitionSo();
        so.setExhibitionId(Integer.valueOf(exhibitionId));
        so.setPage(0);
        so.setLimit(10);
        ExhibitionVo vo = new ExhibitionVo();
        PageHelp<ExhibitionDto> exhibitionDtos = appExhibitionFeign.selExhibitionList(so);
        vo.setData(exhibitionDtos.getData());
        return vo;
    }
}
