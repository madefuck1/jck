package com.soufang.service;

import com.soufang.base.dto.adminUser.AdminUserDto;

/**
 * @Auther: chen
 * @Date: 2019/4/29
 * @Description:
 */
public interface AdminUserService {

    AdminUserDto getUserByPhone(String phone);

    AdminUserDto getById(Long id);
}
