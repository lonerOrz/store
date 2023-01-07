package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("addresses")
@RestController
public class AddressController extends BaseController{
    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"/",""})
    public JsonResult<List<Address>> getByUid(HttpSession session){
        List<Address> data = addressService.getByUid(getuidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    //restful风格的请求编写
    @RequestMapping("/{aid}/set_default")
    public JsonResult<Void> setDefault(  @PathVariable("aid") Integer aid,
                                         HttpSession session){
        addressService.setDefault(  aid,
                                    getuidFromSession(session),
                                    getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("/{aid}/delete_address")
    public JsonResult<Void> deleteAddress(@PathVariable("aid")Integer aid,
                                          HttpSession session){

        addressService.deleteAddress(   aid,
                                        getuidFromSession(session),
                                        getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("/{cga}/change_address")
    public JsonResult<Void> updateAddress(@PathVariable("cga")Integer aid,
                                          Address address,
                                          HttpSession session){
        addressService.updateAddress(aid,
                getuidFromSession(session),
                address,
                getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping("/queryOneAddress")
    public JsonResult<Address> queryOneAddress(Integer aid){
        Address address = addressService.queryAddressByAid(aid);
        //过滤部分不需要的字段
        address.setModifiedTime(null);
        address.setModifiedUser(null);
        address.setCreatedTime(null);
        address.setCreatedUser(null);
        return new JsonResult<>(OK,address);
    }

}
