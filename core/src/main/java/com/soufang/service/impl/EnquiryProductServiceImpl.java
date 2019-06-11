package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.enquiry.EnquiryDto;
import com.soufang.base.dto.enquiryProduct.EnquiryProductDto;
import com.soufang.base.page.PageHelp;
import com.soufang.mapper.EnquiryProductMapper;
import com.soufang.model.EnquiryProduct;
import com.soufang.service.EnquiryProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "enquiryProductService")
public class EnquiryProductServiceImpl implements EnquiryProductService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EnquiryProductMapper enquiryProductMapper;

    @Override
    public List<EnquiryProductDto> getByEnquiryNumber(String enquiryNumber) {
        List<EnquiryProduct> list = enquiryProductMapper.getByEnquiryNumber(enquiryNumber);
        List<EnquiryProductDto> listDto = new ArrayList<>();
        for (EnquiryProduct enqp: list) {
            EnquiryProductDto ePD = new EnquiryProductDto();
            BeanUtils.copyProperties(enqp,ePD);
            listDto.add(ePD);
        }
        return listDto;
    }

    @Override
    @Transactional
    public int addEnquiryProduct(List<EnquiryProductDto> listDto) {
        try {
            for (EnquiryProductDto dto : listDto) {
                EnquiryProduct enPro = new EnquiryProduct();
                BeanUtils.copyProperties(dto, enPro);
                enquiryProductMapper.insertSelective(enPro);
            }
            return listDto.size();

        } catch (Exception e) {
            logger.info("添加询盘产品失败："+e.toString());
            throw new BusinessException("添加询盘产品失败");
        }
    }

    /**
     * 修改询盘产品信息-
     * @param listDto
     * @return
     */
    @Override
    @Transactional
    public int updateEnquiryProduct(List<EnquiryProductDto> listDto){
        try {
            for (EnquiryProductDto dto : listDto) {
                EnquiryProduct enPro = new EnquiryProduct();
                BeanUtils.copyProperties(dto, enPro);
                enquiryProductMapper.updateByPrimaryKeySelective(enPro);
            }
            return listDto.size();

        } catch (Exception e) {
            logger.info("修改询盘产品失败："+e.toString());
            throw new BusinessException("修改询盘产品失败");
        }
    }


    //删除照片
    public int delEnProImgUrl(Long enquiryProductId){
       return enquiryProductMapper.delEnProImgUrl(enquiryProductId);
    }

    @Override
    public PageHelp<EnquiryProductDto> getIndexProductList() {
        List<EnquiryProductDto> enquiryProductDtos = enquiryProductMapper.getIndexProductList();
        for (EnquiryProductDto p:enquiryProductDtos) {
            p.setProductImage(PropertiesParam.file_pre+p.getProductImage());
        }
        PageHelp<EnquiryProductDto> pageHelp = new PageHelp<>();
        pageHelp.setData(enquiryProductDtos);
        return pageHelp;
    }


}
