package com.chairking.poom.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@RestController
//@RequestMapping("/")
@Slf4j
public class BoardController {

	@Autowired
	private BoardService service;
	
	//게시글 등록 페이지로 이동
	@RequestMapping(path="/board/form", method=RequestMethod.GET)

	public String boardForm(){

		return "board/board_form";
	}

	//모든 게시글 리스트 가져오는 서비스
	@GetMapping("/board/all")
	public ModelAndView selectAllBoard(ModelAndView mv,
									   @RequestParam(value="cPage", defaultValue = "1") int cPage) {

		// 게시글 조회수
		// 얘는 나중에 로그인 session값으로 들어오는 거 적용되면 할 것
		//int readcount = service.readcount();
		int numPerpage = 5;

		List<Map<String, Object>> oList = service.selectAllBoard(cPage, numPerpage);

		mv.addObject("oList", oList);
		mv.setViewName("board/board_list");
		return mv;
	}
	
	//게시글 상세 조회
	@GetMapping("/board/view")
	public ModelAndView boardView(@RequestParam String boardNo, ModelAndView mv,HttpServletRequest req) {
		System.out.println(boardNo);
		//좋아요 가져오기
		String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
		
		//태그들 가져오기
		
		mv.addObject("likeTable",likeTable);
		mv.setViewName("board/board_view");
		mv.addObject("board", service.selectBoard(boardNo));
		mv.addObject("commentList", service.selectCommentList(boardNo));
		return mv;
	}

	//메인피드등록하기(파일X, 텍스트만 가능)
	@PostMapping("/board/feedWrite")
	public ModelAndView feedWrite(@RequestParam Map param, ModelAndView mv) {

		//게시글 등록
		int result=service.insertFeed(param);

		String msg="";
		String loc="/login/main";
		if(result>0) {
			msg = "게시글 등록 완료!";
		}else {
			msg = "등록실패! 다시 시도해주세요.";
		}
		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		mv.setViewName("common/msg");

		return mv;
	}
	
	//메인피드글 불러오기
	@RequestMapping("/board/feedNew")
	public ModelAndView feedNew(@RequestParam Map param, ModelAndView mv) {
		
		System.out.println("feednew map"+param);
		//좋아요 테이블 불러오기
		String[] likeTable = service.likeTable((String)param.get("id"));
		System.out.println("feednew like"+likeTable);
		List<Map<String, Object>> feedList;
		if(param.get("loc").equals("전국")) {
			param.put("loc","");
		}
		
		if(param.get("list").equals("feedkey")) {
			String[] myTag=service.myTag(param);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("myTag",myTag);
			map.put("loc", param.get("loc"));
			feedList = service.feedKeyList(map);
			System.out.println(feedList.size());
		}else {
			feedList = service.feedList(param);
		}
		
		if(feedList!=null) {
			mv.addObject("likeTable",likeTable);
			mv.addObject("feedList",feedList);
		}else {
			mv.addObject("feedList","등록된 글이 없습니다.");
		}
		
		mv.setViewName("main/feedList");
		return mv;
	}
	
	//게시판에서 공지사항 클릭
	@RequestMapping("/board/boardNotice")
	public ModelAndView boardNotice(String no, ModelAndView mv) {
		Map<String,Object> notice = service.selectNotice(no);
		System.out.println(notice);
		mv.addObject("notice", notice);
		mv.setViewName("board/board_notice_view");
		return mv;
	}
	
	//좋아요=> +1하기
	@RequestMapping("/board/changeLike")
	public void changeLike(@RequestParam Map<String,String> map) {
		//해당 no로 board테이블에 like count 추가하고 
		//좋아요 테이블에 컬럼 추가하기
		int result=service.changeLike(map);
	}
	
	//왼쪽 게시판 이름 누르면 카테고리로 이동하기
	@RequestMapping("/board/boardList")
	public ModelAndView boardList(@RequestParam String cate, ModelAndView mv,HttpServletRequest req ) {
		int numPerpage = 5;
		//카테고리별 게시글 리스트
		List<Map<String, Object>> list = service.selectBoardList(cate,1, numPerpage);
		//좋아요 가져오기
		String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
		//공지사항 가져오기
		List<Map<String,Object>> notices=service.selectBoardNotice(cate);
		System.out.println(notices);
		mv.addObject("list", list);
		mv.addObject("likeTable",likeTable);
		mv.addObject("notice", notices);
		mv.addObject("name",list.get(0).get("CATEGORY_NAME"));
		mv.setViewName("board/board_list_list");
		return mv;
	}
}
