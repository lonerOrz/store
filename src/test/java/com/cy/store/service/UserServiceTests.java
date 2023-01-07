package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


//@SpringBootTest:表示标注当前的类是一个测试类，不会随项目一块打包
@SpringBootTest
//启动单元测试类，需要传递一个SpringRunner.class
@RunWith(SpringRunner.class)
public class UserServiceTests {
    /**
     * 单元测试方法不启动整个项目，单独运行的条件：
     * 1.必须被test注解所修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不能指定任何的类型
     * 4.方法的返回修饰值必须是public
     */

    @Autowired
    private IUserService userService;
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("test02");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //先获取类的对象再获取异常的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userService.login("test0002","123");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
       userService.changePssword(7,"管理员","123","321");
    }

    @Test
    public void getByUid(){
        System.err.println(userService.getByUid(9));
    }

    @Test
    public void  changeInfo(){
        User user = new User();
        user.setPhone("17731288888");
        user.setEmail("test02@gmail.com");
        user.setGender(0);
        userService.changeInfo(9,"test02",user);
    }

    @Test
    public void changeAvatar(){
        userService.changeAvatar( 10,
                                "/user/test.png",
                                "小明");
    }

}
