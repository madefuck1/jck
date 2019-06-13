package com.soufang.service.impl;

import com.soufang.base.dto.assort.AssortDto;
import com.soufang.mapper.AssortMapper;
import com.soufang.mapper.ShopMapper;
import com.soufang.model.Assort;
import com.soufang.service.CommonPullDownService;
import org.hibernate.validator.internal.xml.BeanType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommonPullDownServiceImpl implements CommonPullDownService {

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    AssortMapper assortMapper;

    @Override
    public List<Map<String,Object>> getShopIdAndNameOption() {
        List<Map<String,Object>> mapList= shopMapper.getShopIdAndNameOption();
        return mapList;
    }

    @Override
    public List<Map<String, Object>> getAssortIdAndName() {
        List<Map<String, Object>> list = assortMapper.getAssortIdAndName();
        return makeParent(list);
    }

    @Override
    public List<AssortDto> getFirstAssort(Long parentId) {
        Assort assort = new Assort();
        assort.setParentId(parentId);
        List<Assort> list = assortMapper.getList(assort);
        List<AssortDto> assortDtos = new ArrayList<>();
        for(Assort temp : list){
            AssortDto assortDto = new AssortDto();
            BeanUtils.copyProperties(temp,assortDto);
            assortDtos.add(assortDto);
        }
        return assortDtos;
    }

    @Override
    public List<AssortDto> getAssortByParentId(Long parentId) {
        return assortMapper.getAssortByParentId(parentId);
    }

    private List<Map<String, Object>> makeParent(List<Map<String, Object>> list){
        List<Map<String, Object>> parent = new ArrayList<>();
        for(Map<String, Object> map : list){
            //父级id =0 是最上层分类
            if(map.get("pId").toString().equals("0")){
                map.put("children",new ArrayList<>());
                parent.add(map);
            }
        }
        list.removeAll(parent);
        makeChildren(parent, list);
        return parent;
    }


    private void makeChildren(List<Map<String, Object>> parent, List<Map<String, Object>> children) {
        if (children.isEmpty()) {
            return;
        }
        List<Map<String, Object>> tmp = new ArrayList<>();
        for (Map<String, Object> c1 : parent) {
            for (Map<String, Object> c2 : children) {
                c2.put("children",new ArrayList<>());
                if (c1.get("id").equals(c2.get("pId"))) {
                    List<Map<String, Object>> temp = (List<Map<String, Object>> )c1.get("children");
                    temp.add(c2);
                    c1.put("children",temp);
                    tmp.add(c2);
                }
            }
        }
        children.removeAll(tmp);
        makeChildren(tmp, children);
    }

    @Override
    public List<AssortDto> getAssortAByParentId(Long parentId) {
        return assortMapper.getAssortAByParentId(parentId);
    }
    @Override
   public List<AssortDto> selUnderAssort(Long assortId){
       return assortMapper.selUnderAssort(assortId);
    }
}
