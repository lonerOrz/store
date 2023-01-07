package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * 用户模块的持久层接口
 */
public interface UserMappper {
    /**
     * 插入用户的数据
     * @param user 用户的数据
     * @return 返回值是受影响的行数（增，删，改都受影响，可根据返回值判断操作是否成功）
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户数据
     * @param username 用户名
     * @return  如果找到用户名则返回对应数据，如果没有找到则返回NULL值
     */
    User findByUsername(String username);

    /**
     * 根据用户的uid来修改用户的密码
     * @param uid 用户的uid
     * @param password 用户输入的新密码
     * @param modifiedUser 表示修改的执行者
     * @param modifiedTime 表示修改的时间
     * @return 返回值是受影响的行数
     */
    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                LocalDate modifiedTime);

    /**
     * 根据用户uid查询用户数据
     * @param uid 用户的uid
     * @return 如果找到则返回对象，反之返回null
     */
    User findByUid(Integer uid);

    /**
     * 根据用户uid更新用户信息
     * @param user 用户数据
     * @return 返回值为受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * @param("sql映射文件中#{}占位符的变量名")：用于解决SQL语句占位符和映射的接口方法参数名不一致时强行注入
     * 根据用户uid更新用户头像
     * @param uid 用户uid
     * @param avatar 用户头像数据
     * @param modifiedUser 表示修改的执行者
     * @param modifiedTime 表示修改的时间
     */
    Integer updateAvatarByUid(@Param("uid")  Integer uid,
                              @Param("avatar")  String avatar,
                              @Param("modifiedUser")  String modifiedUser,
                              @Param("modifiedTime")  LocalDate modifiedTime);





}
