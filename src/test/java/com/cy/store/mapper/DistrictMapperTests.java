package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


//@SpringBootTest:表示标注当前的类是一个测试类，不会随项目一块打包
@SpringBootTest
//启动单元测试类，需要传递一个SpringRunner.class
@RunWith(SpringRunner.class)
public class DistrictMapperTests {
    /**
     * 单元测试方法不启动整个项目，单独运行的条件：
     * 1.必须被test注解所修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不能指定任何的类型
     * 4.方法的返回修饰值必须是public
     */
    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void findByParent(){
        List<District> list = districtMapper.findByParent("210100");
        for(District d : list){
            System.out.println(d);
        }
    }

    @Test
    public void findNameByCode(){
        String name = districtMapper.findNameByCode("610000");
        System.out.println(name);
    }

}
