# store商城项目

## 预备工作

项目结构：com.cy.store

项目依赖：java web，my batis，mysql driver

资源文件：resorces文件下的（static，templates）

### 单元测试：test.com.cy.store

#### 1.在properties配置数据库连接信息

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/store?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=password
```

#### 2.创建一个store数据库

```mysql
creat database store character set utf8;
```

#### 3.测试连接

- 启动springboot的主类，是否有对应的spring图形输出
- 在单元测试类中测试数据库的连接是否可以正常加载

#### 4.访问项目静态资源是否可以正常加载，将所有静态资源复制到static目录下

[^注意]:idea对于JS代码的兼容性较差，编写的JS代码有时候不能正常加载。

- idea缓存清理
- clear - install
- rebuild重构
- 重启idea和操作系统
