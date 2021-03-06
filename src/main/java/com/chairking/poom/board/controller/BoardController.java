package com.chairking.poom.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	public ModelAndView boardView(@RequestParam String boardNo, 
							@RequestParam (value="cate", required=false, defaultValue="none") String cate,
							ModelAndView mv,HttpServletRequest req) {
		System.out.println("어디서 넘어왔니"+cate);
		//좋아요 가져오기
		String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
		//해시태그 가져오기
		List<String> tagList=service.boardTagList(boardNo);
		mv.addObject("tagList", tagList);
		mv.addObject("likeTable",likeTable);
		mv.setViewName("board/board_view");
		mv.addObject("board", service.selectBoard(boardNo));
		mv.addObject("commentList", service.selectCommentList(boardNo));
		mv.addObject("cate", cate);
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
								@RequestParam(value="cPage", required=false, defaultValue="1") int cPage, ModelAndView mv) {
		
		int numPerpage=10;
		if(cPage==1) {
			mv.addObject("cPage",cPage);
		}
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
				map.put("cPage", (cPage-1)*numPerpage+1);
				map.put("numPerpage", cPage*numPerpage);
				map.put("loc", param.get("loc"));
				feedList = service.feedKeyList(map);
				if(feedList.size()>0) {
					for(int i=1; i<feedList.size(); i++) {
						if(feedList.get(i).get("BOARD_NO").equals(feedList.get(i-1).get("BOARD_NO"))) {
							feedList.remove(i);
						}
					}
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
	public ModelAndView boardNotice(@RequestParam Map<String,String> map, ModelAndView mv) {
		String no=map.get("no");
		Map<String,Object> notice = service.selectNotice(no);
		System.out.println(map);
		if(map.get("cate")==null) {
			
		}else if(map.get("cate").equals("4")) {
			mv.addObject("cate", "4");
		}else if(map.get("cate").equals("all")) {
			mv.addObject("cate", "all");
		}
		mv.addObject("notice", notice);
		mv.setViewName("board/board_notice_view");
		return mv;
	}

	//전체글 ajax 페이징처리
	@RequestMapping("/board/allAjax")
	public ModelAndView allListAjax(ModelAndView mv,HttpServletRequest req,
							@RequestParam(value="cPage", required = false, defaultValue = "1") int cPage,
                           @RequestParam(value = "numPerpage", required = false, defaultValue = "5") int numPerpage,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
		
		//멤버 지역 가져오기
        Object memberloc = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_LOC");
        // 페이징처리
        Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
        // 전체 게시글 개수
        int totalData = service.allBoardCount(memberloc);
        // 전체 페이지 수 + lastindex + firstindex 등을 가져옴.
        pagination.setTotalRecordCount(totalData);
        // 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
        List<Map<String, Object>> list = service.allBoard(pagination, memberloc);

        // 좋아요 가져오기
        String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
        // 공지사항 가져오기
        List<Map<String,Object>> notices=service.selectAllBoardNotice();
        System.out.println("페이지네이션"+pagination);
        System.out.println("ajax페이지"+list);
        //태그 가져오기
  		List<Map<String,String>> tagList=service.selectAllBoardTag();
  		mv.addObject("tagList", tagList);
        mv.addObject("list", list);
        mv.addObject("likeTable", likeTable);
        mv.addObject("notices", notices);
        mv.addObject("pagination", pagination);
        mv.setViewName("/board/board_allist_ajax");
		return mv;
	}
	
	//카테고리별 ajax 처리
	@GetMapping("board/cateAjax")
    public ModelAndView cateListAjax(ModelAndView mv, HttpServletRequest req,
                                        @RequestParam(value = "cate") String cate,
                                        @RequestParam(value="cPage", defaultValue = "1") int cPage,
                                        @RequestParam(value = "numPerpage", required = false, defaultValue = "5") int numPerpage,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize){

        //멤버 지역 가져오기
        Object memberloc = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_LOC");

       System.out.println("파라미터 카테고리 : " + cate + "/ cate의 형 " + cate.getClass().getName()) ;
        // 페이징처리
        Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
        // 전체 게시글 개수
        int totalData = service.allcateBoardCount(cate, memberloc);
        // 전체 페이지 수 + lastindex + firstindex 등을 가져옴.
        pagination.setTotalRecordCount(totalData);
        // 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
        List<Map<String, Object>> list = service.allCateBoard(pagination, cate, memberloc);
        // 좋아요 가져오기
        String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
        // 카테고리 이름 가져오기
        Map<String, Object> cateName = service.selectCateName(cate);
        Object cName = cateName.get("CATEGORY_NAME");

        // 공지사항 가져와보기
        List<Map<String, Object>> notices = service.selectAllCateNotice(cate);
      //태그 가져오기
  		List<Map<String,String>> tagList=service.selectAllBoardTag();
  		System.out.println("전체글보드리스트"+list);
		System.out.println("페이지"+pagination);
  		mv.addObject("tagList", tagList);
        mv.addObject("cate", cate);
        mv.addObject("cName", cName);
        mv.addObject("list", list);
        mv.addObject("notices", notices);
        mv.addObject("pagination", pagination);
        mv.addObject("likeTable", likeTable);
        mv.setViewName("/board/board_cate_ajax");
        return mv;
    }

	//모든 게시글 리스트 가져오는 서비스
	@GetMapping("/board/all")
	public ModelAndView selectAllBoard(ModelAndView mv, HttpServletRequest req, @RequestParam (value="bCondition", defaultValue = "") String bCondition,
									   @RequestParam(value="cPage", defaultValue = "1") int cPage,
									   @RequestParam(value = "numPerpage", required = false, defaultValue = "5") int numPerpage,
									   @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize){

		//멤버 지역 가져오기
		Object memberloc = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_LOC");
		// 페이징처리
		Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
		// 전체 게시글 개수
		int totalData = service.allBoardCount(memberloc);
		// 전체 페이지 수 + lastindex + firstindex 등을 가져옴.
		List<Map<String, Object>> list;
		Object condition = "WHERE 1=1";
		if (!bCondition.equals("")) {


			condition += (" AND (member_id like '%" + bCondition + "'");
			condition += (" OR board_title like '%" + bCondition + "%'");
			condition += (" OR board_content like '%" + bCondition + "%')");
			totalData = service.searchBoardCount(condition);
			pagination.setTotalRecordCount(totalData);
			list = service.searchBoardList(pagination, condition);
		} else {
			totalData = service.allBoardCount(memberloc);
			pagination.setTotalRecordCount(totalData);
			// 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
			list = service.allBoard(pagination, memberloc);
		}
		//------------------------------------------------------------------------------------------
		// 좋아요 가져오기
		String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
		// 공지사항 가져오기
		List<Map<String,Object>> notices=service.selectAllBoardNotice();
		//태그 가져오기
		List<Map<String,String>> tagList=service.selectAllBoardTag();
		System.out.println("전체글보드리스트"+list);
		System.out.println("페이지"+pagination);
		mv.addObject("bCondition", bCondition);
		mv.addObject("list", list);
		mv.addObject("likeTable", likeTable);
		mv.addObject("notices", notices);
		mv.addObject("pagination", pagination);
		mv.addObject("cate","all");
		mv.addObject("tagList", tagList);
		mv.setViewName("board/board_alllist");
		return mv;
	}

	@GetMapping("board/cateList")
	public ModelAndView selectCateBoard(ModelAndView mv, HttpServletRequest req,
										@RequestParam(value = "cate") String cate,
										@RequestParam(value="cPage", defaultValue = "1") int cPage,
										@RequestParam(value = "numPerpage", required = false, defaultValue = "5") int numPerpage,
										@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize){

		//멤버 지역 가져오기
		Object memberloc = ((Map) req.getSession().getAttribute("loginMember")).get("MEMBER_LOC");

		System.out.println("파라미터 카테고리 : " + cate + "/ cate의 형 " + cate.getClass().getName()) ;
		// 페이징처리
		Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
		// 전체 게시글 개수
		int totalData = service.allcateBoardCount(cate, memberloc);
		// 전체 페이지 수 + lastindex + firstindex 등을 가져옴.
		pagination.setTotalRecordCount(totalData);
		// 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
		List<Map<String, Object>> list = service.allCateBoard(pagination, cate, memberloc);

		// 카테고리 이름 가져오기
		Map<String, Object> cateName = service.selectCateName(cate);
		Object cName = cateName.get("CATEGORY_NAME");
		// 좋아요 가져오기
		String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
		// 공지사항 가져와보기
		List<Map<String, Object>> notices = service.selectAllCateNotice(cate);
		//태그 가져오기
		List<Map<String,String>> tagList=service.selectAllBoardTag();
		mv.addObject("tagList", tagList);
		mv.addObject("cate", cate);
		mv.addObject("likeTable", likeTable);
		mv.addObject("cName", cName);
		mv.addObject("list", list);
		mv.addObject("notices", notices);
		mv.addObject("pagination", pagination);
		mv.setViewName("/board/board_cate_list");
		return mv;
	}
	
	//이용권 구매시 모든 지역 보여주기
	//특정 지역의 게시글 보여주기
	@RequestMapping("/board/loc/all")
	public ModelAndView allLocList(ModelAndView mv,HttpServletRequest req,
							@RequestParam(value="loc") String loc,
								   @RequestParam (value="bCondition", defaultValue = "") String bCondition,
								   @RequestParam(value="cPage", required = false, defaultValue = "1") int cPage,
                           @RequestParam(value = "numPerpage", required = false, defaultValue = "5") int numPerpage,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
		System.out.println(loc);
        // 페이징처리
        Pagination pagination = new Pagination(cPage, numPerpage, pageSize);

        // 전체 게시글 개수(선택 지역에 따라 달라짐)
		int totalData = loc.equals("전국")?service.allLocBoardCount():service.allBoardCount(loc);
		
		System.out.println("totalData"+totalData);

		List<Map<String, Object>> list;
		Object condition = "WHERE 1=1";
		if (!bCondition.equals("")) {


			condition += (" AND (member_id like '%" + bCondition + "'");
			condition += (" OR board_title like '%" + bCondition + "%'");
			condition += (" OR board_content like '%" + bCondition + "%')");
			totalData = service.searchlocBoardCount(condition);
			pagination.setTotalRecordCount(totalData);
			list = service.searchlocBoardList(pagination, condition);
		} else {
			totalData = service.allBoardCount(loc);
			pagination.setTotalRecordCount(totalData);
			// 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
			list = loc.equals("전국")?service.allLocBoard(pagination):service.allBoard(pagination, loc);

		}

        // 좋아요 가져오기
        String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
        // 공지사항 가져오기
        List<Map<String,Object>> notices=service.selectAllBoardNotice();
        System.out.println("페이지네이션"+pagination);
        System.out.println("전체글보드리스트"+list);
      //태그 가져오기
  		List<Map<String,String>> tagList=service.selectAllBoardTag();
  		
		System.out.println("전체글보드리스트"+list);
		System.out.println("페이지"+pagination);
		
		mv.addObject("list", list);
		mv.addObject("likeTable", likeTable);
		mv.addObject("notices", notices);
		mv.addObject("pagination", pagination);
		mv.addObject("cate","all");
		mv.addObject("tagList", tagList);
		mv.addObject("selectedLoc", loc);
		mv.setViewName("board/board_alllist");
		return mv;
	}
	
	//이용권 구매자 카테고리별 게시글 조회
	@RequestMapping("/board/loc/cate")
	public ModelAndView allLocCate(ModelAndView mv, HttpServletRequest req,
		@RequestParam(value = "loc") Object loc,
		@RequestParam(value = "cate") String cate,
		@RequestParam(value="cPage", defaultValue = "1") int cPage,
		@RequestParam(value = "numPerpage", required = false, defaultValue = "5") int numPerpage,
		@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize){

		System.out.println("파라미터 카테고리 : " + cate + "/ cate의 형 " + cate.getClass().getName()) ;
		// 페이징처리
		Pagination pagination = new Pagination(cPage, numPerpage, pageSize);
		// 전체 게시글 개수
		int totalData = loc.equals("전국")?service.allcateLocBoardCount(cate):service.allcateBoardCount(cate, loc);
//		int totalData = service.allcateBoardCount(cate, memberloc);
		// 전체 페이지 수 + lastindex + firstindex 등을 가져옴.
		pagination.setTotalRecordCount(totalData);
		// 전체 게시글 첫글 ~ 마지막글 ( 전체 게시글 개수를 알기에 )
		List<Map<String, Object>> list = loc.equals("전국")?service.allCateLocBoard(pagination, cate):service.allCateBoard(pagination, cate, loc);

		// 카테고리 이름 가져오기
		Map<String, Object> cateName = service.selectCateName(cate);
		Object cName = cateName.get("CATEGORY_NAME");
		// 좋아요 가져오기
		String[] likeTable = service.likeTable((String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
		// 공지사항 가져와보기
		List<Map<String, Object>> notices = service.selectAllCateNotice(cate);
		//태그 가져오기
		List<Map<String,String>> tagList=service.selectAllBoardTag();
		mv.addObject("tagList", tagList);
		mv.addObject("cate", cate);
		mv.addObject("likeTable", likeTable);
		mv.addObject("cName", cName);
		mv.addObject("list", list);
		mv.addObject("notices", notices);
		mv.addObject("pagination", pagination);
		mv.setViewName("/board/board_cate_ajax");
		
		return mv;
	}
	
	// 게시글 수정하는 곳으로 이동
	@GetMapping("board/modi")
	public ModelAndView boardmodi(HttpServletRequest req, ModelAndView mv,
			@RequestParam(value ="boardNo") String boardNo){

		// 제목, 카테고리, 내용 가져오기
		Map board = service.selectBoard(boardNo);


		//태그 가져오기
		List<Map<String,String>> tagList=service.selectAllBoardTag();

		mv.addObject("tagList",tagList);
		mv.addObject("board", board);
		mv.setViewName("/board/board_modi");
		return mv;
	}


}
