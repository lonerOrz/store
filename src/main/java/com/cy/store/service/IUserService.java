package com.cy.store.service;

import com.cy.store.entity.User;

//用户模块业务层接口
    public interface IUserService {
    /**
     * 用户注册方法
     * @param user
     */
    void reg(User user);

    /**
     * 用户登录方法
     * @param username 用户名
     * @param password 用户密码
     */
    User login(String username, String password);

    /**
     *
     * @param uid
     * @param username
     * @param oldPssword
     * @param newPassword
     */
    void changePssword(Integer uid,
                       String username,
                       String oldPssword,
                       String newPassword
                       );
    /**
     *根据用户uid查询用户数据
     * @param uid 用户id
     * @return 返回用户数据
     */
    User getByUid(Integer uid);


    /**
     * 更新用户数据的操作
     * @param uid 用户id
     * @param username 用户名
     * @param user 用户对象的数据
     */
    void changeInfo(Integer uid,
                    String username,
                    User user);


    /**
     * 更新用户头像的操作
     * @param uid 用户id
     * @param avatar 用户更新的头像路径
     * @param username 用户名称
     */
    void changeAvatar(Integer uid,
                      String avatar,
                      String username);


}



