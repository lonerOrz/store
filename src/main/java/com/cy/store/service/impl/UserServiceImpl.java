package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMappper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service//@Service注解：将当前类交给Spring来管理，自动创建对象以及对象的维护
//用户模块业务层的实现类
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMappper userMappper;

    @Override
    public void reg(User user) {
        //通过user参数来获取传递过来的username
        String username = user.getUsername();
        //调用findByUsername(username)判断用户是否被注册过
        User result = userMappper.findByUsername(username);
        //判断结果集是否不为NULL则抛出用户名被占用的异常
        if(result != null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }

        //密码加密处理的实现：MD5算法的形式：（盐值+password+盐值）再交给MD5进行加密，连续加载三次。
        //盐值就是一个随机的字符串
        String oldPassword = user.getPassword();
        //获取盐值(随机生成)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //补全数据：盐值
        user.setSalt(salt);
        //将密码和盐值进行加密处理
        String md5Password = getMD5Password(oldPassword,salt);
        //将加密之后的密码补全到user对象中
        user.setPassword(md5Password);

        //补全数据：is_delete==0
        user.setIsDelete(0);
        //补全数据：4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        user.setCreatedTime(localDate);
        user.setModifiedTime(localDate);

        //执行注册业务功能的实现(rows==1)
        Integer rows = userMappper.insert(user);
        if (rows != 1){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    @Override
    public User login(String username, String password){
        //根据用户名称来查询数据是否存在，如果不存在则抛出异常
        User result = userMappper.findByUsername(username);
        if (result == null){
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测用户密码是否匹配
        //1.先获取数据库中加密后的密码
        String oldPassword = result.getPassword();
        //2.和用户传递过来的密码进行比较
        //2.1获取盐值：上一次注册时生成的盐值
        String salt = result.getSalt();
        //2.2将用户密码按照相同的md5算法规则进行加密
        String newMd5Password = getMD5Password(password,salt);
        //将密码进行比较
        if (!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断is_delete字段的值是否为1表示标记为被删除
        if (result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }

        //数据返回是为了辅助其他页面做数据展示使用的（uid,username,avatar）提升了系统性能。
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        //将当前用户数据返回
        return user;
    }

    @Override
    public void changePssword(Integer uid, String username, String oldPssword, String newPassword){
        User result = userMappper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //原始密码和数据库中密码进行比较
        String oldMd5Password = getMD5Password(oldPssword,result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //将新密码设计进数据库，新密码先加密再更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMappper.updatePasswordByUid( uid,
                                                        newMd5Password,
                                                        username,
                                                        new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if(rows != 1){
            throw new UpdateException("更新数据产生未知异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMappper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //回显用户信息
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMappper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        Integer rows = userMappper.updateInfoByUid(user);
        if(rows != 1){
            throw new UpdateException("用户数据更新失败");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //查询当前用户数据是否存在
        User result = userMappper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //更新头像
        Integer rows = userMappper.updateAvatarByUid( uid,
                                                      avatar,
                                                      username,
                                                      new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (rows != 1){
            throw new UpdateException("更新用户头像产生未知异常");
        }
    }

    //定义一个MD5算法的加密
    private String getMD5Password(String password, String salt){

        for(int i = 0;i < 3; i++){
            //md5加密算法的调用
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        //返回加密之后的密码
        return password;
    }

}
