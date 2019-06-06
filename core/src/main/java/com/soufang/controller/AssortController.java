package com.soufang.controller;

import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.service.AssortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/19
 * @Description:
 */
@RestController
@RequestMapping("/core/assort")
public class AssortController {

    @Autowired
    AssortService assortService;


    /**
     * 查所有
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    public List<AssortDto> getAll() {
        return assortService.getAll();
    }


    /**
     * 新增
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result save(@RequestBody AssortDto assortDto){
        Result result = new Result();
        List<AssortDto> list = assortService.getList(assortDto);
        if(list != null && list.size() > 0){
            result.setSuccess(false);
            result.setMessage("已存在");
            return result;
        }else {
            assortService.insertSelective(assortDto);
            result.setSuccess(true);
            result.setMessage("添加成功");
            return result;
        }
    }

    /**
     * 获取对应分类明细
     * @param assortId
     * @return
     */
    @RequestMapping(value = "getDetail",method = RequestMethod.POST)
    public AssortDto detail(@RequestBody Long assortId){
        return assortService.selectByPrimaryKey(assortId);
    }


    /**
     * 删除
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result delete(@RequestBody Long id) {
        Result result = new Result();
        if (assortService.deleteByPrimaryKey(id) > 0) {
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestBody AssortDto assortDto) {
        Result result = new Result();
        if (assortService.updateByPrimaryKeySelective(assortDto) > 0) {
            result.setSuccess(true);
            result.setMessage("更新成功");
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 获取分类ID
     * @param assortName
     * @return
     */
    @RequestMapping(value = "getAssortIdByName", method = RequestMethod.POST)
    public Long getAssortIdByName(@RequestBody String assortName){
        return assortService.getAssortIdByName(assortName);
    }
}
