package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.company.CompanyDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.dto.shop.ShopStatisticsDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.mapper.CompanyMapper;
import com.soufang.mapper.ProductMapper;
import com.soufang.mapper.ShopMapper;
import com.soufang.mapper.ShopStatisticsMapper;
import com.soufang.model.Company;
import com.soufang.model.Shop;
import com.soufang.service.ShopService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "shopService")
public class ShopServiceImpl implements ShopService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    ShopStatisticsMapper shopStatisticsMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CompanyMapper companyMapper;


    @Override
    public ShopDto getById(Long id) {
        Shop shop = shopMapper.getById(id);
        ShopDto shopDto = new ShopDto();
        BeanUtils.copyProperties(shop, shopDto);
        shopDto.setAvatarUrl(PropertiesParam.file_pre+ shop.getShopAvatarUrl());
        Company company = companyMapper.getByUserId(shop.getUserId());
        CompanyDto companyDto = new CompanyDto();
        BeanUtils.copyProperties(company,companyDto);
        shopDto.setCompanyDto(companyDto);
        return shopDto;
    }

    @Override
    public List<ShopDto> getList(ShopSo shopSo) {
        Shop shop = new Shop();
        shop.setShopName(shopSo.getShopName());
        shop.setMainProducts(shopSo.getMainProducts());
        List<ShopDto> listDto = new ArrayList<>();
        List<Shop> list = shopMapper.getList(shop);
        for (Shop shop1 : list) {
            ShopDto shopDto = new ShopDto();
            BeanUtils.copyProperties(shop1, shopDto);
            listDto.add(shopDto);
        }
        return listDto;
    }

    @Override
    public int getCount(ShopSo shopSo) {
        Shop shop = new Shop();
        shop.setShopName(shopSo.getShopName());
        shop.setMainProducts(shopSo.getMainProducts());
        return shopMapper.getCount(shop);
    }

    @Override
    public int deleteById(Long id) {
        return shopMapper.deleteById(id);
    }

    @Override
    @Transactional
    public int addShop(ShopDto shopDto) {
        try {
            Shop shop = new Shop();
            BeanUtils.copyProperties(shopDto, shop);
            shop.setShopAvatarUrl(shopDto.getAvatarUrl());
            return shopMapper.addShop(shop);
        } catch (Exception e) {
            logger.info("添加店铺失败：" + e.toString());
            throw new BusinessException("添加店铺失败");
        }
    }

    @Override
    public int reviewYes(Long id) {
        shopMapper.reviewYes(id);
        return 1;
    }

    @Override
    public int reviewNo(ShopDto shopDto) {
        Shop shop = new Shop();
        shop.setRefuseReason(shopDto.getRefuseReason());
        shop.setShopId(shopDto.getShopId());
        shopMapper.reviewNo(shop);
        return 1;
    }

    @Override
    public ShopDto getByUserId(Long userId) {
        ShopDto shopDto = shopMapper.getByUserId(userId);
        if(shopDto != null){
            shopDto.setAvatarUrl(PropertiesParam.file_pre+shopDto.getAvatarUrl());
        }else {
            shopDto = new ShopDto();
        }
        return shopDto;
    }

    @Override
    public PageHelp<ShopDto> appGetList(ShopSo shopSo) {
        PageHelp<ShopDto> pageHelp = new PageHelp<>();
        List<ShopDto> list = shopMapper.appGetList(shopSo);
        for (int i = 0; i < list.size() ; i++) {
            list.get(i).setAvatarUrl(PropertiesParam.file_pre+list.get(i).getAvatarUrl());
            for(int j = 0 ; j < list.get(i).getProductDtoList().size() ; j++){
                if(StringUtils.isNotBlank(list.get(i).getProductDtoList().get(j).getProductImage())){
                    String[] imageArray = list.get(i).getProductDtoList().get(j).getProductImage().split(";");
                    list.get(i).getProductDtoList().get(j).setProductUrl(PropertiesParam.file_pre+imageArray[0]);
                }else {
                    list.get(i).getProductDtoList().get(j).setProductUrl(PropertiesParam.file_pre+"/uploadProduct/product.jpg");
                }
            }
        }
        pageHelp.setData(list);
        pageHelp.setCount(shopMapper.appGetCount(shopSo));
        return pageHelp;
    }

    @Override
    public void updateShop(ShopDto shopDto) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopDto,shop);
        shop.setShopAvatarUrl(shopDto.getAvatarUrl());
        shopMapper.updateByPrimaryKeySelective(shop);
    }

    @Override
    public List<ShopDto> getHotShop(){
        List<Shop > shopList = shopMapper.getHotShop();
        List<ShopDto> shopDtos = new ArrayList<>();
        for (Shop shop: shopList) {
            ShopDto shopDto = new ShopDto();
            shopDto.setAvatarUrl(shop.getShopAvatarUrl());
            BeanUtils.copyProperties(shop,shopDto);
            if(StringUtils.isNotBlank(shopDto.getAvatarUrl())){
                shopDto.setAvatarUrl(PropertiesParam.file_pre+shopDto.getAvatarUrl());
            }else {
                shopDto.setAvatarUrl(PropertiesParam.file_pre+"/uploadProduct/product.jpg");
            }
            shopDtos.add(shopDto);
        }
        return shopDtos;
    }

    @Override
    public ShopStatisticsDto getShopStatisticsInfo(Long shopId) {
        return shopStatisticsMapper.getInfoByShopId(shopId);
    }

    @Override
    public List<ProductDto> getShopProductManaList(ProductDto productDto) {
        List<ProductDto> productDtos = productMapper.getShopProductManaList(productDto);
        for (ProductDto p: productDtos) {
            p.setProductDetail(PropertiesParam.file_pre+p.getProductDetail());
            if(StringUtils.isNotBlank(p.getProductImage())){
                String[] list = p.getProductImage().split(";");
                p.setProductUrl(PropertiesParam.file_pre+list[0]);
            }else {
                p.setProductUrl(PropertiesParam.file_pre+"/uploadProduct/product.jpg");
            }
        }
        return productDtos;
    }
}
