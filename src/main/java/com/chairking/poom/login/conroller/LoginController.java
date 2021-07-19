package com.chairking.poom.login.conroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {

	//로그인 버튼 클릭시 메인화면으로 이동
	@GetMapping("/login")
	public String logIn() {
		return "main/main";
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
}
