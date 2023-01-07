package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;


//@SpringBootTest:表示标注当前的类是一个测试类，不会随项目一块打包
@SpringBootTest
//启动单元测试类，需要传递一个SpringRunner.class
@RunWith(SpringRunner.class)
public class AddressMapperTests {
    /**
     * 单元测试方法不启动整个项目，单独运行的条件：
     * 1.必须被test注解所修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不能指定任何的类型
     * 4.方法的返回修饰值必须是public
     */
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(10);
        address.setPhone("123123434567");
        address.setName("女朋友");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
        Integer count = addressMapper.countByUid(10);
        System.out.println(count);
    }

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(10);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        System.err.println(addressMapper.findByAid(14));

    }

    @Test
    public void updateNonDefault(){
        addressMapper.updateNonDefault(10);
    }

    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(
                4,
                "管理员",
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @Test
    public void deleteByAid(){
        addressMapper.deleteByAid(6);
    }

    @Test
    public void findLastModified(){
        System.out.println(addressMapper.findLastModified(10));
    }

    @Test
    public void updateAddress(){
        Address address = new Address();
        address.setName("小明");
        address.setAid(10);
        addressMapper.updateAddress(address);
    }

}
