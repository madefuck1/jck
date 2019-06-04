package com.soufang.app.vo.productManage;

import com.soufang.app.vo.AppVo;
import com.soufang.base.dto.assort.AssortDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ListAssrotVo extends AppVo {
    List<AssortDto> data;
}
