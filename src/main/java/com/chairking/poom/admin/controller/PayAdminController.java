package com.chairking.poom.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/payAdmin")
public class PayAdminController {
	//결제관리 첫화면
	@GetMapping()
	public ModelAndView pay(ModelAndView mv) {
		mv.addObject("type","pay");
		mv.setViewName("admin/admin_pay");
		return mv;
	}
		
}
