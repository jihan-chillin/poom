package com.chairking.poom.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.admin.model.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
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
		//List<Notice> list = service.allNotice();
		//System.out.println(list.size());
		
		List<Map<String,Object>> list = service.allNotice();
		m.addAttribute("list", list);
		m.addAttribute("count", count);
		return "admin/admin_notice";
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
	
	//공지사항글쓰기
	@GetMapping("/moveWrite")
	public ModelAndView moveWrite(ModelAndView mv) {
		mv.addObject("type", "등록");
		mv.setViewName("admin/admin_notice_write");
		return mv;
	}
	
	@PostMapping("/noticeWrite")
	public ModelAndView noticeWrite(@RequestParam Map map, String[] cateChk,ModelAndView mv) {
		map.put("cate", cateChk);
		for(String s: cateChk) {
			System.out.println(s);
		}
		int result = service.insertNotice(map);
		mv.setViewName("admin/admin");
		return mv;
	}
	

}
