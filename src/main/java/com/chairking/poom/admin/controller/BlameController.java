package com.chairking.poom.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.admin.model.service.AdminService;

@Controller
@RequestMapping("/blame")
public class BlameController {
	@Autowired
	private AdminService service;
	
	//채팅방 신고
	@GetMapping("/chatBlame")
	public ModelAndView chatBlame(HttpServletRequest req, ModelAndView mv) {
		//System.out.println("chatNo:"+req.getParameter("chatNo"));
		//System.out.println("memberId:"+req.getParameter("memberId"));
		mv.setViewName("admin/admin");
		return mv;
	}
	
	//신고 팝업창
	@RequestMapping(value="/report",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView insertBlame(@RequestParam Map<String,String> map,ModelAndView mv) {
		//신고대상 값 보내야함
		
		System.out.println(map);
		mv.addObject("map", map);
		mv.addObject("html", "common/blame_popup");
		mv.setViewName("common/blame_popup");
		return mv;
	}
	
	@GetMapping("/report/page")
	public String reportPage() {return "common/blame_popup";}

}
