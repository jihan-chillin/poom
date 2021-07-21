package com.chairking.poom.admin.controller;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.admin.model.service.AdminService;
import com.chairking.poom.admin.model.vo.Notice;

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
	
	//공지사항 작성=>db등록
	@PostMapping("/noticeWrite")
	public ModelAndView noticeWrite(@RequestParam Map<String,String> param, String[] cateChk,ModelAndView mv) {
		int result;
		Notice n;
		if(cateChk.length==1) {
			n=Notice.builder().cate(cateChk[0]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
			result=service.insertNotice(n);
		}else {
			Notice notice;
			for(int i=0;i<cateChk.length;i++) {
				n=Notice.builder().cate(cateChk[i]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
				result=service.insertNotice(n);
			}
		}
		mv.setViewName("admin/admin");
		return mv;
	}
	

}
