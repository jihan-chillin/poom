package com.chairking.poom.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.admin.model.service.AdminService;
import com.chairking.poom.admin.model.vo.Notice;
import com.chairking.poom.common.Pagination;

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
	public ModelAndView notice(String type,ModelAndView mv, 
						@RequestParam(value="cPage", defaultValue="1") int cPage) {
		int totalData =service.countAllNotice();
		int numPerpage=5;
		//구글페이징처리해보기
		Pagination paging= new Pagination(totalData,cPage);
		int startIndex=paging.getStartIndex();
		int pageSize=paging.getpageBarSize();
		
		List<Map<String,Object>> list = service.allNotice(startIndex,10);
		
		//mv.addObject("pagebar", PageFactory.getPageBar(totalData, cPage, numPerpage, "notice"));
		
//		int totalPage=(int)Math.ceil((double)totalData/numPerpage);
//		int pageBarSize=5;
//		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
//		int pageEnd=pageNo+pageBarSize-1;
//		mv.addObject("cPage",cPage);
//		mv.addObject("pageNo",pageNo);
//		mv.addObject("totalPage",totalPage);
//		mv.addObject("pageEnd",pageEnd);
		
		mv.addObject("list", list);
//		mv.addObject("pagebar",paging);
//		mv.addObject("totalData", totalData);
		mv.setViewName("admin/admin_notice");
		return mv;
	}
	
	//신고관리 메뉴 페이지
	@GetMapping("/blame")
	public ModelAndView blame(String type,ModelAndView mv,
			@RequestParam(value="cPage", defaultValue="1") int cPage) {
		List<Map<String,Object>> list = null;
		String title=null;
		switch(type) {
			case "blame": case "1" : list = service.allBoardBlame(cPage,10); title="신고된 게시글 관리";break;
			case "2" : list = service.allBoardBlame(cPage,10); title="신고된 댓글 관리";break;
			case "3" : list = service.allBoardBlame(cPage,10); title="신고된 채팅 관리";break;
			case "4" : list = service.allBoardBlame(cPage,10); title="정지된 회원 관리";break;
		}
		
		mv.addObject("list", list);
		mv.addObject("type", type);
		System.out.println("type : "+type);
		mv.addObject("blame_title", title);
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
	@Transactional
	public ModelAndView noticeWrite(@RequestParam Map<String,String> param, String[] cateChk,ModelAndView mv) {
		int result;
		Notice n;
		if(cateChk.length==1) {
			n=Notice.builder().cate(cateChk[0]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
			result=service.insertNotice(n);
		}else {
			for(int i=0;i<cateChk.length;i++) {
				n=Notice.builder().cate(cateChk[i]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
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
			Notice n;
			if(cateChk.length==1) {
				n=Notice.builder().cate(cateChk[0]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
				result=service.insertNotice(n);
			}else {
				for(int i=0;i<cateChk.length;i++) {
					n=Notice.builder().cate(cateChk[i]).noticeTitle(param.get("noticeTitle")).noticeContent(param.get("noticeContent")).build();
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
