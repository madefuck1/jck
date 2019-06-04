package com.soufang.feign.hystrix;

import com.soufang.base.Result;
import com.soufang.base.dto.assort.AssortDto;
import com.soufang.feign.AdminAssortFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/23
 * @Description:
 */
@Component
public class AdminAssortHystrix implements AdminAssortFeign {


    @Override
    public List<AssortDto> getAll() {
        return new ArrayList<>();
    }

    @Override
    public Result save(@RequestBody AssortDto assortDto) {
        return new Result(false,"暂无数据");
    }

    @Override
    public AssortDto detail(@RequestBody Long assortId) {
        return new AssortDto();
    }

    @Override
    public Result delete(@RequestBody Long id) {
        return new Result(false,"暂无数据");
    }

    @Override
    public Result update(@RequestBody AssortDto assortDto) {
        return new Result(false,"暂无数据");
    }
}
