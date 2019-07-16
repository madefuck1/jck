package com.soufang.service.impl;

import com.soufang.base.dto.address.AddressDto;
import com.soufang.base.utils.DateUtils;
import com.soufang.mapper.AddressMapper;
import com.soufang.model.Address;
import com.soufang.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    @Override
    public List<AddressDto> getList(Long userId) {
        List<AddressDto> listDto = new ArrayList<>();
        List<Address> list = addressMapper.getList(userId);
        for (Address address : list) {
            AddressDto addressDto = new AddressDto();
            BeanUtils.copyProperties(address,addressDto);
            listDto.add(addressDto);
        }
        return listDto;
    }

    @Override
    public int insertSelective(AddressDto addressDto) {
        Address address = new Address();
        BeanUtils.copyProperties(addressDto,address);
        if(address.getCreateTime() == null){
            address.setCreateTime(DateUtils.getToday());
        }
        if(address.getAddressType() == null){
            address.setAddressType(0);
        }
        //先判断改地址是否是改用户的第一个地址，如果是，则将此地址设为默认地址
        List<Address> addresses = addressMapper.getList(address.getUserId());
        if(!(addresses != null && addresses.size() > 0 )){
            address.setIsDefaultAddress(1);
        }
        //判断新增的地址类型，如果是默认地址，则将原来的默认地址改为非默认地址
        if(address.getIsDefaultAddress() == 1){
            addressMapper.updatedNoDefaultAddressByUserId(address.getUserId());
        }
        return addressMapper.insertSelective(address);
    }

    @Override
    public int updateByPrimaryKeySelective(AddressDto addressDto) {
        Address address = new Address();
        BeanUtils.copyProperties(addressDto,address);
        //如果是默认地址，则将原来的默认地址改为非默认地址
        if(address.getIsDefaultAddress() == 1){
            addressMapper.updatedNoDefaultAddressByUserId(address.getUserId());
        }
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public int deleteByPrimaryKey(Long addressId) {
        return addressMapper.deleteByPrimaryKey(addressId);
    }

    @Override
    public int updatedDefaultAddress(Long addressId) {
        return addressMapper.updatedDefaultAddress(addressId);
    }

    @Override
    public List<Address> selectDefaultAddress(Long addressId) {
        return addressMapper.selectDefaultAddress(addressId);
    }

    @Override
    public int updatedNoDefaultAddress(Address address) {
        return addressMapper.updatedNoDefaultAddress(address);
    }

    @Override
    public AddressDto selectByPrimaryKey(Long addressId) {
        AddressDto addressDto = new AddressDto();
        Address address = addressMapper.selectByPrimaryKey(addressId);
        BeanUtils.copyProperties(address,addressDto);
        return addressDto;
    }

    @Override
    public AddressDto getDefaultAddress(Long userId) {
        Address address = addressMapper.getDefaultAddress(userId);
        AddressDto addressDto = new AddressDto();
        BeanUtils.copyProperties(address,addressDto);
        return addressDto;
    }

}
