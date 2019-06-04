package com.soufang.Vo.roleModule;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.roleModule.ModuleDto;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/24
 * @Description:
 */
public class ModuleVo extends AdminVo {

    private List<ModuleDto> data ;

    public List<ModuleDto> getData() {
        return data;
    }

    public void setData(List<ModuleDto> data) {
        this.data = data;
    }
}
