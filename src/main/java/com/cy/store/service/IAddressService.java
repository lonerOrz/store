package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

/** 收货地址业务层接口 */
public interface IAddressService {

    void addNewAddress(Integer uid, String username, Address address);

    List<Address> getByUid(Integer uid);

    /**
     * 修改某个用户的默认收货地址
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 修改执行人
     */
    void setDefault(Integer aid,
                    Integer uid,
                    String username);

    /**
     * 删除用户选中的收货地址
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 修改执行人
     */
    void deleteAddress(Integer aid,
                       Integer uid,
                       String username);

    /**
     * 更新用户选中收货地址的信息
     * @param address 新地址信息
     */
    void updateAddress(Integer aid,Integer uid,Address address,String username);

    //查询某条地址的抽象方法
    Address queryAddressByAid(Integer aid);

}


