package com.chairking.poom.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.admin.model.service.BlameService;
import com.chairking.poom.common.Pagination;

@Controller
@RequestMapping("/blame")
public class BlameController {
	@Autowired
	private BlameService service;
	
	//신고관리 메뉴 페이지
	@GetMapping()
	public ModelAndView blame(String type,ModelAndView mv,
			@RequestParam(value = "delStatus", required = false, defaultValue = "n") String delStatus,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage, //현재페이지
            @RequestParam(value = "cntPerPage", required = false, defaultValue = "5") int cntPerPage, //numPerpage
            @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {	//pageBar사이즈
		//각 테이블 위 제목
		String title=null;
		switch(type) {
			case "blame": case "1" : title="신고된 게시글 관리";break;
			case "2" : title="신고된 댓글 관리";break;
			case "3" : title="신고된 채팅 관리";break;
			case "4" : title="정지된 회원 관리";break;
		}
		//삭제여부 radio버튼
		String sql="";
		if(delStatus.equals("n")) {
			sql="del_status  ,blame_count desc";
		}else {			//삭제여부 y면
			sql="del_status desc ,blame_count desc";
		}
		//페이징처리
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		pagination.setTotalRecordCount(service.blameCount(type));
		List<Map<String,Object>> list = service.allBlameList(type, pagination,sql);
		mv.addObject("pagination",pagination);
		mv.addObject("list", list);
		mv.addObject("type", type);
		mv.addObject("blame_title", title);
		mv.addObject("delStatus", delStatus);
		mv.setViewName("admin/admin_blame");
		return mv;
	}
	
	//신고 팝업창
	@RequestMapping(value="/report",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView report(@RequestParam Map<String,String> map,ModelAndView mv) {
		//신고대상 값 보내야함
		System.out.println("blame/report"+map);
		mv.addObject("map", map);
		mv.setViewName("admin/blame_popup");
		return mv;
	}

	//신고하기=> insert 각 신고테이블 & 게시글/댓글/채팅 테이블 컬럼 count+1하기
	@PostMapping("/insertblame")
	@Transactional
	public ModelAndView insertBlame(@RequestParam Map<String,String> map, ModelAndView mv) {
		if(map.get("textarea").length()>1 && map.get("blame_reason").equals("기타")) {
			map.put("blame_reason", "기타 - "+map.get("textarea"));
		}
		System.out.println("insertblame:"+map);
		//type에따라 각 해당하는 신고테이블에 넣기
		int result=service.insertBlame(map);	
		
		//insert+update 후 
		//해당 no의 blame_count가져오고 그걸 토대로 del_status=1로 update하기
		int reply=service.hiddenCheck(map);
		mv.addObject("map",map);
		mv.setViewName("admin/blame_popup_suc");
		return mv;
	}
	
	//누적신고수 팝업
	@RequestMapping(value="/checkPop",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView checkPop(@RequestParam Map map,ModelAndView mv) {
		System.out.println("type:"+map.get("type")+" / no:"+map.get("no"));
		//type & no받기=> db연결해서 사유 가져와야함
		//총 select * 한 리스트
		List<Map<String,Object>> list=service.selectBlame(map);
		System.out.println("댓글신고팝업"+list);
		
		//각 신고개수 돈 리스트
		Map<String,Object> countMap=service.selectCountBlame(map);
		
		//기타 사유가 있다면
		List<Map<String,String>> ectMap=null;
		if(countMap.get("5")!=null) {
			ectMap=service.selectEctAll(map);
		}
		mv.addObject("type", map.get("type"));
		mv.addObject("list",list);
		mv.addObject("countMap", countMap);
		mv.addObject("ectMap", ectMap);
		mv.setViewName("admin/check_blamecount_pop");
		return mv;
	}
	
	//삭제버튼 => 실삭제 아니고 각 테이블에 del_status수정하기
	@RequestMapping(value="/delete",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView delete(String type, 
			@RequestParam (value="checkArr[]") List<String> checkArr,ModelAndView mv) {
		Map<String,Object> map= new HashMap();
		map.put("type", type);
		map.put("arr", checkArr);
		int result=service.deleteBlame(map);
		switch(type) {
			case "게시글": type="1";break;
			case "댓글" : type="2";break;
			case "채팅" : type="3";break;
			case "회원" : type="4";break;
		}
		mv.setViewName("redirect:/blame?type="+type);
		return mv;
	}
}
