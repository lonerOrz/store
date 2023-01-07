package com.cy.store.mapper;

import com.cy.store.entity.Product;

import java.util.List;

public interface ProductMapper {

    //热销商品前四名
    List<Product> findHotList();

    //根据商品id查询商品详情
    Product findById(Integer id);

}
