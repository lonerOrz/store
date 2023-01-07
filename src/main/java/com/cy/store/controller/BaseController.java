package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int OK = 200;

    //请求处理方法，这个方法的返回值就是需要传递给前端的数据
    //自动将异常对象传递给此方法的参数列表上
    //当项目中发生了异常，被统一拦截到此方法中，这个方法此时就充当的是请求处理方法，方法返回值直接给到前端
    @ExceptionHandler({ServiceException.class,FileUploadException.class})//用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMesssge("用户名被占用");
        }else if(e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMesssge("用户数据不存在异常");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMesssge("用户密码错误的异常");
        }else if(e instanceof AddressCountLimitException){
            result.setState(4003);
            result.setMesssge("用户收货地址超出限制的异常");
        }else if(e instanceof AddressNotFoundException){
            result.setState(4004);
            result.setMesssge("用户收货地址数据不存在的异常");
        }else if(e instanceof AccessDeniedException){
            result.setState(4005);
            result.setMesssge("非法访问数据的异常");
        }else if(e instanceof ProductNotFoundException){
            result.setState(4006);
            result.setMesssge("商品数据不存在的异常");
        }else if(e instanceof InsertException){
            result.setState(5000);
            result.setMesssge("注册时产生未知异常");
        }else if(e instanceof UpdateException) {
            result.setState(5001);
            result.setMesssge("更新数据时产生未知异常");
        }else if(e instanceof DeleteException) {
            result.setState(5002);
            result.setMesssge("删除数据时产生未知异常");
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
        }else if (e instanceof FileSizeException) {
            result.setState(6001);
        }else if (e instanceof FileTypeException) {
            result.setState(6002);
        }else if (e instanceof FileStateException) {
            result.setState(6003);
        }else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录用户的uid
     */
    protected final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取session对象中的username
     * @param session session对象
     * @return 当前登录用户的username
     */
    protected final String getUsernameFromSession(HttpSession session){
        return  session.getAttribute("username").toString();
    }
}
