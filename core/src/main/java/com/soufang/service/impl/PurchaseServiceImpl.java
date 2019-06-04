package com.soufang.service.impl;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.search.purchase.PurchaseSo;
import com.soufang.mapper.EnquiryMapper;
import com.soufang.mapper.EnquiryProductMapper;
import com.soufang.mapper.PurchaseMapper;
import com.soufang.mapper.ShopMapper;
import com.soufang.model.Enquiry;
import com.soufang.model.EnquiryProduct;
import com.soufang.model.Purchase;
import com.soufang.model.Shop;
import com.soufang.service.PurchaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service(value = "purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseMapper purchaseMapper;

    @Autowired
    EnquiryMapper enquiryMapper;

    @Autowired
    EnquiryProductMapper enquiryProductMapper;

    @Autowired
    ShopMapper shopMapper;

    @Override
    public List<PurchaseDto> getByProId(Long id) {
        List<Purchase> list = purchaseMapper.getByProId(id);
        List<PurchaseDto> listDto = new ArrayList<>();
        for (Purchase purchase : list) {
            PurchaseDto purchaseDto = new PurchaseDto();
            BeanUtils.copyProperties(purchase, purchaseDto);
            listDto.add(purchaseDto);
        }
        return listDto;
    }

    @Override
    public List<EnquiryDto> getMyPurchaseList(@RequestBody PurchaseSo purchaseSo) {
        purchaseSo.setPage((purchaseSo.getPage() - 1) * 5);
        List<Enquiry> list = enquiryMapper.getMyPurchaseList(purchaseSo);
        List<EnquiryDto> listDto = new ArrayList<>();
        //询盘
        for (int i = 0; i < list.size(); i++) {
            Enquiry enquiry = list.get(i);
            EnquiryDto enquiryDto = new EnquiryDto();
            //状态-获取对应枚举
            BeanUtils.copyProperties(enquiry, enquiryDto);
            List<EnquiryProductDto> enquiryProductDtos = new ArrayList<>();
            for (EnquiryProduct enquiryProduct : enquiry.getEnquiryProducts()) {
                EnquiryProductDto enquiryProductDto = new EnquiryProductDto();
                BeanUtils.copyProperties(enquiryProduct, enquiryProductDto);
                List<PurchaseDto> purchaseDtos = new ArrayList<>();
                for (Purchase purchase : enquiryProduct.getPurchases()) {
                    PurchaseDto purchaseDto = new PurchaseDto();
                    BeanUtils.copyProperties(purchase, purchaseDto);
                    purchaseDtos.add(purchaseDto);
                }
                enquiryProductDtos.add(enquiryProductDto);
            }
            enquiryDto.setEnquiryProductDto(enquiryProductDtos);
            listDto.add(enquiryDto);
        }
        return listDto;
    }

    @Override
    public int getCount(PurchaseSo purchaseSo) {
        return purchaseMapper.getCount(purchaseSo);
    }

    /**
     * 新增报价单
     *
     * @param purchaseDto
     * @return
     */
    @Override
    @Transactional
    public int addPurchase(List<PurchaseDto> purchaseDto) {
        for (PurchaseDto dto : purchaseDto) {
            Purchase purchase = new Purchase();
            BeanUtils.copyProperties(dto, purchase);
            purchaseMapper.insertSelective(purchase);
        }
        return purchaseDto.size();
    }


    //查询报价信息
    public List<PurchaseDto> getPurchaseListByEnqunum(String enquiryNumber) {
        List<PurchaseDto> purchaseDtos = new ArrayList<>();
        //查询结果
        List<Purchase> purchases = purchaseMapper.getPurchaseListByEnqunum(enquiryNumber);

        for (int i = 0; i < purchases.size(); i++) {
            Purchase purchase = purchases.get(i);
            PurchaseDto purchaseDto = new PurchaseDto();
            //转移报价信息
            BeanUtils.copyProperties(purchase, purchaseDto);

            //转移商铺信息
            ShopDto shopDto = new ShopDto();
            Shop shop = purchase.getShop();
            BeanUtils.copyProperties(shop, shopDto);
            purchaseDto.setShopDto(shopDto);

            purchaseDtos.add(purchaseDto);

        }

        return purchaseDtos;
    }

    @Override
    public Result updateUnitPrice(PurchaseDto purchaseDto) {
        Result result = new Result();
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseDto, purchase);
        int i = purchaseMapper.updateByPrimaryKeySelective(purchase);
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("修改价格成功");
        } else {
            result.setSuccess(false);
            result.setMessage("修改价格失败");
        }
        return result;
    }

    //报价-采用拒绝
    public Result isUseRefused(PurchaseDto purchaseDto) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(purchaseDto, purchase);
        Result result = new Result();
        int i = purchaseMapper.isUseRefused(purchase);
        if (i > 0) {
            result.setSuccess(true);
            result.setMessage("操作成功");
        } else {
            result.setSuccess(false);
            result.setMessage("操作失败");
        }
        return  result;
    }

    public int purchase(PurchaseDto purchaseDto){
        Result result = new Result();
        //查询SHOP信息通过用户ID
        Shop shop =shopMapper.getByUserId(purchaseDto.getUserId());
        //加入SHOPID
        purchaseDto.setShopId(shop.getShopId());
        //查询产品ID根据询盘编号
        List<EnquiryProduct> enquiryProducts =enquiryProductMapper.getByEnquiryNumber(purchaseDto.getEnquiryNumber().toString());
        purchaseDto.setEnquiryNumber(enquiryProducts.get(0).getEnquiryProductId());
        Purchase purchase=new Purchase();
        BeanUtils.copyProperties(purchase,purchaseDto);
       return purchaseMapper.purchase(purchase);

    }

}
