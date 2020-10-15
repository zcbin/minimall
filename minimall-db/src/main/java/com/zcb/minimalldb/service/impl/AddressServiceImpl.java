package com.zcb.minimalldb.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcb.minimalldb.dao.AddressMapper;
import com.zcb.minimalldb.domain.Address;
import com.zcb.minimalldb.domain.AddressExample;
import com.zcb.minimalldb.service.IAddressService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public int add(Address address) {
        address.setAddTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.insertSelective(address);
    }

    @Override
    public int update(Address address) {
        address.setUpdateTime(LocalDateTime.now());
        return addressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public int delete(Integer id) {
        return addressMapper.logicalDeleteByPrimaryKey(id);
    }


    @Override
    public Address findDefault(Integer uid) {
        AddressExample example = new AddressExample();
        example.or().andUserIdEqualTo(uid).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.selectOneByExample(example);
    }

    @Override
    public int resetDefault(Integer uid) {
        Address address = new Address();
        address.setIsDefault(false);
        AddressExample example = new AddressExample();
        example.or().andUserIdEqualTo(uid).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
        return addressMapper.updateByExampleSelective(address, example);
    }

    @Override
    public List<Address> query(Integer userid, String name, Integer offset, Integer limit, String sort, String order) {
        AddressExample example = new AddressExample();
        AddressExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(userid)) {
            criteria.andUserIdEqualTo(userid);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameEqualTo(name);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(offset, limit);
        return addressMapper.selectByExample(example);
    }
}
