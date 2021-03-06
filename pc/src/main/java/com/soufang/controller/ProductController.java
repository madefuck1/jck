package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.banner.BannerDto;
import com.soufang.base.dto.favorite.FavoriteDto;
import com.soufang.base.dto.product.ProductColorDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.product.ProductSpecDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.assess.AssessSo;
import com.soufang.base.search.assort.AssortSo;
import com.soufang.base.search.product.ProductManageSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.utils.FtpClient;
import com.soufang.config.interceptor.MemberAccess;
import com.soufang.feign.*;
import com.soufang.vo.product.ListProductVo;
import com.soufang.vo.product.ListSpecReqVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "product")
public class ProductController extends BaseController {

    @Autowired
    ProductFeign productFeign;

    @Autowired
    CommonPullDownFeign commonPullDownFeign;

    @Autowired
    AssessFeign assessFeign;

    @Autowired
    FavoriteFeign favoriteFeign;

    @Autowired
    BannerFeign bannerFeign;

    @Value("${upload.product}")
    private String productUrl;

    @MemberAccess
    @RequestMapping(value = "createProduct", method = RequestMethod.GET)
    public String createProduct() {
        return "sellerCenter/releaseProduct";
    }

    /**
     * 新增产品页面
     *
     * @param assortId
     * @param model
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "createProductTwo", method = RequestMethod.GET)
    public String createProductTwo(Long assortId, ModelMap model) {
        AssortDto assortThree = commonPullDownFeign.detail(assortId);
        AssortDto assortTwo = commonPullDownFeign.detail(assortThree.getParentId());
        AssortDto assortOne = commonPullDownFeign.detail(assortTwo.getParentId());
        model.put("assortOne", assortOne);
        model.put("assortTwo", assortTwo);
        model.put("assortThree", assortThree);

        return "sellerCenter/createProduct";
    }

    //新建产品  分为两步，一步提交数据，一步提交图片
    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Result createProduct(@ModelAttribute ProductDto productDto, HttpServletRequest request) {
        productDto = init(productDto, request);
        Result result = productFeign.createProductFirst(productDto);
        return result;
    }

    private ProductDto init(ProductDto productDto, HttpServletRequest request) {
        Date date = DateUtils.getToday();
        ShopDto shopDto = getShopInfo(request);
        productDto.setShopId(shopDto.getShopId());
        productDto.setShopName(shopDto.getShopName());
        productDto.setIsUpper(1);
        AssortDto assortDto = commonPullDownFeign.detail(productDto.getAssortId());
        productDto.setAssortName(assortDto.getAssortName());
        productDto.setKey1(assortDto.getKey1());
        productDto.setKey2(assortDto.getKey2());
        productDto.setKey3(assortDto.getKey3());
        productDto.setKey4(assortDto.getKey4());
        productDto.setKey5(assortDto.getKey5());
        //TODO  productDetail productImage productUrl
        productDto.setProductLevel(1);
        productDto.setCreateTime(date);
        List<ProductColorDto> productColorDtos = new ArrayList<>();
        for (ProductColorDto productColorDto : productDto.getProductColorDtoList()) {
            productColorDto.setIsSpot(0);
            productColorDto.setCreateTime(date);
            productColorDto.setSpock(new BigDecimal("99999999"));
            productColorDtos.add(productColorDto);
        }
        productDto.setProductColorDtoList(productColorDtos);
        List<ProductSpecDto> productSpecDtos = new ArrayList<>();
        for (ProductSpecDto productSpecDto : productDto.getProductSpecDtoList()) {
            if (productSpecDto.getMaxNumber() == null) {
                productSpecDto.setMaxNumber(99999999L);
            }
            productSpecDto.setPriceSecret(0);
            productSpecDto.setCreateTime(date);
            productSpecDtos.add(productSpecDto);
        }
        productDto.setProductSpecDtoList(productSpecDtos);
        return productDto;
    }

    @MemberAccess
    @ResponseBody
    @RequestMapping(value = "createSecond", method = RequestMethod.POST)
    public Result createSecond(MultipartFile[] files, MultipartFile[] files2,MultipartFile[] files3, Long productId) {
        ProductDto temp = new ProductDto();
        temp.setProductId(productId);
        ProductDto productDto = productFeign.getProductDetail(temp);
        StringBuffer productImage = new StringBuffer();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> map = FtpClient.uploadImage(files[i], productUrl);
            if ((boolean) map.get("success")) {
                productImage.append(map.get("uploadName").toString() + ";");
            }
        }
        StringBuffer productDetail = new StringBuffer();
        for (int i = 0; i < files2.length; i++) {
            Map<String, Object> map = FtpClient.uploadImage(files2[i], productUrl);
            if ((boolean) map.get("success")) {
                productDetail.append(map.get("uploadName").toString() + ";");
            }
        }
       StringBuffer productVedio = new StringBuffer();
//        Map<String, Object> map = FtpClient.uploadImage(files3[0], productUrl);
//        if ((boolean) map.get("success")) {
//            productVedio.append("video"+map.get("uploadName").toString() + ";");
//        }
        //图片/视频地址
        productDto.setProductImage(productImage.toString()+productVedio.toString());
        productDto.setProductDetail(productDetail.toString());
        Result result = productFeign.updateProduct(productDto);
        return result;
    }

    /**
     * 编辑产品
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "editProduct", method = RequestMethod.GET)
    public String editProduct(HttpServletRequest request,Long productId,ModelMap map) {
        UserDto userInfo = this.getUserInfo(request);
        ProductDto dto = new ProductDto();
        dto.setUserId(userInfo.getUserId());
        dto.setProductId(productId);
        ProductDto productDto = productFeign.getProductDetail(dto);
        map = getAssortForEdit(productDto.getAssortId(),map);
        //将附加的产品属性转化为json
        StringBuilder str = new StringBuilder();
        if (productDto.getKv1() != null) {
            str.append(productDto.getKv1() + ";");
        }
        if (productDto.getKv2() != null) {
            str.append(productDto.getKv2() + ";");
        }
        if (productDto.getKv3() != null) {
            str.append(productDto.getKv3() + ";");
        }
        if (productDto.getKv4() != null) {
            str.append(productDto.getKv4() + ";");
        }
        if (productDto.getKv5() != null) {
            str.append(productDto.getKv5() + ";");
        }
        if (!"".equals(productDto.getProductJson()) && productDto.getProductJson() != null) {
//            str.append(productDto.getProductJson().substring(1, productDto.getProductJson().length() - 1).replace("\"", ""));
            str.append(productDto.getProductJson() + ";");
        }
        String[] productJson = null;
        productJson = str.toString().split(";");
        map.put("productJson", productJson);

        map.put("productDto",productDto);
        return "sellerCenter/editProduct";
    }

    public ModelMap getAssortForEdit(Long assortId, ModelMap model) {
        AssortDto assortThree = commonPullDownFeign.detail(assortId);
        AssortDto assortTwo = commonPullDownFeign.detail(assortThree.getParentId());
        AssortDto assortOne = commonPullDownFeign.detail(assortTwo.getParentId());
        model.put("assortOne", assortOne);
        model.put("assortTwo", assortTwo);
        model.put("assortThree", assortThree);

        return model;
    }

    /**
     * 检索页面
     *
     * @param
     * @param
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(ModelMap model, String searchProductName, String assortId, String assortName) {
        Integer limit = 40;
        ProductDto dto = new ProductDto();
        dto.setProductName(searchProductName);
        dto.setIsUpper(1);//上架的产品
        model.put("searchProductName", searchProductName);
        // 设置翻页
        dto.setPage(0);
        dto.setLimit(limit);
        model.put("assortLevel", assortId == null ? "" : assortId.substring(0, 1));
        // 分类id
        Long tempId;
        List<AssortDto> tempList;
        StringBuilder stringBuilder;
        AssortSo so;
        Map<String, Object> tempMap;
        switch (assortId == null ? "" : assortId.substring(0, 1)) { // assortId 判断，是否为null
            case "A":
                // 选一级分类
                tempId = Long.valueOf(assortId.substring(1));
                tempList = commonPullDownFeign.getAssortByParentId(tempId);
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
                    dto.setAssortIds(stringBuilder.toString().substring(1));
                } else {
                    dto.setAssortId(tempId);
                }
                // 一级分类
                model.put("assortIdA", tempId);
                model.put("assortNameA", assortName);
                // 二级分类
                model.put("assortIdB", "无");
                model.put("assortNameB", "无");
                // 三级分类
                model.put("assortIdC", "无");
                model.put("assortNameC", "无");
                break;
            case "B":
                // 选二级分类
                tempId = Long.valueOf(assortId.substring(1));
                tempList = commonPullDownFeign.getAssortAByParentId(tempId);
                stringBuilder = new StringBuilder();
                if (tempList.size() != 0) {
                    for (AssortDto tempDto : tempList) {
                        if (tempDto.getChildren().size() != 0) {
                            for (AssortDto children : tempDto.getChildren()) {
                                stringBuilder.append("," + children.getAssortId());
                            }
                            dto.setAssortIds(stringBuilder.toString().substring(1));
                        } else {
                            stringBuilder.append("," + tempDto.getAssortId());
                        }
                    }
                    dto.setAssortIds(stringBuilder.toString().substring(1));
                } else {
                    dto.setAssortIds(assortId.substring(1));
                }
                so = new AssortSo();
                so.setAssortIdB(tempId);
                tempMap = commonPullDownFeign.getAssortByKey(so);
                // 一级分类
                model.put("assortIdA", tempMap.get("aId"));
                model.put("assortNameA", tempMap.get("aName"));
                // 二级分类
                model.put("assortIdB", tempMap.get("bId"));
                model.put("assortNameB", assortName);
                // 三级分类
                model.put("assortIdC", tempMap.get("cId"));
                model.put("assortNameC", "无");
                break;
            case "C":
                // 选三级分类
                dto.setAssortIds(assortId.substring(1));
                so = new AssortSo();
                so.setAssortIdC(Long.valueOf(assortId.substring(1)));
                tempMap = commonPullDownFeign.getAssortByKey(so);
                // 一级分类
                model.put("assortIdA", tempMap.get("aId"));
                model.put("assortNameA", tempMap.get("aName"));
                // 二级分类
                model.put("assortIdB", tempMap.get("bId"));
                model.put("assortNameB", tempMap.get("bName"));
                // 三级分类
                model.put("assortIdC", tempMap.get("cId"));
                model.put("assortNameC", assortName);
                break;
            default:
                // 一级分类
                model.put("assortIdA", "无");
                model.put("assortNameA", "无");
                // 二级分类
                model.put("assortIdB", "无");
                model.put("assortNameB", "无");
                // 三级分类
                model.put("assortIdC", "无");
                model.put("assortNameC", "无");
                break;
        }
        // 产品列表
        PageHelp<ProductDto> productList = productFeign.getProductList(dto);
        model.put("productList", productList.getData());

        // 热门产品
        int co = productList.getData().size();
        int i = (co / 4 )+1;
        PageHelp<ProductDto> hotProductList = productFeign.getHotProductList(i);
        model.put("HotProductList", hotProductList.getData());

        //  产品数量
        model.put("productCount", productList.getCount());

        // 面包屑 最上级
        List<AssortDto> firstAssort = commonPullDownFeign.getFirstAssort();
        model.put("assortA", firstAssort);

        return "product/search";
    }

    /**
     * 检索页面 ajax请求刷页面
     *
     * @param
     * @param
     * @param
     * @param orderSort(0:default;1:default;2:销量从高到低;3:价格从低到高;4:价格从高到低;)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "searchAssortWithSort", method = RequestMethod.GET)
    public Map<String, Object> searchAssortWithSort(String searchProductName, String assortId, Integer orderSort, Integer pageIndex) {
        Map<String, Object> map = new HashMap<>();
        Integer limit = 40;
        ProductDto dto = new ProductDto();
        dto.setProductName(searchProductName);
        dto.setIsUpper(1);
        // 设置翻页
        dto.setLimit(limit);
        dto.setPage(pageIndex);
        if (orderSort != null) {
            if (orderSort == 2) {
                dto.setSort(1);
                dto.setSortType(1);
            } else if (orderSort == 3) {
                dto.setSort(0);
                dto.setSortType(3);
            } else if (orderSort == 4) {
                dto.setSort(1);
                dto.setSortType(3);
            }
        }
        Long tempId;
        List<AssortDto> tempList;
        StringBuilder stringBuilder;
        if (assortId != null && !"".equals(assortId)) {
            switch (assortId.substring(0, 1)) {
                case "A":
                    // 选一级分类
                    tempId = Long.valueOf(assortId.substring(1));
                    tempList = commonPullDownFeign.getAssortByParentId(tempId);
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
                        dto.setAssortIds(stringBuilder.toString().substring(1));
                    } else {
                        dto.setAssortId(tempId);
                    }
                    break;
                case "B":
                    // 选二级分类
                    tempId = Long.valueOf(assortId.substring(1));
                    tempList = commonPullDownFeign.getAssortAByParentId(tempId);
                    stringBuilder = new StringBuilder();
                    if (tempList.size() != 0) {
                        for (AssortDto tempDto : tempList) {
                            if (tempDto.getChildren() .size() != 0) {
                                for (AssortDto children : tempDto.getChildren()) {
                                    stringBuilder.append("," + children.getAssortId());
                                }
                                dto.setAssortIds(stringBuilder.toString().substring(1));
                            } else {
                                stringBuilder.append("," + tempDto.getAssortId());
                            }
                        }
                        dto.setAssortIds(stringBuilder.toString().substring(1));
                    } else {
                        dto.setAssortIds(assortId.substring(1));
                    }
                    break;
                case "C":
                    // 选三级分类
                    dto.setAssortIds(assortId.substring(1));
                    break;
            }
        }

        // 产品列表
        PageHelp<ProductDto> productList = productFeign.getProductList(dto);
        map.put("productList", productList.getData());

        //  产品数量
        map.put("productCount", productList.getCount());

        return map;
    }


    /**
     * 产品明细页面
     *
     * @param request
     * @param map
     * @param productId
     * @return
     */
    @RequestMapping(value = "toDetail", method = RequestMethod.GET)
    public String introductionHtml(HttpServletRequest request, ModelMap map, Long productId) {
        // 是否登录
        UserDto userInfo = null;
        try {
            userInfo = getUserInfo(request);
            if (userInfo != null && userInfo.getUserId() != null) {
                // 足迹列表
                ProductDto temp = new ProductDto();
                temp.setUserId(userInfo.getUserId());
                List<ProductDto> footPrintList = productFeign.getFootPrintList(temp);
                map.put("leftName", "浏览记录");
                map.put("leftList", footPrintList);
            } else {
                // 推荐列表
                PageHelp<ProductDto> hotProductList = productFeign.getHotProductList(10);
                map.put("leftName", "热门产品");
                map.put("leftList", hotProductList.getData());
            }
        } catch (NumberFormatException e) {
            // 推荐列表
            PageHelp<ProductDto> hotProductList = productFeign.getHotProductList(10);
            map.put("leftName", "热门产品");
            map.put("leftList", hotProductList.getData());
        }
        ProductDto dto = new ProductDto();
        dto.setProductId(productId);
        dto.setUserId(userInfo.getUserId());
        ProductDto productDto = productFeign.getProductDetail(dto);

        // 产品图片
        List<String> resultImage = Arrays.asList(productDto.getProductImage().split(";"));
        List<String> productImages = new ArrayList<>();
        String productVedio=new String();
        for (String temp : resultImage) {
            //对图片格式做判断区分视频
            if(temp.contains("video")){
                productVedio=temp.replace("video","");
            }else{
                productImages.add(temp);
            }

        }
        map.put("images", productImages);
        map.put("video", productVedio);
        // 宝贝详情
        List<String> productDetail = Arrays.asList(productDto.getProductDetail().split(";"));
        List<String> productDetailTemp = new ArrayList<>();
        for (String temp : productDetail) {
            productDetailTemp.add(temp);
        }
        map.put("productDetail", productDetailTemp);

        // 产品参数
        StringBuilder str = new StringBuilder();
        if (productDto.getKv1() != null) {
            str.append(productDto.getKv1() + ";");
        }
        if (productDto.getKv2() != null) {
            str.append(productDto.getKv2() + ";");
        }
        if (productDto.getKv3() != null) {
            str.append(productDto.getKv3() + ";");
        }
        if (productDto.getKv4() != null) {
            str.append(productDto.getKv4() + ";");
        }
        if (productDto.getKv5() != null) {
            str.append(productDto.getKv5() + ";");
        }
        if (StringUtils.isNotBlank(productDto.getProductJson())) {
//            str.append(productDto.getProductJson().substring(1, productDto.getProductJson().length() - 1).replace("\"", ""));
            str.append(productDto.getProductJson() + ";");
        }
        String[] productJson = null;
        productJson = str.toString().split(";");
        map.put("productJson", productJson);

        // 产品信息
        map.put("detail", productDto);

        //获取各种评价的总数量
        AssessSo assessSo = new AssessSo();
        assessSo.setProductId(productId);
        Map<String, Integer> allCount = assessFeign.getCount(assessSo);
        map.put("allCount", allCount);
        //收藏状态
        FavoriteDto favoriteDto = new FavoriteDto();
        favoriteDto.setUserId(userInfo.getUserId());
        favoriteDto.setFavoriteTargetId(productId);
        favoriteDto.setFavoriteTargetType(1);
        boolean show =favoriteFeign.isCollect(favoriteDto);
        if(show==true){
            map.put("isShowNone", 1);
        }else{
            map.put("isShowNone", 0);
        }

        return "product/productDetail";
    }

