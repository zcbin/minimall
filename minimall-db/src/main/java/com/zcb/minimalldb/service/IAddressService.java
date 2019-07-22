package com.zcb.minimalldb.service;

import com.zcb.minimalldb.domain.Address;

import java.util.List;

public interface IAddressService {
    /**
     * 查询用户的所有收货地址
     * @param uid 用户id
     * @return
     */
    List<Address> queryByUid(Integer uid);

    /**
     * 根据用户id和地址id查询
     * @param uid
     * @param id
     * @return
     */
    Address query(Integer uid, Integer id);

    /**
     * 新增收货地址
     * @param address
     * @return
     */
    int addAddress(Address address);

    /**
     * 修改收货地址
     * @param address
     * @return
     */
    int updateAddress(Address address);

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    int deleteAddress(Integer id);

    /**
     * 查找默认地址
     * @param uid
     * @return
     */
    Address findDefault(Integer uid);

    /**
     * 查询地址
     * @param userid
     * @param name
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    List<Address> query(Integer userid, String name, Integer offset, Integer limit, String sort, String order);
}
