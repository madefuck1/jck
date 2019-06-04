package com.soufang.service.impl;

import com.soufang.base.dto.assort.AssortDto;
import com.soufang.base.search.assort.AssortSo;
import com.soufang.mapper.AssortMapper;
import com.soufang.model.Assort;
import com.soufang.service.AssortService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: chen
 * @Date: 2019/4/20
 * @Description:
 */
@Service(value = "assortService")
public class AssortServiceImpl implements AssortService {

    @Autowired
    AssortMapper assortMapper;


    @Override
    public int deleteByPrimaryKey(Long assortId) {
        return assortMapper.deleteByPrimaryKey(assortId);
    }

    @Override
    public int insertSelective(AssortDto assortDto) {
        Assort assort = new Assort();
        assort.setAssortName(assortDto.getAssortName());
        assort.setAssortLevel(assortDto.getAssortLevel());
        assort.setParentId(assortDto.getParentId());
        assort.setSort(assortDto.getSort());
        if (StringUtils.isNotBlank(assortDto.getKey1())) {
            assort.setKey1(assortDto.getKey1());
        }
        if (StringUtils.isNotBlank(assortDto.getKey2())) {
            assort.setKey1(assortDto.getKey2());
        }
        if (StringUtils.isNotBlank(assortDto.getKey3())) {
            assort.setKey1(assortDto.getKey3());
        }
        if (StringUtils.isNotBlank(assortDto.getKey4())) {
            assort.setKey1(assortDto.getKey4());
        }
        if (StringUtils.isNotBlank(assortDto.getKey5())) {
            assort.setKey1(assortDto.getKey5());
        }
        assortMapper.insertSelective(assort);
        return 1;
    }

    @Override
    public AssortDto selectByPrimaryKey(Long assortId) {
        Assort assort = assortMapper.selectByPrimaryKey(assortId);
        AssortDto assortDto = new AssortDto();
        BeanUtils.copyProperties(assort,assortDto);
        return assortDto;
    }

    @Override
    public int updateByPrimaryKeySelective(AssortDto assortDto) {
        Assort assort = new Assort();
        assort.setAssortId(assortDto.getAssortId());
        assort.setAssortName(assortDto.getAssortName());
        assort.setAssortLevel(assortDto.getAssortLevel());
        assort.setParentId(assortDto.getParentId());
        assort.setSort(assortDto.getSort());

        if (StringUtils.isNotBlank(assortDto.getKey1())) {
            assort.setKey1(assortDto.getKey1());
        }
        if (StringUtils.isNotBlank(assortDto.getKey2())) {
            assort.setKey2(assortDto.getKey2());
        }
        if (StringUtils.isNotBlank(assortDto.getKey3())) {
            assort.setKey3(assortDto.getKey3());
        }
        if (StringUtils.isNotBlank(assortDto.getKey4())) {
            assort.setKey4(assortDto.getKey4());
        }
        if (StringUtils.isNotBlank(assortDto.getKey5())) {
            assort.setKey5(assortDto.getKey5());
        }
        return assortMapper.updateByPrimaryKeySelective(assort);
    }

    @Override
    public List<AssortDto> getAll() {
        List<Assort> list = assortMapper.getAll();
        List<AssortDto> assortDtoList = new ArrayList<>();
        for(Assort assort : list){
            AssortDto assortDto = new AssortDto();
            BeanUtils.copyProperties(assort,assortDto);
            assortDtoList.add(assortDto);
        }
        return assortDtoList;
    }

    @Override
    public List<AssortDto> getList(AssortDto assortDto) {
        Assort assort = new Assort();
        BeanUtils.copyProperties(assortDto,assort);
        List<Assort> list = assortMapper.getList(assort);
        List<AssortDto> assortDtoList = new ArrayList<>();
        for(Assort a : list){
            AssortDto dto = new AssortDto();
            BeanUtils.copyProperties(a,dto);
            assortDtoList.add(dto);
        }
        return assortDtoList;
    }

    @Override
    public List<Map<String, Object>> getIdName() {
        return assortMapper.getIdName();
    }

    @Override
    public Map<String,Object> getAssortByKey(AssortSo so) {
        return assortMapper.getAssortByKey(so);
    }
}
