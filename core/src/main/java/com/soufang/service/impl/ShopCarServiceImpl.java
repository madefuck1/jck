package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.dto.product.ProductColorDto;
import com.soufang.base.dto.shopCar.ShopCarDto;
import com.soufang.base.dto.shopCar.ShopCarProductDto;
import com.soufang.base.page.PageHelp;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.ProductColorMapper;
import com.soufang.mapper.ShopCarMapper;
import com.soufang.mapper.ShopCarProductMapper;
import com.soufang.model.ShopCar;
import com.soufang.model.ShopCarProduct;
import com.soufang.service.ShopCarService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCarServiceImpl implements ShopCarService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ShopCarMapper shopCarMapper;
    @Autowired
    ShopCarProductMapper shopCarProductMapper;
    @Autowired
    ProductColorMapper productColorMapper;

    @Override
    public PageHelp<ShopCarDto> getShopCarList(Long userId) {
        PageHelp<ShopCarDto> pageHelp = new PageHelp<>();
        // 判断购物车数据是否为空
        int count = shopCarMapper.getShopCarListCount(userId);
        pageHelp.setCount(count);
        List<ShopCarDto> list = new ArrayList<>();
        if (count > 0) {
            // 不为空 查询购物车数据
            list = shopCarMapper.getShopCarList(userId);
            for (ShopCarDto temp : list) {
                for (ShopCarProductDto temp2 : temp.getShopCarProductDtoList()) {
                    ProductColorDto productColorDto = new ProductColorDto();
                    productColorDto.setProductId(temp2.getProductId());
                    productColorDto.setProductColor(temp2.getProductColor());
                    productColorDto = productColorMapper.getDetail(temp2);
                    temp2.setProductColorDto(productColorDto);
                }
            }
        }
        pageHelp.setData(list);
        return pageHelp;
    }

    @Override
    @Transactional
    public void addToShopCar(ShopCarDto dto) {
        try {
            // 判断是否在购物车中存在
            ShopCar shopCar = shopCarMapper.isExistByShop(dto);
            ShopCarProduct shopCarProduct;
            // 不存在，新增
            if (shopCar == null) {
                shopCar = new ShopCar();
                shopCar.setShopId(dto.getShopId());
                shopCar.setUserId(dto.getUserId());
                shopCar.setCreateTime(DateUtils.getToday());
                shopCarMapper.insert(shopCar);
                // 添加购物产品信息
                ShopCarProductDto shopCarProductDto = dto.getShopCarProductDto();
                shopCarProduct = new ShopCarProduct();
                // 从ShopCarProductDto拷贝信息到ShopCarProduct
                BeanUtils.copyProperties(shopCarProductDto, shopCarProduct);
                shopCarProduct.setShopCarId(shopCar.getShopCarId());
                shopCarProductMapper.insert(shopCarProduct);
            } else {
                // 存在，再判断添加的产品是否已经存在
                ShopCarProductDto shopCarProductDto = dto.getShopCarProductDto();
                shopCarProductDto.setShopCarId(shopCar.getShopCarId());
                shopCarProduct = shopCarProductMapper.isExistProduct(shopCarProductDto);
                // 不存在
                if (shopCarProduct == null) {
                    shopCarProduct = new ShopCarProduct();
                    // 从ShopCarProductDto拷贝信息到ShopCarProduct
                    BeanUtils.copyProperties(shopCarProductDto, shopCarProduct);
                    shopCarProductMapper.insert(shopCarProduct);
                } else {
                    // 存在 更新数量
                    shopCarProduct.setProductNumber(shopCarProduct.getProductNumber().add(shopCarProductDto.getProductNumber()));
                    shopCarProductMapper.updateByPrimaryKeySelective(shopCarProduct);
                }
            }
        } catch (Exception e) {
            logger.info("添加购物车失败：" + e.getMessage());
            throw new BusinessException("添加购物车失败");
        }
    }

    @Override
    @Transactional
    public void delShopCar(ShopCarDto dto) {
        try {
            for (Long temp : dto.getShopCarProductIds()) {
                shopCarProductMapper.deleteByPrimaryKey(temp);
                // 查询 购物车产品为空 的店铺,有就删除
                List<ShopCarDto> shopCarDtos = shopCarMapper.selectShopCarWithProductIsNotExist(dto.getUserId());
                if (shopCarDtos.size() > 0) {
                    // 存在就逐个删除
                    for (ShopCarDto temp2 : shopCarDtos) {
                        shopCarMapper.deleteByPrimaryKey(temp2.getShopCarId());
                    }
                }
            }
        } catch (Exception e) {
            logger.info("删除失败：" + e.getMessage());
            throw new BusinessException("删除失败");
        }
    }

    @Override
    public void editShopCar(ShopCarProductDto dto) {
        try {
            // 查询修改后的规格颜色是否已经存在
            ShopCarProduct shopCarProduct = shopCarProductMapper.isExistProduct(dto);
            if (shopCarProduct == null) {
                // 不存在 更新购物车产品信息
                // 从ShopCarProductDto 拷贝信息到ShopCarProduct
                shopCarProduct = new ShopCarProduct();
                BeanUtils.copyProperties(dto, shopCarProduct);
                shopCarProductMapper.updateByPrimaryKeySelective(shopCarProduct);
            } else {
                //  存在 删除当前修改的购物车产品信息
                shopCarProduct.setProductNumber(dto.getProductNumber());
                shopCarProductMapper.updateByPrimaryKeySelective(shopCarProduct);
            }
        } catch (Exception e) {
            logger.info("编辑失败：" + e.getMessage());
            throw new BusinessException("编辑失败");
        }
    }

    private ShopCarProduct tr(ShopCarProductDto dto) {
        ShopCarProduct product = new ShopCarProduct();
        if (dto.getShopCarProductId() != null) {
            product.setShopCarProductId(dto.getShopCarProductId());
        }
        if (dto.getShopCarId() != null) {
            product.setShopCarId(dto.getShopCarId());
        }
        if (dto.getProductId() != null) {
            product.setProductId(dto.getProductId());
        }
        if (dto.getProductNumber() != null) {
            product.setProductNumber(dto.getProductNumber());
        }
        if (StringUtils.isNotBlank(dto.getProductUnit())) {
            product.setProductUnit(dto.getProductUnit());
        }
        if (StringUtils.isNotBlank(dto.getProductSpec())) {
            product.setProductSpec(dto.getProductSpec());
        }
        if (StringUtils.isNotBlank(dto.getProductColor())) {
            product.setProductColor(dto.getProductColor());
        }
        return product;
    }

    @Override
    public int getShopCarCountByUserId(Long userId) {
        int count = shopCarMapper.getShopCarCountByUserId(userId);
        return count;
    }

    @Override
    public ShopCarDto getShopCarDtoById(Long shopCarId) {
        ShopCarDto shopCarDto = shopCarMapper.getShopCarDto(shopCarId);
        return shopCarDto;
    }

    @Override
    public ShopCarProductDto getShopCarProductDtoById(Long shopCarProductId) {
        ShopCarProduct shopCarProduct = shopCarProductMapper.selectByPrimaryKey(shopCarProductId);
        ShopCarProductDto shopCarProductDto = new ShopCarProductDto();
        BeanUtils.copyProperties(shopCarProduct, shopCarProductDto);
        return shopCarProductDto;
    }

    @Override
    public boolean delShopCarAfterOrderGenerate(List<ShopCarDto> dtoList) {
        Long userId = 0l;
        for (ShopCarDto tempDto : dtoList) {
            userId = tempDto.getUserId();
            for (ShopCarProductDto tempPDto : tempDto.getShopCarProductDtoList()) {
                shopCarProductMapper.deleteByPrimaryKey(tempPDto.getShopCarProductId());
            }
        }
        // 查询 购物车产品为空 的店铺,有就删除
        List<ShopCarDto> shopCars = shopCarMapper.selectShopCarWithProductIsNotExist(userId);
        if (shopCars.size() > 0) {
            for (ShopCarDto o : shopCars) {
                shopCarMapper.deleteByPrimaryKey(o.getShopCarId());
            }
        }
        return true;
    }
}
