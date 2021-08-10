package com.chairking.poom.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chairking.poom.member.model.vo.Member;

@Configuration
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Object loginMember =  (Object)session.getAttribute("loginMember");
 
        if(loginMember!=null){
            return true;
        }else{
        	response.sendRedirect("/21AM_POOM_final");
            return false;
            
        }
		
		
	}
}


 
