package com.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果用户已经登录，放行
        Object user = request.getSession().getAttribute("USER_SESSION");
        if(user!=null){
            return true;//放行
        }
        //如果做和登录相关得操作，放行
        String requestURI=request.getRequestURI();
        if(requestURI.indexOf("login")>=0){
            return true;
        }
        //其他，拦截并跳转到登录页面
        request.setAttribute("msg","你还没有登录，请先登录");
        request.getRequestDispatcher("/admin/login.jsp").forward(request,response);
        return false;
    }
}
