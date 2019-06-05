package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.AddressMapper;
import com.zcb.minimalldb.domain.Address;
import com.zcb.minimalldb.domain.AddressExample;
import com.zcb.minimalldb.service.IAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class AddressServiceImpl implements IAddressService {
    @Resource
    private AddressMapper addressMapper;
    @Override
    public List<Address> queryByUid(Integer uid) {
        AddressExample example = new AddressExample();
        example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
        return addressMapper.selectByExample(example);
    }

    @Override
    public Address query(Integer uid, Integer id) {
        AddressExample example = new AddressExample();
        example.or().andUserIdEqualTo(uid).andIdEqualTo(id).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    @Override
    public int addAddress(Address address) {
        address.setAddTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.insert(address);
    }

    @Override
    public int updateAddress(Address address) {
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public int deleteAddress(Integer id) {
        return addressMapper.logicalDeleteByPrimaryKey(id);
    }

    @Override
    public Address findDefault(Integer uid) {
        AddressExample example = new AddressExample();
        example.or().andUserIdEqualTo(uid).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }
}
