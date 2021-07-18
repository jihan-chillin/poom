package com.chairking.poom.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping()
	public String admin() {
		return "admin/admin_main";
	}
	
//	@GetMapping("/notice")
//	public ModelAndView notice(String type, ModelAndView mv) {
//		mv.addObject("type",type);
//		mv.setViewName("admin/admin_notice");
//		return mv;
//	}
	
	//ajax
	@GetMapping("/notice")
	public String notice(String type,Model m) {
		System.out.println(type);
		m.addAttribute("type1", type);
		return "admin/admin :: #target";
	}
	@GetMapping("/blame")
	public ModelAndView blame(String type,ModelAndView mv) {
		mv.addObject("type",type);
		mv.setViewName("admin/admin_blame");
		return mv;
	}
	
	@GetMapping("/pay")
	public ModelAndView pay(String type,ModelAndView mv) {
		mv.addObject("type",type);
		mv.setViewName("admin/admin_pay");
		return mv;
	}
	
	@GetMapping("/main")
	public ModelAndView main(String type, ModelAndView mv) {
		mv.addObject("type",type);
		mv.setViewName("admin/admin");
		return mv;
	}
}
