package com.soufang.Vo.assort;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.assort.AssortDto;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/19
 * @Description:
 */
public class AssortVo extends AdminVo {

    private List<AssortDto> data;

    public List<AssortDto> getData() {
        return data;
    }

    public void setData(List<AssortDto> data) {
        this.data = data;
    }
}
