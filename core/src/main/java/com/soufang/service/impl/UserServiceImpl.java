package com.soufang.service.impl;

import com.soufang.base.BusinessException;
import com.soufang.base.dto.user.UserDto;
import com.soufang.base.enums.UserLevelEnum;
import com.soufang.base.search.user.UserSo;
import com.soufang.base.utils.DateUtils;
import com.soufang.base.utils.MD5Utils;
import com.soufang.mapper.UserMapper;
import com.soufang.model.User;
import com.soufang.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDto getById(Long id) {
        UserDto userDto = userMapper.getById(id);
        return userDto;
    }

    @Override
    public List<UserDto> getList(UserSo userSo) {
        User user = new User();
        user.setRealName(userSo.getRealName());
        user.setUserName(userSo.getUserName());
        user.setPhone(userSo.getPhone());
        user.setEmail(userSo.getEmail());
        List<User> list = userMapper.getList(user);
        List<UserDto> listDto = new ArrayList<>();
        for (User user1 : list) {
            UserDto userDto1 = new UserDto();
            BeanUtils.copyProperties(user1,userDto1);
            listDto.add(userDto1);
        }
        return listDto;
    }
    @Override
    public List<UserDto> getUsers(UserDto userDto) {
        User user = new User();
        user.setRealName(userDto.getRealName());
        user.setUserName(userDto.getUserName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        List<User> list = userMapper.getList(user);
        List<UserDto> listDto = new ArrayList<>();
        for (User user1 : list) {
            UserDto userDto1 = new UserDto();
            BeanUtils.copyProperties(user1,userDto1);
            listDto.add(userDto1);
        }
        return listDto;
    }

    @Override
    public int getCount(UserSo userSo) {
        User user = new User();
        user.setUserName(userSo.getUserName());
        user.setPhone(userSo.getPhone());
        user.setEmail(userSo.getEmail());
        return userMapper.getCount(user);
    }

    @Override
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    @Transactional
    public Long addUser(UserDto userDto) {
        try {
            User user = new User();
            userDto.setUserStatus(0);
            userDto.setCreateTime(DateUtils.getToday());
            userDto.setUserLevel(UserLevelEnum.one.getValue());
            BeanUtils.copyProperties(userDto,user);
            user.setPassWord(MD5Utils.md5(userDto.getPassWord()));
            userMapper.addUser(user);
            return user.getUserId();
        }catch (Exception e){
            logger.info("添加用户失败："+e.toString());
            throw new BusinessException("添加用户失败");
        }
    }

    @Override
    public int updateUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        return userMapper.updateUser(user);
    }

    @Override
    public int ableUser(Long id) {
        userMapper.ableUser(id);
        return 1;
    }

    @Override
    public int disAbleUser(Long id) {
        userMapper.disAbleUser(id);
        return 1;
    }

    @Override
    public int resetPass(Long id) {
        userMapper.resetPass(id);
        return 1;
    }

    /**
     * 由店铺id拿用户信息
     * @param id
     * @return
     */
    @Override
    public UserDto getByShopId(Long id) {
        User user = userMapper.getByShopId(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    /**
     * 由询盘编号拿用户信息
     * @param enquiryNumber  询盘编号
     * @return
     */
    @Override
    public UserDto getByEnquiryNumber(String enquiryNumber) {
        User user = userMapper.getByEnquiryNumber(enquiryNumber);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    @Override
    public UserDto getOneUser(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        User temp = userMapper.loginByPhone(user);
        if(temp == null){
            temp = userMapper.loginByEmail(user);
            if(temp == null){
                temp = userMapper.loginByUserName(user);
            }
        }
        if(temp == null){
            return null ;
        }else {
            UserDto userDto1 = new UserDto();
            BeanUtils.copyProperties(temp,userDto1);
            return userDto1;
        }
    }

    //更新密码
    @Override
    @Transactional
    public int update(UserDto userDto){
       try {
           User user =new User();
           BeanUtils.copyProperties(userDto,user);
           if(user.getUserId() == null && StringUtils.isNotBlank(user.getPhone()) && StringUtils.isNotBlank(user.getPassWord())){
               userMapper.updatePasswordByPhone(user);
           }else if(user.getUserId() == null && StringUtils.isNotBlank(user.getEmail()) && StringUtils.isNotBlank(user.getPassWord())){
               userMapper.updatePasswordByEmail(user);
           }else {
               userMapper.updateUser(user);
           }
            return 1;
       }catch (Exception e){
           logger.info("更新用户报错:"+e.getMessage());
           throw new BusinessException("更新用户失败");
       }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateUserInfo(UserDto userDto) {
        try {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            userMapper.updateUser(user);
        }catch (Exception e){
            logger.info("更新用户信息报错"+e.getMessage());
            throw new BusinessException("更新用户出错");
        }
    }
}
