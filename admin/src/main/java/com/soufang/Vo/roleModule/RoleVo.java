package com.soufang.Vo.roleModule;

import com.soufang.Vo.AdminVo;
import com.soufang.base.dto.roleModule.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoleVo extends AdminVo {
    List<RoleDto> data;
}
