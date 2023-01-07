package com.cy.store.util;

import java.io.Serializable;

/**
 * Json格式的数据据进行响应
 */
public class JsonResult<E> implements Serializable {
    //状态码
    private Integer state;
    //描述信息
    private String messsge;
    //数据
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Throwable e) {
        this.messsge = e.getMessage();
    }

    public JsonResult(Integer state,E data) {
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMesssge() {
        return messsge;
    }

    public void setMesssge(String messsge) {
        this.messsge = messsge;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

}
