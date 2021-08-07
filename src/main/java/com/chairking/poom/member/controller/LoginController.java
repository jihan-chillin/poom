package com.chairking.poom.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chairking.poom.hashTag.controller.TagController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.member.model.service.LoginService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/login")
@SessionAttributes("loginMember")
@Slf4j
public class LoginController {

	@Autowired
	TagController tagController;

	@Autowired	
	LoginService service;
	
	//암호화
	@Autowired
	PasswordEncoder pwEncoder;

	@GetMapping("/main")
	public ModelAndView logIn(ModelAndView mv, HttpServletRequest req) {
		mv.setViewName("main/main");
		mv.addObject("loginId",(String)((Map)req.getSession().getAttribute("loginMember")).get("MEMBER_ID"));
		return mv;
	}

	//회원가입 클릭시 이용약관화면으로 이동
	@GetMapping("/termsofservice")
	public String termsOfService() {
		return "login/termsOfService";
	}

	//이용약관 선택 후 확인클릭시 회원가입화면으로 이동
	@GetMapping("/signup")
	public String signUp() {
		return "login/signup";
	}

	//ID찾기, PW찾기 이동
	@GetMapping("/userfind")
	public String userFind(@RequestParam String type, Model model) {
		model.addAttribute("type",type);
		return "login/userfind";
	}

	//이메일인증
	@Autowired
    public JavaMailSender emailSender;

	@RequestMapping("/CheckMail")
	@ResponseBody
	public void sendMail(HttpServletResponse response, @RequestParam String mail) throws Exception {
		Gson gson = new Gson();

		Map<String, Object> data = new HashMap<String,Object>();
		Random random = new Random();
		String key = "";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mail); // 스크립트에서 보낸 메일을 받을 사용자 이메일 주소
		// 입력 키를 위한 코드
		for (int i = 0; i < 3; i++) {
			int index = random.nextInt(25) + 65; // A~Z까지 랜덤 알파벳 생성
			key += (char) index;
		}
		int numIndex = random.nextInt(8999) + 1000; // 4자리 정수를 생성
		key += numIndex;
		message.setSubject("인증번호 입력을 위한 메일 전송");
		message.setText("인증 번호 : " + key);
		emailSender.send(message);
		data.put("key", key);

		response.getWriter().print(gson.toJson(data));
	}
	
	//아이디 중복확인
	@GetMapping("/duplCheck")
	public ModelAndView duplCheck(@RequestParam Map check, ModelAndView mv) {
		
		String type=(String)check.get("type");
		String duplsql="";
		if(type.equals("id")) {
			String id = (String)check.get("check");
			duplsql="MEMBER_ID=\'"+id+"\'";
			mv.addObject("check",id);
		}else {
			String nickname = (String)check.get("check");
			duplsql="MEMBER_NICKNAME=\'"+nickname+"\'";
			mv.addObject("check",nickname);
		}
		
		Map<String,Object> m = service.duplCheck(duplsql);

		
		mv.addObject("m",m);
		mv.addObject("type",type);
		mv.setViewName("login/duplCheck");
		return mv;
	}
	
	//회원가입
	@PostMapping("/memberEnroll")
	public ModelAndView memberEnroll(@RequestParam Map m, String[] keyword, ModelAndView mv) {
		//비밀번호 암호화
		m.put("memberPw", pwEncoder.encode((String)m.get("memberPw")));
		
		int result;
		int result2;
		if(keyword != null) {
			//회원가입(관심키워드O)
			result = service.insertMember(m);
			
			Map memberTag = new HashMap();
			for(int i=0;i<keyword.length;i++) {
				memberTag.put("id", (String)m.get("memberId"));
				memberTag.put("keyword",keyword[i]);
				result2 = service.inesrtMemberKeyword(memberTag);
				// 테그 테이블에 회원가입시 입력한 태그들 추가. by 희웅
				//tagController.insertTag(memberTag);
				mv.addObject("msg",result>0&&result2>0?"회원가입성공":"회원가입실패, 다시 시도해주세요.");
			}
		}else {
			//회원가입(관심키워드X)
			result = service.insertMember(m);
			mv.addObject("msg",result>0?"회원가입성공":"회원가입실패, 다시 시도해주세요.");
		}
		
		mv.addObject("loc","/");
		mv.setViewName("common/msg");
		return mv;
	}

	//로그인 
	@PostMapping("/memberLogin")
	public ModelAndView memberLogin(@RequestParam Map param, ModelAndView mv) {
		
		Map<String,Object> m = service.selectMember(param);
		
		if(!m.containsKey("INTRO")) {
    		m.put("INTRO", null);
    	}
    	if(!m.containsKey("MEMBER_IMG")) {
    		m.put("MEMBER_IMG", "poom_profile.jpg");
    	}
    	
		String msg="로그인 실패! 다시 시도해주세요.";
		String loc="/";
		if(m!=null && param.get("id").equals("admin") && pwEncoder.matches((String)param.get("pw"), (String)m.get("MEMBER_PW"))) {
			mv.addObject("loginMember",m);
			msg="poom 관리자님! 관리자페이지에 오신걸 환영합니다!";
			loc="/admin";
		}else if(m!=null && pwEncoder.matches((String)param.get("pw"), (String)m.get("MEMBER_PW"))) {
			mv.addObject("loginMember",m);
			msg="로그인 성공! "+m.get("MEMBER_NAME")+"님, poom에 오신걸 환영합니다!";
			loc="/login/main";
		}
		
		mv.addObject("m",m);
		mv.addObject("msg",msg);
		mv.addObject("loc",loc);
		mv.setViewName("common/msg");
		return mv;
	}
	
	//로그아웃
	@GetMapping("/logOut")
	public String logout(HttpSession session, SessionStatus ss) {
		if(session!=null) session.invalidate();
		if(!ss.isComplete()) {
			ss.setComplete();
		}
		
		return "redirect:/"; 
	}
	
	//ID 찾기
	@PostMapping("/idFind")
	public ModelAndView idFind(@RequestParam Map param, ModelAndView mv) {
		String id = service.idFind(param);

		if(id!=null) {
			//id 마지막 3자리 ***표시하기
			id = (id.replaceAll(id.substring(id.length()-3,id.length()),"***"));
		}
		
		mv.addObject("type","id");
		mv.addObject("m",id);
		mv.setViewName("login/findresult");
		
		return mv;
	}

	//PW 찾기
	@PostMapping("/pwFind")
	public ModelAndView pwFind(@RequestParam Map param, ModelAndView mv) {
		Map<String, Object> m = service.pwFind(param);
		
		mv.addObject("type","pw");
		mv.addObject("m",m);
		mv.setViewName("login/findresult");
		
		return mv;
	}
	
	//PW찾기 -> 새 비밀번호로 변경
	@PostMapping("/updatePw")
	public ModelAndView updatePw(@RequestParam Map param, ModelAndView mv) {
		//비밀번호 암호화
		param.put("memberPw", pwEncoder.encode((String)param.get("memberPw")));
		
		int result = service.updatePw(param);
		
		if(result>0) {
			mv.addObject("goIndex","goIndex");
			mv.addObject("msg","비밀번호 변경을 완료하였습니다.");
			mv.setViewName("common/msg");
		}
		
		return mv;
	}
}
