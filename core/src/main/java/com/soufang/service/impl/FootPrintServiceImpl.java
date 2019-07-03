package com.soufang.service.impl;

import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.footprint.FootPrintDto;
import com.soufang.base.dto.product.ProductDto;
import com.soufang.base.dto.product.ProductStatisticsDto;
import com.soufang.base.dto.shop.ShopDto;
import com.soufang.base.search.footPrint.FootPrintSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.FootPrintMapper;
import com.soufang.model.FootPrint;
import com.soufang.service.FootPrintService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FootPrintServiceImpl implements FootPrintService {

    @Autowired
    FootPrintMapper footPrintMapper;

    /**
     * 新增数据
     * @param dto
     * @return
     */
    @Override
    public boolean insert(FootPrintDto dto) {
        // 封装参数
        FootPrint footPrint = new FootPrint();
        BeanUtils.copyProperties(dto, footPrint);
        footPrint.setCreateTime(DateUtils.getToday());
        int insert = footPrintMapper.insert(footPrint);
        return insert>0? true:false;
    }

    /**
     * 查询一周的足迹数据
     * @param footPrintSo
     * @return
     */
    @Override
    public  List<FootPrintDto>  selFootPrintOneWeek( FootPrintSo footPrintSo){
        // 封装参数
        footPrintSo.setPage((footPrintSo.getPage()-1)*5);
        //取得所有数据
        List<FootPrint> footPrints = footPrintMapper.selFootPrintOneWeek(footPrintSo);
        List<FootPrintDto> footPrintDtos = new ArrayList<>();
        for (FootPrint fp : footPrints){
            FootPrintDto fPDto1 = new FootPrintDto();
            BeanUtils.copyProperties(fp,fPDto1);
            //商品信息
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(fp.getProduct(),productDto);
            //更改图片地址
            productDto.setProductImage(PropertiesParam.file_pre+productDto.getProductImage());
            productDto.setUrl(productDto.getProductImage().split(";")[0]);
            //商店信息
            ShopDto shopDto =new ShopDto();
            BeanUtils.copyProperties(fp.getShop(),shopDto);
            //价格-库存-交易浏览量
            ProductStatisticsDto productStatisticsDto  = new ProductStatisticsDto();
            BeanUtils.copyProperties(fp.getProductStatistics(),productStatisticsDto);
            //存入足迹信息中
            fPDto1.setProductDto(productDto);
            fPDto1.setShopDto(shopDto);
            fPDto1.setProductStatisticsDto(productStatisticsDto);
            footPrintDtos.add(fPDto1);
        }
        return  footPrintDtos;
    }


    /**
     * 查询所有数据
     * @param footPrintSo
     * @return
     */
    @Override
    public List<FootPrintDto> getFootPrintList( FootPrintSo footPrintSo){
        // 封装参数
        footPrintSo.setPage((footPrintSo.getPage()-1)*5);
        //取得所有数据
        List<FootPrint> footPrints = footPrintMapper.getFootPrintList(footPrintSo);
        List<FootPrintDto> footPrintDtos = new ArrayList<>();
        for (FootPrint fp : footPrints){
            FootPrintDto fPDto1 = new FootPrintDto();
            BeanUtils.copyProperties(fp,fPDto1);
            //商品信息
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(fp.getProduct(),productDto);
            // 更改图片地址
            productDto.setProductImage(PropertiesParam.file_pre+productDto.getProductImage());
            productDto.setUrl(productDto.getProductImage().split(";")[0]);
            //商店信息
            ShopDto shopDto =new ShopDto();
            BeanUtils.copyProperties(fp.getShop(),shopDto);
            //价格-库存-交易浏览量
            ProductStatisticsDto productStatisticsDto  = new ProductStatisticsDto();
            BeanUtils.copyProperties(fp.getProductStatistics(),productStatisticsDto);
            //存入足迹信息中
            fPDto1.setProductDto(productDto);
            fPDto1.setShopDto(shopDto);
            fPDto1.setProductStatisticsDto(productStatisticsDto);
            footPrintDtos.add(fPDto1);
        }
        return  footPrintDtos;
}

    /**
     *获取总数量
     * @param footPrintSo
     * @return
     */
    @Override
    public int getFoPtCount( FootPrintSo footPrintSo){
       FootPrint footPrint = new FootPrint();
       BeanUtils.copyProperties(footPrintSo, footPrint);
       int count = footPrintMapper.getFoPtCount(footPrint);
       return count;
    }

    /**
     * 删除
     * @param footPrintId
     * @return
     */
    @Override
    public int delFootPrintById(long footPrintId){
      return footPrintMapper.deleteByPrimaryKey(footPrintId);
    }

    /**
     * 根据ID查询详情
     * @param footPringtId
     * @return
     */
    @Override
    public FootPrintDto selFootPrintById(String footPringtId){
        FootPrintDto footPrintDto =new FootPrintDto();
        FootPrint footPrint =footPrintMapper.selectByPrimaryKey(footPringtId);
        BeanUtils.copyProperties(footPrint,footPrintDto);
        return  footPrintDto;
    }
}
