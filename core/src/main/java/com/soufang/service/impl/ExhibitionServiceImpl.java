/**
 * Copyright (C), 2019-2019, XXX有限公司
 * FileName: ExhibitionServiceImpl
 * Author:   小三
 * Date:     2019/6/11 10:01
 * Description: 展示实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.soufang.service.impl;

import com.soufang.base.PropertiesParam;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.dto.exhibition.ExhibitionDto;
import com.soufang.base.search.exhibition.ExhibitionSo;
import com.soufang.mapper.ExhibitionMapper;
import com.soufang.model.Assort;
import com.soufang.model.Exhibition;
import com.soufang.service.ExhibitionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈展示实现类〉
 *
 * @author 小三
 * @create 2019/6/11
 * @since 1.0.0
 */
@Service(value  = "exhibitionService")
public class ExhibitionServiceImpl implements ExhibitionService {
    @Autowired
    ExhibitionMapper exhibitionMapper;

    //查询所有
   public  List<ExhibitionDto> selExhibition(ExhibitionSo exhibitionSo){
       if(!(exhibitionSo.getPage()==0||"".equals(exhibitionSo.getPage()))){
           exhibitionSo.setPage((exhibitionSo.getPage() - 1) * 5);
       }
       List<Exhibition> exhibitionList =exhibitionMapper.selExhibition(exhibitionSo);
       List<ExhibitionDto> exhibitionDtoList=new ArrayList<>();
       //转化对象DTO
       for (int i = 0; i < exhibitionList.size(); i++) {
           Exhibition exhibition =exhibitionList.get(i);
           ExhibitionDto exhibitionDto = new ExhibitionDto();
           //分类信息
           Assort assort =exhibition.getAssort();
           AssortDto assortDto =new AssortDto();
           BeanUtils.copyProperties(assort, assortDto);
           exhibitionDto.setAssortDto(assortDto);
           //更改图片地址
           exhibition.setExhibitionPhoto(PropertiesParam.file_pre+exhibition.getExhibitionPhoto());
           //展会信息
           BeanUtils.copyProperties(exhibition, exhibitionDto);
           exhibitionDtoList.add(exhibitionDto);
       }
       return exhibitionDtoList;
    }
}
