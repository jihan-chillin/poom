package com.chairking.poom.admin.controller;

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

@Controller
@RequestMapping("/blame")
public class BlameController {
	@Autowired
	private BlameService service;
	
	//신고관리 메뉴 페이지
	@GetMapping()
	public ModelAndView blame(String type,ModelAndView mv,
			@RequestParam(value="cPage", defaultValue="1") int cPage) {
		String title=null;
		switch(type) {
			case "blame": case "1" : title="신고된 게시글 관리";break;
			case "2" : title="신고된 댓글 관리";break;
			case "3" : title="신고된 채팅 관리";break;
			case "4" : title="정지된 회원 관리";break;
		}
		List<Map<String,Object>> list = service.allBlameList(type,cPage,10);
		
		mv.addObject("list", list);
		mv.addObject("type", type);
		mv.addObject("blame_title", title);
		mv.setViewName("admin/admin_blame");
		return mv;
	}
	
	//신고 팝업창
	@RequestMapping(value="/report",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView report(@RequestParam Map<String,String> map,ModelAndView mv) {
		//신고대상 값 보내야함
		System.out.println("blame/report"+map);
		mv.addObject("map", map);
		
		mv.setViewName("common/blame_popup");
		return mv;
	}

	//신고하기=> insert 각 신고테이블 & 게시글/댓글/채팅 테이블 컬럼 count+1하기
	@PostMapping("/insertblame")
	@Transactional
	public ModelAndView insertBlame(@RequestParam Map<String,String> map, ModelAndView mv) {
		
		if(map.get("textarea").length()>1) {
			map.put("blame_reason", "기타 - "+map.get("textarea"));
		}
		System.out.println("insertblame:"+map);
		//type에따라 각 해당하는 신고테이블에 넣기
		int result=service.insertBlame(map);	//서비스에 트랜젝션처리함 근데 에러뜨면 에러페이지 이동하게 해놓거나 msg로 이동하게 해서 처리하기
		System.out.println("db에 잘 들ㅇ갔니 result"+result);
		mv.addObject("map",map);
		mv.setViewName("common/blame_popup_suc");
		return mv;
		
	}
}
