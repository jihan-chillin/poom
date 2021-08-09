package com.chairking.poom.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dom4j.rule.Mode;
import com.chairking.poom.noti.controller.NotiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
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
	
//	게시글 상세 조회
	@GetMapping("/board/view")
	public ModelAndView boardView(@RequestParam String boardNo, ModelAndView mv,HttpServletRequest req) {
		System.out.println(boardNo);
		//좋아요 가져오기
		String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
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
	public ModelAndView feedNew(@RequestParam Map param,
								@RequestParam(value="cPage", defaultValue="1") int cPage, ModelAndView mv) {
		
		int numPerpage=10;
		//좋아요 테이블 불러오기
		String[] likeTable = service.likeTable((String)param.get("id"));
		//보드태그 테이블 불러오기
		List<Map<String, Object>> boardTag = service.boardTag();
		
		List<Map<String, Object>> feedList;
		String noFeed="";
		if(param.get("list").equals("feedkey")) {
			//키워드 글 조회
			String[] myTag=service.myTag(param);
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(myTag.length>0) {
				map.put("myTag",myTag);
				map.put("cPage", cPage);
				map.put("numPerpage", numPerpage);
				map.put("loc", param.get("loc"));
				feedList = service.feedKeyList(map);
				if(feedList.size()>0) {
					System.out.println("전: "+feedList.size());
					
					for(int i=1; i<feedList.size(); i++) {
						if(feedList.get(i).get("BOARD_NO").equals(feedList.get(i-1).get("BOARD_NO"))) {
							feedList.remove(i);
						}
					}
					System.out.println("후: "+feedList.size());
					mv.addObject("feedList",feedList);
				}else {
					noFeed="noFeed";
				}
			}else {
				noFeed="noTag";
			}
		}else {
			String loc="";
			if(param.get("loc").equals("전국")) {
				loc="";
			}else {
				loc = (String)param.get("loc");
			}
			
			feedList = service.feedList(loc,cPage,numPerpage);
			if(feedList.size()>0) {
				mv.addObject("feedList",feedList);
			}else {
				noFeed="noFeed";
			}
		}
		
		mv.addObject("noFeed",noFeed);
		mv.addObject("likeTable",likeTable);
		mv.addObject("boardTag",boardTag);
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
	


}
