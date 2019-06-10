package com.soufang.vo.StoreConstruction;

import com.soufang.base.dto.storeConstruction.StoreCurouselMapDto;
import com.soufang.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListStoreMapVo extends BaseVo {
    List<StoreCurouselMapDto> list;
}
