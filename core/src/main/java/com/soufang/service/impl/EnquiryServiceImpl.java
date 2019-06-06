package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.dto.purchase.PurchaseDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.enums.EnquiryStatusEnum;
import com.soufang.base.search.enquiry.EnquirySo;
import com.soufang.mapper.EnquiryMapper;
import com.soufang.mapper.EnquiryProductMapper;
import com.soufang.mapper.PurchaseMapper;
import com.soufang.model.*;
import com.soufang.service.EnquiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "enquiryService")
public class EnquiryServiceImpl implements EnquiryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EnquiryMapper enquiryMapper;
    @Autowired
    EnquiryProductMapper enquiryProductMapper;
    @Autowired
    PurchaseMapper purchaseMapper;

    @Override
    public EnquiryDto getByEnqNum(String enquiryNumber) {
        EnquiryDto enquiryDto = new EnquiryDto();
        Enquiry enquiry = enquiryMapper.getByEnqNum(enquiryNumber);
        //状态-获取对应枚举
        enquiry.setStatusMessage(EnquiryStatusEnum.getByKey(enquiry.getEnquiryStatus()).getMessage());
        BeanUtils.copyProperties(enquiry,enquiryDto);
        List<EnquiryProductDto> enquiryProductDtos = new ArrayList<>();
        for(EnquiryProduct enquiryProduct : enquiry.getEnquiryProducts()){
            EnquiryProductDto enquiryProductDto = new EnquiryProductDto();
            //更改图片地址
            enquiryProduct.setProductImage(PropertiesParam.file_pre+enquiryProduct.getProductImage());
            BeanUtils.copyProperties(enquiryProduct, enquiryProductDto);
            List<PurchaseDto> purchaseDtos = new ArrayList<>();
            for (Purchase purchase : enquiryProduct.getPurchases()) {
                PurchaseDto purchaseDto = new PurchaseDto();
                BeanUtils.copyProperties(purchase, purchaseDto);
                purchaseDtos.add(purchaseDto);
            }
            enquiryProductDtos.add(enquiryProductDto);
            enquiryDto.setEnquiryProductDto(enquiryProductDtos);
        }
        return enquiryDto;
    }

    public EnquiryDto selectProductById(String enquiryProductId){
        EnquiryDto enquiryDto = new EnquiryDto();
        Enquiry enquiry = enquiryMapper.selectProductById(enquiryProductId);
        //状态-获取对应枚举
        enquiry.setStatusMessage(EnquiryStatusEnum.getByKey(enquiry.getEnquiryStatus()).getMessage());
        BeanUtils.copyProperties(enquiry,enquiryDto);
        List<EnquiryProductDto> enquiryProductDtos = new ArrayList<>();
        for(EnquiryProduct enquiryProduct : enquiry.getEnquiryProducts()){
            EnquiryProductDto enquiryProductDto = new EnquiryProductDto();
            //更改图片地址
            enquiryProduct.setProductImage(PropertiesParam.file_pre+enquiryProduct.getProductImage());
            BeanUtils.copyProperties(enquiryProduct, enquiryProductDto);
            List<PurchaseDto> purchaseDtos = new ArrayList<>();
            for (Purchase purchase : enquiryProduct.getPurchases()) {
                PurchaseDto purchaseDto = new PurchaseDto();
                BeanUtils.copyProperties(purchase, purchaseDto);
                purchaseDtos.add(purchaseDto);
            }
            enquiryProductDtos.add(enquiryProductDto);
            enquiryDto.setEnquiryProductDto(enquiryProductDtos);
        }
        return enquiryDto;
    }


    @Override
    public List<EnquiryDto> getList(EnquirySo enquirySo) {
        //页面默认加载赋值
        enquirySo.setPage((enquirySo.getPage() - 1) * 5);
        List<Enquiry> list = enquiryMapper.getList(enquirySo);
        List<EnquiryDto> listDto = new ArrayList<>();
        //询盘
        for (int i = 0; i < list.size(); i++) {
            Enquiry enquiry = list.get(i);
            EnquiryDto enquiryDto = new EnquiryDto();
            //状态-获取对应枚举
            enquiry.setStatusMessage(EnquiryStatusEnum.getByKey(enquiry.getEnquiryStatus()).getMessage());
            // 格式化时间
            SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy MM dd" );
            Date date= new Date();
            enquiry.setStrCreateTime(sdf1.format(enquiry.getCreateTime()));
            BeanUtils.copyProperties(enquiry, enquiryDto);
            List<EnquiryProductDto> enquiryProductDtos = new ArrayList<>();
            for (EnquiryProduct enquiryProduct : enquiry.getEnquiryProducts()) {
                EnquiryProductDto enquiryProductDto = new EnquiryProductDto();
                //更改图片地址
                enquiryProduct.setProductImage(PropertiesParam.file_pre+enquiryProduct.getProductImage());
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
        return  listDto;
    }

    /**
     * 查询求购信息
     * @param enquirySo
     * @return
     */
    @Override
    public List<EnquiryDto> enquiryTableMessage(EnquirySo enquirySo){
        enquirySo.setPage((enquirySo.getPage() - 1) * 5);
        List<Enquiry> lists= enquiryMapper.enquiryTableMessage(enquirySo);
        List<EnquiryDto> listDtos = new ArrayList<>();
        //询盘
        for (int i = 0; i < lists.size(); i++) {
            Enquiry enquiry = lists.get(i);
            EnquiryDto enquiryDto = new EnquiryDto();
            //状态-获取对应枚举
            enquiry.setStatusMessage(EnquiryStatusEnum.getByKey(enquiry.getEnquiryStatus()).getMessage());
            // 格式化时间
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy MM dd");
            Date date = new Date();
            enquiry.setStrCreateTime(sdf1.format(enquiry.getCreateTime()));
            BeanUtils.copyProperties(enquiry, enquiryDto);
           //对商铺信息进行判断是否存在
            Shop shop = enquiry.getShop();
            if(shop.getShopId()!=null){
                ShopDto shopDto=new ShopDto();
                BeanUtils.copyProperties(shop,shopDto);
                //加入个人商铺信息
                enquiryDto.setShopDto(shopDto);
            }
            List<EnquiryProductDto> enquiryProductDtos = new ArrayList<>();
            for (EnquiryProduct enquiryProduct : enquiry.getEnquiryProducts()) {
                EnquiryProductDto enquiryProductDto = new EnquiryProductDto();
                BeanUtils.copyProperties(enquiryProduct, enquiryProductDto);
                List<PurchaseDto> purchaseDtos = new ArrayList<>();

                AssortDto assortDto =new AssortDto();
                Assort assort = enquiryProduct.getAssort();
                BeanUtils.copyProperties(assort,assortDto);
                //产品中加上分类信息
                enquiryProductDto.setAssortDtos(assortDto);
                enquiryProductDtos.add(enquiryProductDto);
            }
            //加入产品信息
            enquiryDto.setEnquiryProductDto(enquiryProductDtos);
            //将询盘信息装入
            listDtos.add(enquiryDto);
        }
        return listDtos;
    }

    /**
     * 求购列表总数
     * @param EnquirySo
     * @return
     */
    @Override
    public  int  enquiryTableCount(EnquirySo EnquirySo){
        return enquiryMapper.enquiryTableCount(EnquirySo);
    }

    @Override
    public int getMyQuoteCount(EnquirySo enquirySo) {
        return enquiryMapper.getMyQuoteCount(enquirySo);
    }

    @Override
    public EnquiryDto getQuoteDetails(Long enquiryProductId) {
        Enquiry enquiry = enquiryMapper.getQuoteDetail(enquiryProductId);
        EnquiryDto enqDto = new EnquiryDto();
        BeanUtils.copyProperties(enquiry,enqDto);
        List<EnquiryProductDto> ePDto = new ArrayList<>();
        for (EnquiryProduct product : enquiry.getEnquiryProducts()) {
            product.setProductImage(PropertiesParam.file_pre+product.getProductImage());//修改图片地址
            EnquiryProductDto eDto = new EnquiryProductDto();
            BeanUtils.copyProperties(product,eDto);
            List<PurchaseDto> pDtos = new ArrayList<>();
            for (Purchase purchase: product.getPurchases()) {
                PurchaseDto purchaseDto = new PurchaseDto();
                BeanUtils.copyProperties(purchase,purchaseDto);
                pDtos.add(purchaseDto);
            }
            ePDto.add(eDto);
        }
        enqDto.setEnquiryProductDto(ePDto);
        return enqDto;
    }

    @Override
    public int getCount(EnquirySo enquirySo) {
        int i = enquiryMapper.getCount(enquirySo);
        return i;
    }

    @Override
    public int banQuote(String enquiryNumber) {
        return  enquiryMapper.banQuote(enquiryNumber);
    }

    @Override
    public int regainQuote(String enquiryNumber) {
        return  enquiryMapper.regainQuote(enquiryNumber);
    }

    @Override
    @Transactional
    public int insertSelective(EnquiryDto enquiryDto) {
            Enquiry enquiry = new Enquiry();
            BeanUtils.copyProperties(enquiryDto,enquiry);
            //新增状态为审核中
            enquiry.setEnquiryStatus(EnquiryStatusEnum.to_auditing.getValue());
            enquiry.setEnquiryType(Integer.valueOf(enquiryDto.getStrEnquiryType()));
            //循环插入询盘产品信息
            List<EnquiryProductDto> listDto=enquiryDto.getEnquiryProductDto();
            for (EnquiryProductDto dto : listDto) {
                EnquiryProduct enPro = new EnquiryProduct();
                BeanUtils.copyProperties(dto, enPro);
                enquiryProductMapper.insert(enPro);
            }
            //插入询盘信息
            int i =enquiryMapper.insert(enquiry);
            return i;

    }

    /**
     * 修改询盘信息
     */
    public int updateEnquiry(EnquiryDto enquiryDto){
        try {
            Enquiry enquiry = new Enquiry();
            BeanUtils.copyProperties(enquiryDto,enquiry);
            //修改询盘信息
            int i=enquiryMapper.updateByPrimaryKeySelective(enquiry);
            //循环修改询盘产品信息
            List<EnquiryProductDto> listDto=enquiryDto.getEnquiryProductDto();
            for (EnquiryProductDto dto : listDto) {
                EnquiryProduct enPro = new EnquiryProduct();
                BeanUtils.copyProperties(dto, enPro);
                enquiryProductMapper.updateByPrimaryKeySelective(enPro);
            }
            return i;
        }catch (Exception e){
            logger.info("修改询盘失败："+e.toString());
            throw new BusinessException("修改询盘失败");
        }
    }


    /**
     * 删除询盘-产品-询价
     * @return
     */
    public int delEnquiry(String enquiryNumber){
        //通过enquiryNumber查出所有产品ID集合在所有产品ID找到-所有询价ID集合

        //1.删除询价信息
       int j= purchaseMapper.delPurchaseByEnquiryNumber(enquiryNumber);

        //2.删除产品
        int k= enquiryProductMapper.delByEnquiryNum(enquiryNumber);

        //3.删除询盘
        int i = enquiryMapper.delEnquiry(enquiryNumber);

        return i;
    }


}
