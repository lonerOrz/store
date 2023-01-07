package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

//@SpringBootTest:表示标注当前的类是一个测试类，不会随项目一块打包
@SpringBootTest
//启动单元测试类，需要传递一个SpringRunner.class
@RunWith(SpringRunner.class)
public class CartMapperTests {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(10);
        cart.setPid(10000013);
        cart.setNum(2);
        cart.setPrice(8000L);
        Integer rows = cartMapper.insert(cart);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateNumByCid(){
        Integer cid = 1;
        Integer num = 10;
        String modifiedUser = "购物车管理员";
        LocalDate modifiedTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer rows = cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUidAndPid() {
        Integer uid = 10;
        Integer pid = 10000013;
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        System.out.println(result);
    }


}
