package com.soufang.mapper;

import com.soufang.base.dto.user.UserDto;
import com.soufang.model.User;

import java.util.List;

public interface UserMapper {

    /**
     * 由id拿user信息
     * @return
     */
    UserDto getById(Long id);

    /**
     *条件查询得到user 集合
     * @param user
     * @return
     */
    List<User> getList(User user);

    /**
     * 条件查询user 数量
     * @param user
     * @return
     */
    int getCount(User user);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 更新某个用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

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
    User getByShopId(Long id);

    /**
     * 由询盘编号拿用户信息
     * @param enquiryNumber  询盘编号
     * @return
     */
    User getByEnquiryNumber(String enquiryNumber);

    /**
     * 由电话/邮箱/用户名配合密码拿到某个用户
     * @param user
     * @return
     */
    User loginByPhone(User user);
    User loginByEmail(User user);
    User loginByUserName(User user);

    /**
     * 通过邮箱或电话修改密码
     * @param user
     * @return
     */
    int updatePasswordByPhone(User user);
    int updatePasswordByEmail(User user);
}
