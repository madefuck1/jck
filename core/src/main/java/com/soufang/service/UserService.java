package com.soufang.service;

import com.soufang.base.dto.user.UserDto;
import com.soufang.base.search.user.UserSo;

import java.util.List;

public interface UserService {
    /**
     * 由id拿user信息
     * @return
     */
    UserDto getById(Long id);

    /**
     *条件查询得到user 集合
     * @param userSo
     * @return
     */
    List<UserDto> getList(UserSo userSo);

    List<UserDto> getUsers(UserDto userDto);
    /**
     * 条件查询user 数量
     * @param userSo
     * @return
     */
    int getCount(UserSo userSo);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 添加用户
     * @param userDto
     * @return 返回用户主键
     */
    Long addUser(UserDto userDto);

    /**
     * 更新某个用户信息
     * @param userDto
     * @return
     */
    int updateUser(UserDto userDto);

    /**
     * 恢复用户
     * @param id
     * @return
     */
    int ableUser(Long id );

    /**
     * 禁用用户
     * @param id
     * @return
     */
    int disAbleUser(Long id );

    /**
     * 重置秘密
     * @param id
     * @return
     */
    int resetPass(Long id );

    /**
     * 由店铺id拿用户信息
     * @param id
     * @return
     */
    UserDto getByShopId(Long id);

    /**
     * 由询盘编号拿用户信息
     * @param enquiryNumber  询盘编号
     * @return
     */
    UserDto getByEnquiryNumber(String enquiryNumber);

    /**
     * 由电话/邮箱/用户名配合密码拿到某个用户
     * @param userDto
     * @return
     */
    UserDto getOneUser(UserDto userDto);

    /**
     * 修改用户信息
     * @param userDto
     * @return
     */
    int update(UserDto userDto);

    void updateUserInfo(UserDto userDto);


}
