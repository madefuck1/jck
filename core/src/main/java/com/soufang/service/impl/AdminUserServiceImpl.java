package com.soufang.service.impl;

import com.soufang.base.dto.adminUser.AdminUserDto;
import com.soufang.mapper.AdminUserMapper;
import com.soufang.model.AdminUser;
import com.soufang.service.AdminUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: chen
 * @Date: 2019/4/29
 * @Description:
 */
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    AdminUserMapper adminUserMapper;

    @Override
    public AdminUserDto getUserByPhone(String phone) {
        AdminUser adminUser = adminUserMapper.selectByPhone(phone);
        AdminUserDto adminUserDto = new AdminUserDto();
        if(adminUser != null){
            BeanUtils.copyProperties(adminUser,adminUserDto);
        }
        return adminUserDto;
    }

    @Override
    public AdminUserDto getById(Long id) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(id);
        AdminUserDto adminUserDto = new AdminUserDto();
        BeanUtils.copyProperties(adminUser,adminUserDto);
        return adminUserDto ;
    }
}
