package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.search.shop.ShopSo;
import com.soufang.mapper.ShopMapper;
import com.soufang.model.Shop;
import com.soufang.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "shopService")
public class ShopServiceImpl implements ShopService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ShopMapper shopMapper;

    @Override
    public ShopDto getById(Long id) {
        Shop shop = shopMapper.getById(id);
        ShopDto shopDto = new ShopDto();
        BeanUtils.copyProperties(shop, shopDto);
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
        Shop shop = shopMapper.getByUserId(userId);
        ShopDto shopDto = new ShopDto();
        if (shop == null) {
            return new ShopDto();
        }
        BeanUtils.copyProperties(shop, shopDto);
        return shopDto;
    }

    @Override
    public List<ShopDto> appGetList(ShopSo shopSo) {
        return shopMapper.appGetList(shopSo);
    }

    @Override
    public void updateShop(ShopDto shopDto) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopDto,shop);
        shopMapper.updateByPrimaryKeySelective(shop);
    }
}
