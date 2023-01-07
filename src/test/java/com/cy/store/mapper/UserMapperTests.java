package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneId;
import java.util.Date;


//@SpringBootTest:表示标注当前的类是一个测试类，不会随项目一块打包
@SpringBootTest
//启动单元测试类，需要传递一个SpringRunner.class
@RunWith(SpringRunner.class)
public class UserMapperTests {
    /**
     * 单元测试方法不启动整个项目，单独运行的条件：
     * 1.必须被test注解所修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不能指定任何的类型
     * 4.方法的返回修饰值必须是public
     */

    @Autowired
    private UserMappper userMapper;
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }
    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(8,"321","管理员",new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(8));
    }

    @Test
    public void uptateInfoByUid(){
        User user = new User();
        user.setUid(9);
        user.setPhone("18508053212");
        user.setEmail("test02@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(10,
                                     "/user/avatar.jpg",
                                     "管理员",
                                     new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }



}