    /**
     * 产品详情
     *
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getDetail", method = RequestMethod.GET)
    public ProductDto getDetail(Long productId) {
        ProductDto dto = new ProductDto();
        dto.setProductId(productId);
        return productFeign.getProductDetail(dto);
    }


    /**
     * 通过产品id和产品规格名获取产品规格列表
     *
     * @param reqVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getProductSpecList", method = RequestMethod.POST)
    public List<ProductSpecDto> getProductSpecList(@RequestBody ListSpecReqVo reqVo) {
        ProductSpecDto dto = new ProductSpecDto();
        dto.setProductId(reqVo.getProductId());
        dto.setSpecName(reqVo.getSpecName());
        return productFeign.getProductSpecList(dto);
    }

    /**
     * 立即购买跳转到提交订单页面
     *
     * @param request
     * @param map
     * @param productId
     * @param productNum
     * @param productColor
     * @param productSpec
     * @return
     */
    @MemberAccess
    @RequestMapping(value = "toSubmitOrder", method = RequestMethod.GET)
    public String toSubmitOrder(HttpServletRequest request, ModelMap map, Long productId, Long productNum
            , String productColor, String productSpec) {

        ProductDto so = new ProductDto();
        so.setProductId(productId);
        so.setProductNumber(productNum);
        so.setProductSpec(productSpec.trim());
        ProductDto productDto = productFeign.getProductDetailBySpec_Number(so);

        /*// order
        UserDto userInfo = getUserInfo(request);
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userInfo.getUserId());
        orderDto.setBuyerName(userInfo.getUserName());
        map.put("orderDto", orderDto);

        // orderShop
        OrderShopDto orderShopDto = new OrderShopDto();
        orderShopDto.setUserId(userInfo.getUserId());
        orderShopDto.setShopId(productDto.getShopId());
        orderShopDto.setShopName(productDto.getShopName());
        orderShopDto.setSumPrice(new BigDecimal(productNum).multiply(productDto.getProductSpecDto().getSpecNumber()));
        orderShopDto.setActualPrice(new BigDecimal(productNum).multiply(productDto.getProductSpecDto().getSpecNumber()));
        map.put("orderShopDto", orderShopDto);*/

        // orderProduct
        map.put("productDto", productDto);
        map.put("productColor", productColor);
        map.put("productNumber", productNum);
        return "product/submitOrder";
    }

