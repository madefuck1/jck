package com.soufang.service;

import com.soufang.base.dto.address.AddressDto;
import com.soufang.model.Address;

import java.util.List;

public interface AddressService {
    /**
     * 查询所有地址
     * @return
     */
    List<AddressDto> getList(Long userId);

    /**
     * 添加地址
     * @param addressDto
     * @return
     */
    int insertSelective(AddressDto addressDto);

    /**
     * 修改地址
     * @param addressDto
     * @return
     */
    int updateByPrimaryKeySelective(AddressDto addressDto);

    /**
     * 删除地址
     * @param addressId
     * @return
     */
    int deleteByPrimaryKey(Long addressId);

    /**
     * 设为默认地址
     * @param addressId
     * @return
     */
    int updatedDefaultAddress(Long addressId);

    /**
     * 查询默认地址是否存在
     * @param addressId
     * @return
     */
    List<Address> selectDefaultAddress(Long addressId);

    /**
     * 将某个用户地址改为非默认地址
     * @param address
     * @return
     */
    int updatedNoDefaultAddress(Address address);

    /**
     * 查询地址详情
     * @param addressId
     * @return
     */
    AddressDto selectByPrimaryKey(Long addressId);

    /**
     * 获取用户默认地址
     * @param userId
     * @return
     */
    AddressDto getDefaultAddress(Long userId);
}
