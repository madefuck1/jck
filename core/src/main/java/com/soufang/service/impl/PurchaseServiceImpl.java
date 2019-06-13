package com.soufang.service.impl;

import com.soufang.base.Result;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.enums.PurchaseStatusEnum;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.base.search.purchase.PurchaseSo;
import com.soufang.base.utils.DateUtils;
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

import java.math.BigDecimal;
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

    @Override
    public int purchase(PurchaseDto purchaseDto){
        Result result = new Result();
        //查询SHOP信息通过用户ID
        Shop shop =shopMapper.getByUserId(purchaseDto.getUserId());
        //加入SHOPID
        purchaseDto.setShopId(shop.getShopId());
        //查询产品ID根据询盘编号
        List<EnquiryProduct> enquiryProducts =enquiryProductMapper.getByEnquiryNumber(purchaseDto.getEnquiryNumber());
        if("".equals(purchaseDto.getEnquiryProductId())||purchaseDto.getEnquiryProductId()==null){
            purchaseDto.setEnquiryProductId(enquiryProducts.get(0).getEnquiryProductId());

        }
        //计算总价multiply
        purchaseDto.setSumPrice(purchaseDto.getUnitPrice().multiply(new BigDecimal(enquiryProducts.get(0).getProductNumber())));
        //多个产品
       /* for(int i = 0; i < enquiryProducts.size();i++){
            purchaseDto.setEnquiryNumber(enquiryProducts.get(0).getEnquiryNumber());
        }*/
       //单个产品
        purchaseDto.setOfferTime(DateUtils.getToday());
        purchaseDto.setOfferStatus(PurchaseStatusEnum.already_offer.getValue());
        Purchase purchase=new Purchase();
        BeanUtils.copyProperties(purchaseDto,purchase);
        //对报价做判断-
        int i =purchaseMapper.getCount(purchase);
        if(i>0){
            //已存在报价-存在则更新报价信息
            return purchaseMapper.updateByPrimaryKey(purchase);

        }else{
            //-增加报价信息
            return purchaseMapper.purchase(purchase);
        }
    }

    /**
     * 接收报价
     * @param purchaseSo
     * @return
     */
    @Override
    public int acceptPurchase(PurchaseSo purchaseSo){
        EnquirySo enquirySo= new EnquirySo();
        //接收报价-还需要去更改询盘状态为-已报价
        if(purchaseSo.getOfferStatus()==2){
            enquirySo.setEnquiryStatus(4);
            enquirySo.setEnquiryNumber(purchaseSo.getEnquiryNumber());
            //调用修改询盘状态
            enquiryMapper.delEnquiry(enquirySo);
        }
     return purchaseMapper.acceptPurchase(purchaseSo);
    }

}
