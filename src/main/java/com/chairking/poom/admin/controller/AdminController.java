package com.chairking.poom.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.admin.domain.NoticeRepository;
import com.chairking.poom.admin.domain.Notice;
import com.chairking.poom.admin.model.service.AdminService;
import com.chairking.poom.admin.model.vo.Notice1;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	@Autowired
	NoticeRepository pageNotice;
	
	@Autowired
	private AdminService service;
	
	@GetMapping()
	public String admin() {
		//admin main(네모3개)
		return "admin/admin_main";
	}
	
//	@GetMapping("/main")
//	public ModelAndView main(String type, ModelAndView mv) {
//		mv.addObject("type",type);
//		mv.setViewName("admin/admin");
//		return mv;
//	}
//	
	//ajax
	@GetMapping("/notice")
	public ModelAndView notice(ModelAndView mv, 
						@PageableDefault(size=10,sort= {"noticeDate"},direction=Direction.DESC) Pageable pageable) {
		//Pageable을 사용하여 페이징처리하기
//		List<Map<String,Object>> list = service.allNotice(startIndex,10);
		Page<Notice> list = pageNotice.findAll(pageable);
		//총페이지 수
		int totalPage=list.getTotalPages();
		//페이지블럭 갯수
		int pageBar=5;
		//페이지블럭 첫 숫자
		int startPage= ((list.getPageable().getPageNumber())/pageBar)*pageBar+1;
		//페이지블럭 마지막숫자
		int endPage=startPage+pageBar-1;
		//전체 페이지 수 보다 endPage가 크면 totalPage로 초기화
		endPage=totalPage<endPage?totalPage:endPage;
		//현재페이지 (pageable은 0부터 시작함 그래서 +1해야 cPage)
		//int cPage=Math.max(list.getPageable().getPageNumber()+1,endPage);
		int cPage=list.getPageable().getPageNumber()+1;
		
		mv.addObject("cPage",cPage);
		mv.addObject("startpage", startPage);
		mv.addObject("endpage", endPage);
		mv.addObject("list", list);
		mv.addObject("totalPage", totalPage);
		mv.setViewName("admin/admin_notice");
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
	@Transactional
	public ModelAndView noticeWrite(@RequestParam Map<String,String> param, String[] cateChk,ModelAndView mv) {
		int result;
		Notice1 n;
		if(cateChk.length==1) {
			n=Notice1.builder().cate(cateChk[0]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
			result=service.insertNotice(n);
		}else {
			for(int i=0;i<cateChk.length;i++) {
				n=Notice1.builder().cate(cateChk[i]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
				result=service.insertNotice(n);
			}
		}
		mv.addObject("type", "notice");
		mv.setViewName("redirect:/admin/notice");
		return mv;
	}
	
	//공지사항 상세페이지
	@GetMapping("/noticeView")
	@Transactional
	public ModelAndView noticeView(@RequestParam String no, ModelAndView mv) {
		System.out.println(no);
		Map<String,Object> notice = service.selectNotice(no);
		mv.addObject("notice", notice);
		mv.addObject("type", "수정");
		mv.setViewName("admin/noticeView");
		return mv;
	}
	
	//공지사항 삭제
	@GetMapping("/noticeDelete")
	public ModelAndView noticeDelete(@RequestParam String no, ModelAndView mv) {
		//status 1로 수정하기 
		int result=service.noticeDelete(no);
		if(result>0) {
			mv.setViewName("redirect:/admin/notice");
		}else {
			mv.setViewName("redirect:/admin/main");
		}
		return mv;
	}
	
	//공지사항 수정페이지 이동
	@GetMapping("/noticeEdit")
	public ModelAndView noticeEdit(@RequestParam String no, ModelAndView mv) {
		Map<String,Object> notice=service.selectNotice(no);
		mv.addObject("notice",notice);
		mv.setViewName("admin/noticeEdit");
		return mv;
	}
	
	//공지사항 수정 => 해당 notice 삭제하고 재 등록하기
	@PostMapping("/noticeUpdate")
	@Transactional
	public ModelAndView noticeUpdate(@RequestParam Map<String,String> param, String[] cateChk,ModelAndView mv) {
		System.out.println("공지사항 수정"+param.get("noticeNo"));
		int result;
		//no로 삭제 먼저 하기
		result=service.realDelete(param.get("noticeNo"));
		
		//정상 삭제 됐으면 다시 재등록처리
		if(result>0) {
			Notice1 n;
			if(cateChk.length==1) {
				n=Notice1.builder().cate(cateChk[0]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
				result=service.insertNotice(n);
			}else {
				for(int i=0;i<cateChk.length;i++) {
					n=Notice1.builder().cate(cateChk[i]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
					result=service.insertNotice(n);
				}
			}
			mv.addObject("type", "notice");
			mv.setViewName("redirect:/admin/notice");
		}else {
			System.out.println("공지사항 수정 실패");
		}
		return mv;
	}
	
	//공지사항 재게시하기
	@GetMapping("/changeStatus")
	@Transactional
	public ModelAndView changeStatus(@RequestParam String no, ModelAndView mv) {
		int result=service.changeStatus(no);
		if(result>0) {
			mv.addObject("type", "notice");
			mv.setViewName("redirect:/admin/notice");
		}
		return mv;
	}
	

}
