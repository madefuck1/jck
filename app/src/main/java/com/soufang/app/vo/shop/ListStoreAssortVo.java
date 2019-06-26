package com.soufang.app.vo.shop;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.storeConstruction.StoreExclusiveAssortDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListStoreAssortVo extends AppVo {
    List<StoreExclusiveAssortDto> data;
}
