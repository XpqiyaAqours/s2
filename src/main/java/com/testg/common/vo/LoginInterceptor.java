package com.testg.common.vo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//未登录拦截器
@Slf4j
@RestControllerAdvice(basePackages = "com.testg")
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获得cookie
        Cookie[] cookies = request.getCookies();
        // 没有cookie信息，则重定向到登录界面
        if (null == cookies) {
            response.sendRedirect(request.getContextPath() + "/login");
            System.out.println("未登录拦截");
            return false;
        }
        //获取session中的key
        String key = (String) request.getSession().getAttribute("key");
        //对比key
        for (Cookie cookie : cookies) {

            if (key == cookie.getValue()) {
                System.out.println("登录拦截器已放行");
                return true;

            }
        }
        System.out.println("未登录拦截");
        return false;
    }
}
