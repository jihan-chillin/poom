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
	
	@GetMapping("/notice")
	public ModelAndView notice(String type,ModelAndView mv, 
			@PageableDefault(size = 5, sort = {"noticeDate"},direction=Direction.DESC) Pageable pageable) {
			//size, sort, page 기존 페이지당 데이터 수 정하는 것
		
		
		//Page<Notice> list = jpa.findAll(PageRequest.of(0, 20));  //Page의 index는 0부터시작임
		//list.getTotalPages() => 전체 페이지 수
		//list.getTotalElements() =>전체 데이터 갯수
		//System.out.println(list.size());
		
		Page<Notice> list= jpa.findAll(pageable); //url에 page와 size를 보내면 볼 수 있음
		
		//첫페이지
//		int startpage=Math.max(1, list.getPageable().getPageNumber()-4);	//현재 페이지 정보
//		int endpage=Math.min(list.getTotalPages(), list.getPageable().getPageNumber()+5);
//		System.out.println("startpage: "+startpage);
//		System.out.println("endpage: "+endpage);
//		System.out.println("현재페이지" +list.getPageable().getPageNumber());
		
		//현재페이지
		int cPage=list.getPageable().getPageNumber()+1;
		//총페이지 수
		int totalPage=list.getTotalPages();
		//페이지블럭 갯수
		int pageBar=5;
		//페이지블럭 첫 숫자
		int startPage= ((cPage-1)/pageBar)*pageBar+1;
		//페이지블럭 마지막숫자
		int endPage=startPage+pageBar-1;
		endPage=totalPage<endPage?totalPage:endPage;
		
		mv.addObject("cPage",cPage);
		mv.addObject("startpage", startPage);
		mv.addObject("endpage", endPage);
		mv.addObject("list", list);
		mv.setViewName("admin/pagingTest");
		
		
		
		return mv;
	}
	
	
	
	
	
	
	
	//결제관리 첫화면
	@GetMapping()
	public ModelAndView pay(ModelAndView mv) {
		mv.addObject("type","pay");
		mv.setViewName("admin/admin_pay");
		return mv;
	}
		
}
