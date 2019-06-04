package com.soufang.app.vo.dictionary;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.dictionary.DictionaryDto;

import java.util.List;

/**
 * @Auther: chen
 * @Date: 2019/4/30
 * @Description:
 */
public class DictionaryVo extends AppVo {

    private List<DictionaryDto> data ;

    public List<DictionaryDto> getData() {
        return data;
    }

    public void setData(List<DictionaryDto> data) {
        this.data = data;
    }
}