    //首页获取某类别的产品信息
    @ResponseBody
    @RequestMapping(value = "getAssortProduct", method = RequestMethod.POST)
    public ListProductVo getAssortProduct(@RequestBody ProductManageSo so) {
        PageHelp<ProductDto> pageHelp = productFeign.getAssortProduct(so);
        ListProductVo vo = new ListProductVo();
        vo.setData(pageHelp.getData());
        vo.setCount(pageHelp.getCount());
        //获取类别大图
        BannerDto bannerDto = new BannerDto();
        bannerDto.setTerminal(3000);
        bannerDto.setTarget_type(3);
        bannerDto.setTarget_id(so.getAssortId());
        List<BannerDto> assortpictures = bannerFeign.getAssortPicture(bannerDto);
        vo.setAssortpictures(assortpictures);
        return vo;
    }

    /**
     * 商品是否被收藏
     *
     * @param request
     * @param productId
     * @param type
     * @return
     */
    @ResponseBody
    @MemberAccess
    @RequestMapping(value = "isCollection", method = RequestMethod.GET)
    public Boolean isCollection(HttpServletRequest request, Long productId, Integer type,String productName) {
        UserDto userDto = getUserInfo(request);
        FavoriteDto favoriteDto = new FavoriteDto();
        favoriteDto.setUserId(userDto.getUserId());
        favoriteDto.setFavoriteTargetId(productId);
        favoriteDto.setFavoriteTargetType(type);
        if(!favoriteFeign.isCollect(favoriteDto)){
            favoriteDto.setFavoriteTargetName(productName);
            favoriteFeign.addFavorite(favoriteDto);
            return true;
        }  else{
            favoriteFeign.delFavorite(favoriteDto);
            return false;
        }

    }

}
