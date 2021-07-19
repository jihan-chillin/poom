package com.chairking.poom.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.admin.model.service.AdminService;
import com.chairking.poom.admin.model.vo.Notice;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService service;
	
	@GetMapping()
	public String admin() {
		//admin main(네모3개)
		return "admin/admin_main";
	}
	
	@GetMapping("/main")
	public ModelAndView main(String type, ModelAndView mv) {
		mv.addObject("type",type);
		mv.setViewName("admin/admin");
		return mv;
	}
	
	//ajax
	@GetMapping("/notice")
	public String notice(String type,Model m) {
		int count =service.countAllNotice();
		List<Notice> list = service.allNotice();
		m.addAttribute("list", list);
		m.addAttribute("count", count);
		m.addAttribute("type1", type);
		System.out.println(list.size());
		return "admin/admin_notice_ajax";
	}
	
	//ajax
	@GetMapping("/blame")
	public ModelAndView blame(String type,ModelAndView mv) {
		mv.addObject("type",type);
		mv.setViewName("admin/admin_blame");
		return mv;
	}
	
	//ajax
	@GetMapping("/pay")
	public ModelAndView pay(String type,ModelAndView mv) {
		mv.addObject("type",type);
		mv.setViewName("admin/admin_pay");
		return mv;
	}
	

}
