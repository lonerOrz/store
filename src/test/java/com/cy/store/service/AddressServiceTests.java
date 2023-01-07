package com.cy.store.service;

import com.cy.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


//@SpringBootTest:表示标注当前的类是一个测试类，不会随项目一块打包
@SpringBootTest
//启动单元测试类，需要传递一个SpringRunner.class
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    /**
     * 单元测试方法不启动整个项目，单独运行的条件：
     * 1.必须被test注解所修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不能指定任何的类型
     * 4.方法的返回修饰值必须是public
     */

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("12312349999");
        address.setName("男朋友");
        addressService.addNewAddress(10,"管理员",address);
    }

    @Test
    public void setDefault(){
        addressService.setDefault(8,10,"test03");
    }

    @Test
    public void deleteAddress(){
        addressService.deleteAddress(9,9,"管理员");
    }

    @Test
    public void updateAddress(){
        Address address = new Address();
        address.setName("test");
        addressService.updateAddress(10,9,address,"管理员");
        System.err.println(address);
    }

    @Test
    public void queryAddressByAid(){
        System.err.println(addressService.queryAddressByAid(14));
    }

}
