package com.soufang.mapper;

import com.soufang.model.Address;

import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(Long addressId);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long addressId);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    List<Address> getList(Long userId);

    int updatedDefaultAddress(Long addressId);

    List<Address> selectDefaultAddress(Long addressId);

    int updatedNoDefaultAddress(Address address);

    int updatedNoDefaultAddressByUserId(Long userId);

    Address getDefaultAddress(Long userId);
}