package com.chairking.poom.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.admin.domain.Notice;
import com.chairking.poom.admin.domain.NoticeRepository;

@Controller
@RequestMapping("/payAdmin")
public class PayAdminController {
	
	@Autowired
	private NoticeRepository jpa;

	//결제관리 첫화면
	@GetMapping()
	public ModelAndView pay(ModelAndView mv) {
		mv.addObject("type","pay");
		mv.setViewName("admin/admin_pay");
		return mv;
	}
		
}
