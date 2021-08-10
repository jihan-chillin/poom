package com.chairking.poom.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
	
	@Autowired
	private LoginCheckInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
				.addPathPatterns("/login/main")
				.addPathPatterns("/message")
				.addPathPatterns("/payAdmin/*")
				.addPathPatterns("/blame/*")
				.addPathPatterns("/admin/*")
				.addPathPatterns("/board/*")
				.addPathPatterns("/chat/*")
				.addPathPatterns("/tag/*")
				.addPathPatterns("/member/*")
				.addPathPatterns("/message/*")
				.addPathPatterns("/mywrite/*")
				.addPathPatterns("/mycomment/*")
				.addPathPatterns("/mylike/*")
				.addPathPatterns("/pay/*")
				.addPathPatterns("/popup/*")
				.addPathPatterns("/search/*")
				.addPathPatterns("/rank/*");
	}
}

