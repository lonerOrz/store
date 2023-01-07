package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

public interface DistrictMapper {
    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下的所有列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据当前code来获取当前省市区的名称
     * @param code 当前code
     * @return 省市区名称
     */
    String findNameByCode(String code);

}
