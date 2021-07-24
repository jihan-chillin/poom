package com.chairking.poom.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor{
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        User loginMember = (User) session.getAttribute("loginMember");
 
        if(ObjectUtils.isEmpty(loginMember)){
            response.sendRedirect("/");
            return false;
        }else{
            session.setMaxInactiveInterval(30*60);
            return true;
        }
        
    }
 

 
}