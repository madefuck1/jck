package com.soufang.Vo.dictionary;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.dictionary.DictionaryDto;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/9
 * @Description:
 */
public class DictionaryVo extends AdminVo {
    private List<DictionaryDto> data;

    public List<DictionaryDto> getData() {
        return data;
    }

    public void setData(List<DictionaryDto> data) {
        this.data = data;
    }
}
