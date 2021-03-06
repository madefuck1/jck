package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.PageBase;
import com.soufang.base.PropertiesParam;
import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.product.ProductColorDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.product.ProductSpecDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.dto.storeConstruction.StoreProductAssortDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.product.ProductManageSo;
import com.soufang.mapper.*;
import com.soufang.model.*;
import com.soufang.service.ProductManageService;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductManageServiceImpl implements ProductManageService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    AssortMapper assortMapper;
    @Autowired
    ProductColorMapper productColorMapper;
    @Autowired
    ProductSpecMapper productSpecMapper;
    @Autowired
    ProductStatisticsMapper productStatisticsMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public PageHelp<ProductDto> list(ProductDto dto) {

        // 设置页面其实数量
        int page = dto.getPage();
        if (page != 0) {
            page = page * dto.getLimit();
        }
        dto.setPage(page);
        if (StringUtils.isBlank(dto.getProductName())) {
            dto.setProductName(null);
        }
        List<ProductDto> products = productMapper.getList(dto);
        List<ProductDto> productDtos = new ArrayList<>();
        for (ProductDto productDto : products) {
            StringBuffer productImage = new StringBuffer();
            String productUrl = "";
            if(StringUtils.isNotBlank(productDto.getProductImage())){
                String[] imageArray = productDto.getProductImage().split(";");
                for (int i = 0; i <imageArray.length ; i++) {
                    if(i == 0){
                        productUrl = PropertiesParam.file_pre + imageArray[i] ;
                    }
                    productImage.append(PropertiesParam.file_pre).append(imageArray[i]).append(";");
                }
            }else {
                productUrl = PropertiesParam.file_pre+"/uploadProduct/product.jpg";
                productImage.append(PropertiesParam.file_pre+"/uploadProduct/product.jpg");
            }
            productDto.setProductUrl(productUrl);
            productDto.setProductImage(productImage.toString());

            StringBuffer productDetail = new StringBuffer();
            if(StringUtils.isNotBlank(productDto.getProductDetail())){
                String[] imageArray = productDto.getProductDetail().split(";");
                for (int i = 0; i <imageArray.length ; i++) {
                    productDetail.append(PropertiesParam.file_pre).append(imageArray[i]).append(";");
                }
            }else {
                productDetail.append(PropertiesParam.file_pre+"/uploadProduct/product.jpg");
            }
            productDto.setProductDetail(productDetail.toString());

            productDtos.add(productDto);
        }
        PageHelp<ProductDto> pageHelp = new PageHelp<>();
        pageHelp.setData(productDtos);
        pageHelp.setCount(productMapper.getCount(dto));
        return pageHelp;
    }

    @Override
    public List<ProductDto> allProduct() {
        List<ProductDto> products = productMapper.getAllProduct();
        return products;
    }

    //适配find_in_set
    private String getAssortList(Long assortId) {
        StringBuffer idsString = new StringBuffer();
        List<Assort> list = assortMapper.getAll();
        List<Long> ids = new ArrayList<>();
        getAssortList(assortId, list, ids);
        for (Long id : ids) {
            idsString.append(id).append(",");
        }
        idsString.append(assortId);
        return idsString.toString();
    }

    private void getAssortList(Long assortId, List<Assort> list, List<Long> ids) {
        boolean hasChild = false;
        List<Long> idList = new ArrayList<>();
        for (Assort assort : list) {
            if (assortId == assort.getParentId()) {
                ids.add(assort.getAssortId());
                idList.add(assort.getAssortId());
                hasChild = true;
            }
        }
        for (Long id : idList) {
            if (hasChild) {
                getAssortList(id, list, ids);
            }
        }
    }

    @Override
    public ProductDto getDetail(ProductDto dto) {
        ProductDto productDto =  productMapper.getDetail(dto);
        StringBuffer productImage = new StringBuffer();
        StringBuffer productDetail = new StringBuffer();
        if(StringUtils.isNotBlank(productDto.getProductDetail())){
            String[] imageArray = productDto.getProductDetail().split(";");
            for (int i = 0; i <imageArray.length ; i++) {
                productDetail.append(PropertiesParam.file_pre).append(imageArray[i]).append(";");
            }
        }else {
            productDetail.append(PropertiesParam.file_pre+"/uploadProduct/product.jpg");
        }
        String productUrl = "";
        if(StringUtils.isNotBlank(productDto.getProductImage())){
            String[] imageArray = productDto.getProductImage().split(";");
            for (int i = 0; i <imageArray.length ; i++) {
                if(i == 0){
                    productUrl = PropertiesParam.file_pre + imageArray[i] ;
                }
                productImage.append(PropertiesParam.file_pre).append(imageArray[i]).append(";");
            }
        }else {
            productUrl = PropertiesParam.file_pre+"/uploadProduct/product.jpg";
            productImage.append(PropertiesParam.file_pre+"/uploadProduct/product.jpg");
        }
        productDto.setProductUrl(productUrl);
        productDto.setProductImage(productImage.toString());
        productDto.setProductDetail(productDetail.toString());
        return productDto;
    }


    @Override
    public boolean updateProduct(ProductDto dto) {
        Product product = new Product();
        // 从dto拷贝信息到product
        BeanUtils.copyProperties(dto, product);
        int i = productMapper.updateByPrimaryKeySelective(product);
        if (dto.getProductColorDtoList() != null) {
            for (ProductColorDto productColorDto : dto.getProductColorDtoList()) {
                ProductColor productColor = new ProductColor();
                BeanUtils.copyProperties(productColorDto, productColor);
                productColorMapper.updateByPrimaryKeySelective(productColor);
            }
        }
        if (dto.getProductSpecDtoList() != null) {
            for (ProductSpecDto productSpecDto : dto.getProductSpecDtoList()) {
                ProductSpec productSpec = new ProductSpec();
                BeanUtils.copyProperties(productSpecDto, productSpec);
                productSpecMapper.updateByPrimaryKeySelective(productSpec);
            }
        }
        return i > 0 ? true : false;
    }

    @Override
    public boolean updateProductColor(ProductColorDto dto) {
        ProductColor productColor = new ProductColor();
        // 从dto拷贝信息到 productColor
        BeanUtils.copyProperties(dto, productColor);
        int i = productColorMapper.updateByPrimaryKeySelective(productColor);
        return i > 0 ? true : false;
    }

    @Override
    public boolean updateProductSpec(ProductSpecDto dto) {
        ProductSpec productSpec = new ProductSpec();
        // 从dto拷贝信息到 productSpec
        BeanUtils.copyProperties(dto, productSpec);
        int i = productSpecMapper.updateByPrimaryKeySelective(productSpec);
        return i > 0 ? true : false;
    }

    @Override
    public boolean updateProductStatistics(Long productId, Integer type) {
        ProductStatistics productStatistics = productStatisticsMapper.selectByProductId(productId);
        if (productStatistics == null) {
            productStatistics = new ProductStatistics();
            productStatistics.setProductId(productId);
            productStatisticsMapper.insert(productStatistics);
        }
        // type{1：收藏；2：交易；3：浏览 }
        switch (type) {
            case 1:
                productStatistics.setCollectionNumber(productStatistics.getCollectionNumber() + 1);
                break;
            case 2:
                productStatistics.setDealNumber(productStatistics.getDealNumber() + 1);
                break;
            case 3:
                productStatistics.setBrowseNumber(productStatistics.getBrowseNumber() + 1);
                break;
        }

        int i = productStatisticsMapper.updateByPrimaryKeySelective(productStatistics);
        return i > 0 ? true : false;
    }

    @Override
    @Transactional
    public void registerProduct(ProductDto dto) {
        // 添加产品信息
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        productMapper.insert(product); // todo 错误捕捉

        // 添加产品颜色 入参 List<ProductColor>d
        List<ProductColorDto> productColorDtoList = dto.getProductColorDtoList();
        ProductColor productColor;
        if (productColorDtoList.size() != 0) {
            for (ProductColorDto temp : productColorDtoList) {
                productColor = new ProductColor();
                BeanUtils.copyProperties(temp, productColor);
                productColor.setProductId(product.getProductId());
                productColorMapper.insert(productColor); // todo 错误捕捉
            }
        }

        // 添加产品规格 入参 List<ProductSpec>
        List<ProductSpecDto> productSpecDtoList = dto.getProductSpecDtoList();
        ProductSpec productSpec;
        if (productSpecDtoList.size() != 0) {
            for (ProductSpecDto temp : productSpecDtoList) {
                productSpec = new ProductSpec();
                BeanUtils.copyProperties(temp, productSpec);
                productSpecMapper.insert(productSpec);
            }
        }

    }

    @Override
    public PageHelp<ProductDto> hotList(Integer limit) {
        List<ProductDto> products = productMapper.getHotList(limit);
        for (ProductDto p : products) {
            String[] img = new String[10];
            if (StringUtils.isNotBlank(p.getProductImage())) {
                img = p.getProductImage().split(";");
            }
            if(StringUtils.isNotBlank(img[0])){
                p.setProductUrl(PropertiesParam.file_pre + img[0]);
            }else {
                p.setProductUrl(PropertiesParam.file_pre + "/uploadProduct/product.jpg");
            }
        }
        PageHelp<ProductDto> pageHelp = new PageHelp<>();
        pageHelp.setData(products);
        return pageHelp;
    }
    @Override
    public PageHelp<ProductDto> hotListPage(PageBase page) {
        if(page.getPage() !=0){
            page.setPage((page.getPage()-1)*page.getLimit());
        }
        List<ProductDto> products = productMapper.getHotListPage(page);
        for (ProductDto p : products) {
            String[] img = new String[10];
            if (StringUtils.isNotBlank(p.getProductImage())) {
                img = p.getProductImage().split(";");
            }
            if(StringUtils.isNotBlank(img[0])){
                p.setProductUrl(PropertiesParam.file_pre + img[0]);
            }else {
                p.setProductUrl(PropertiesParam.file_pre + "/uploadProduct/product.jpg");
            }
        }
        PageHelp<ProductDto> pageHelp = new PageHelp<>();
        pageHelp.setData(products);
        return pageHelp;
    }

    @Override
    public List<ProductDto> getFootPrintList(ProductDto dto) {
        List<ProductDto> list = productMapper.getFootPrintList(dto);
        for (ProductDto p : list) {
            String[] img = new String[10];
            if (StringUtils.isNotBlank(p.getProductImage())) {
                img = p.getProductImage().split(";");
            }
            if(StringUtils.isNotBlank(img[0])){
                p.setProductUrl(PropertiesParam.file_pre + img[0]);
            }else {
                p.setProductUrl(PropertiesParam.file_pre + "/uploadProduct/product.jpg");
            }
        }
        return list ;
    }


    @Override
    public List<ProductSpecDto> getProductSpecList(ProductSpecDto dto) {
        return productSpecMapper.getProductSpecList(dto);
    }

    @Override
    public BigDecimal selectPrice(ShopCarProductDto shopCarProductDto) {
        ShopCarProduct shopCarProduct = new ShopCarProduct();
        BeanUtils.copyProperties(shopCarProductDto, shopCarProduct);
        return productSpecMapper.selectPrice(shopCarProduct);
    }

    @Override
    public ProductDto getProductDetailBySpec_Number(ProductDto dto) {
        ProductDto productDto = productMapper.getProductDetailBySpec_Number(dto);
        String[] img = new String[10];
        if (StringUtils.isNotBlank(productDto.getProductImage())) {
            img = productDto.getProductImage().split(";");
        }
        if(StringUtils.isNotBlank(img[0])){
            productDto.setProductUrl(PropertiesParam.file_pre + img[0]);
        }else {
            productDto.setProductUrl(PropertiesParam.file_pre + "/uploadProduct/product.jpg");
        }
        return productDto;
    }

    @Override
    public PageHelp<ProductDto> getShopProductList(ProductManageSo productSo) {
        // 设置页面其实数量
        int page = productSo.getPage();
        page = (page - 1) * productSo.getLimit();
        productSo.setPage(page);
        List<ProductDto> products = productMapper.getShopProductList(productSo);
        for (ProductDto p:products) {
            if(StringUtils.isNotBlank(p.getProductImage())) {
                String[] img = p.getProductImage().split(";");
                if (StringUtils.isNotBlank(p.getProductImage())) {
                    p.setProductImage(PropertiesParam.file_pre + img[0]);
                }
            }else if(StringUtils.isNotBlank(p.getProductDetail())){
                String[] img = p.getProductDetail().split(";");
                if (StringUtils.isNotBlank(p.getProductDetail())) {
                    p.setProductImage(PropertiesParam.file_pre + img[0]);
                }
            }
        }
        PageHelp<ProductDto> pageHelp = new PageHelp<>();
        pageHelp.setData(products);
        int count = productMapper.getShopProductCount(productSo);
        pageHelp.setCount(count);
        return pageHelp;
    }

    @Override
    public Result getDown(String[] ids) {
        Result result = new Result();
        List<Integer> productIds = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            productIds.add(Integer.valueOf(ids[i]));
        }
        int count = productMapper.getDown(productIds);
        return judge(count);
    }

    @Override
    public Result putUp(String[] ids) {

        List<Integer> productIds = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            productIds.add(Integer.valueOf(ids[i]));
        }
        int count = productMapper.putUp(productIds);
        return judge(count);
    }

    @Override
    public Result deleteProduct(String[] ids) {
        List<Integer> productIds = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            productIds.add(Integer.valueOf(ids[i]));
        }
        int count = productMapper.deleteProduct(productIds);
        return judge(count);
    }

    private Result judge(int count) {
        Result result = new Result();
        if (count > 0) {
            result.setMessage("修改成功");
            result.setSuccess(true);
        } else {
            result.setMessage("修改失败");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result createProductFirst(ProductDto productDto) {
        Result result = new Result();
        try {
            Product product = new Product();
            BeanUtils.copyProperties(productDto, product);
            productMapper.insert(product);
            BigDecimal price = BigDecimal.ZERO;
            for (ProductSpecDto productSpecDto : productDto.getProductSpecDtoList()) {
                if (price.subtract(productSpecDto.getSpecNumber()).compareTo(BigDecimal.ZERO) < 0) {
                    price = productSpecDto.getSpecNumber();
                }
                ProductSpec productSpec = new ProductSpec();
                BeanUtils.copyProperties(productSpecDto, productSpec);
                productSpec.setProductId(product.getProductId());
                productSpecMapper.insert(productSpec);
            }
            for (ProductColorDto productColorDto : productDto.getProductColorDtoList()) {
                ProductColor productColor = new ProductColor();
                BeanUtils.copyProperties(productColorDto, productColor);
                productColor.setProductId(product.getProductId());
                productColorMapper.insert(productColor);
            }
            ProductStatistics productStatistics = new ProductStatistics();
            productStatistics.setProductId(product.getProductId());
            productStatistics.setBrowseNumber(0L);
            productStatistics.setCollectionNumber(0L);
            productStatistics.setDealNumber(0L);
            productStatistics.setProductStock("999999");
            productStatistics.setProductPrice(price.toString());
            productStatisticsMapper.insert(productStatistics);
            result.setMessage(product.getProductId().toString());
        } catch (Exception e) {
            System.out.println(e);
            logger.info("新增产品第一步报错：", e.getMessage());
            throw new BusinessException("新增产品报错");
        }
        return result;
    }

    @Override
    public PageHelp<ProductDto> getAssortProduct(ProductManageSo so) {
        StringBuffer assortIds = new StringBuffer();
        List<AssortDto> assortDtos = assortMapper.getAssortByParentId(so.getAssortId());

        for (int i = 0; i < assortDtos.size(); i++) {
            for (int j = 0; j < assortDtos.get(i).getChildren().size(); j++) {
                assortIds.append(assortDtos.get(i).getChildren().get(j).getAssortId() + ",");
            }
        }
        so.setPage((so.getPage() - 1) * so.getLimit());
        ProductDto productDto = new ProductDto();
        productDto.setAssortIds(String.valueOf(assortIds));
        productDto.setLimit(so.getLimit());
        productDto.setPage(so.getPage());
        List<ProductDto> products = productMapper.getAssortProduct(productDto);
        for (ProductDto p : products) {
            String[] img = new String[10];
            if (StringUtils.isNotBlank(p.getProductImage())) {
                img = p.getProductImage().split(";");
            }
            if (StringUtils.isNotBlank(p.getProductImage())) {
                p.setProductImage(PropertiesParam.file_pre + img[0]);
            }
        }
        PageHelp<ProductDto> pageHelp = new PageHelp<>();
        pageHelp.setData(products);
        pageHelp.setCount(products.size());
        return pageHelp;
    }

    @Override
    public PageHelp<ProductDto> getIndexFootProduct() {
        PageHelp<ProductDto> pageHelp = new PageHelp<>();
        List<ProductDto> list = productMapper.getIndexFootProduct();
        for (ProductDto p : list) {
            String[] img = new String[10];
            if (StringUtils.isNotBlank(p.getProductImage())) {
                img = p.getProductImage().split(";");
            }
            if (StringUtils.isNotBlank(p.getProductImage())) {
                p.setProductImage(PropertiesParam.file_pre + img[0]);
            }
        }
        pageHelp.setData(list);
        return pageHelp;
    }

    @Override
    public List<ProductDto> getProductTop6(ProductDto dto) {
        List<ProductDto> productDtos = productMapper.getProductTop6(dto);
        for (int i = 0; i < productDtos.size(); i++) {
            if(StringUtils.isNotBlank(productDtos.get(i).getProductImage())){
                String[] imageArray = productDtos.get(i).getProductImage().split(";");
                productDtos.get(i).setUrl(PropertiesParam.file_pre+imageArray[0]);
            }else {
                productDtos.get(i).setUrl(PropertiesParam.file_pre+"/uploadProduct/product.jpg");
            }
        }
        return  productDtos;
    }

    @Override
    public PageHelp<ProductDto> getProductByAssortId(StoreProductAssortDto productAssortDto) {
        PageHelp<ProductDto> pageHelp = new PageHelp<>();
        List<ProductDto> list;
        int count;
        // 判断是否为该店铺下的全部全部商品
        if (productAssortDto.getExclusiveAssortId() != null && productAssortDto.getExclusiveAssortId() != 0) {
            list = productMapper.getProductByAssortId1(productAssortDto);
            count = productMapper.getProductByAssortId1Count(productAssortDto);
        } else {
            list = productMapper.getProductByAssortId2(productAssortDto);
            count = productMapper.getProductByAssortId2Count(productAssortDto);
        }
        for (int i = 0; i < list.size(); i++) {
            if(StringUtils.isNotBlank(list.get(i).getProductImage())){
                String[] imageArray = list.get(i).getProductImage().split(";");
                list.get(i).setUrl(PropertiesParam.file_pre+imageArray[0]);
            }else {
                list.get(i).setUrl(PropertiesParam.file_pre+"/uploadProduct/product.jpg");
            }
        }
        pageHelp.setCount(count);
        pageHelp.setData(list);
        return pageHelp;
    }
}
