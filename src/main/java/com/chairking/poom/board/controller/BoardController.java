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
import com.chairking.poom.common.Pagination;

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
	public ModelAndView feedNew(@RequestParam Map param, ModelAndView mv) {
		

		//좋아요 테이블 불러오기
		String[] likeTable = service.likeTable((String)param.get("id"));
		//보드태그 테이블 불러오기
		List<Map<String, Object>> boardTag = service.boardTag();
		
		List<Map<String, Object>> feedList;
		if(param.get("loc").equals("전국")) {
			param.put("loc","");
		}
		
		String noFeed="";
		if(param.get("list").equals("feedkey")) {
			//키워드 글 조회
			String[] myTag=service.myTag(param);
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(myTag.length>0) {
				map.put("myTag",myTag);
				map.put("loc", param.get("loc"));
				feedList = service.feedKeyList(map);
				if(feedList.size()>0) {
					mv.addObject("feedList",feedList);
				}else {
					noFeed="noFeed";
				}
			}else {
				noFeed="noTag";
			}
		}else {
			feedList = service.feedList(param);
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
	
	//좋아요=> +1하기
	@RequestMapping("/board/changeLike")
	public ModelAndView changeLike(@RequestParam Map<String,String> map,ModelAndView mv) {
		//해당 no로 board테이블에 like count 추가하고 
		//좋아요 테이블에 컬럼 추가하기
		int result=service.changeLike(map);
		//좋아요 리스트 다시 가져오기
		String[] likeTable = service.likeTable(map.get("id"));
		
		//메인에서 좋아요 눌렀을때
		if(map.get("type")==null) {
			//추가 후 list다시 불러오기
			List<Map<String, Object>> feedList = service.feedList(map);
			if(feedList!=null) {
				mv.addObject("likeTable",likeTable);
				mv.addObject("feedList",feedList);
			}else {
				mv.addObject("feedList","등록된 글이 없습니다.");
			}
			mv.setViewName("main/feedList");
		}else {			//게시글에서 좋아요 눌렀을때
			mv.addObject("likeTable",likeTable);
			mv.setViewName("board/board_view");
			mv.addObject("board", service.selectBoard(map.get("no")));
			mv.addObject("commentList", service.selectCommentList(map.get("no")));
		}
		return mv;
	}
	
	//전체글 ajax 페이징처리
	@RequestMapping("/board/allAjax")
	public ModelAndView allListAjax(ModelAndView mv,HttpServletRequest req,
							@RequestParam(value="cPage", required = false,defaultValue = "1") int cPage,
                           @RequestParam(value = "numPerpage", required = false, defaultValue = "5") int numPerpage,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
		
		System.out.println("ajax cPage"+cPage);
		//멤버 지역 가져오기
        Object memberloc = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_LOC");
        // 페이징처리
        Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
        // 전체 게시글 개수
        int totalData = service.allBoardCount();
        // 전체 페이지 수 + lastindex + firstindex 등을 가져옴.
        pagination.setTotalRecordCount(totalData);
        // 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
        List<Map<String, Object>> list = service.allBoard(pagination, memberloc);

        // 좋아요 가져오기
        String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
        // 공지사항 가져오기
        List<Map<String,Object>> notices=service.selectAllBoardNotice();
        System.out.println("페이지네이션"+pagination);
        System.out.println("전체글보드리스트"+list);
        mv.addObject("list", list);
        mv.addObject("likeTable", likeTable);
        mv.addObject("notices", notices);
        mv.addObject("pagination", pagination);
        mv.setViewName("/board/board_allist_ajax");
		return mv;
	}

}
