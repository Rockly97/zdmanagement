package com.zdxt.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("zdUser") == null) {
            response.sendRedirect("/admin/login");
            return false;
        }
        return true;
    }
}
//    $(" input[ name='test' ] ").val()
//        $(" input[ type='text' ] ").val()