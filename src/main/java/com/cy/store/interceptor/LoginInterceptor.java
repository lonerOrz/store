package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 定义一个拦截器 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 检测全局session对象中是否有uid，如果有则放行，如果没有则重定向到登录页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器（url+Controller的映射）
     * @return 如果返回值为true表示放行当前请求，如果返回值为flase表示拦截当前请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //HttpServletRequest来获取session对象
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null){
            //用户没有登录，则重定向到登录页面
            response.sendRedirect("/web/login.html");
            //结束后续的调用
            return false;
        }
        //请求放行
        return true;
    }
}
