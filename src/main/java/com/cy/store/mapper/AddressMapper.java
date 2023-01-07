package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/** 收货地址持久层的接口 */
public interface AddressMapper {

    /**
     * 插入用户的收货地址数据
     *
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户id查询用户收货地址数量
     *
     * @param uid 用户id
     * @return 当前用户的收货地址数量
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户id查询用户的收货地址数据
     *
     * @param uid 用户id
     * @return 收货地址数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据
     *
     * @param aid 收货地址aid
     * @return 收货地址数据
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户id设置用户所有收货地址设置非默认
     *
     * @param uid 用户id
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);


    /**
     * 根据aid设置用户的默认收货地址
     *
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(
            @Param("aid") Integer aid,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") LocalDate modifiedTime);

    /**
     * 根据收货地址id删除数据
     *
     * @param aid 地址id
     * @return 受影响的行数
     */
    Integer deleteByAid(@Param("aid") Integer aid);

    /**
     * 查找最新修改的地址数据
     * @param uid 用户id
     * @return 地址数据
     */
    Address findLastModified(Integer uid);

    /**
     * 更新当前收货地址信息
     * @param address 新地址信息
     * @return 影响的行数
     */
    Integer updateAddress(Address address);


}