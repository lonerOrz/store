package com.cy.store;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired //自动装配
    private DataSource dataSource;
    @Test
    void contextLoads() {
    }

    /**
     * 数据库连接池：
     * 1.DBCP
     * 2.C3P0
     * 3.Hikari
     * HikariProxyConnection@991806841 wrapping com.mysql.cj.jdbc.ConnectionImpl@793d163b
     */
    @Test
    void getConnection(){
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
