package com.chairking.poom.login.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.google.gson.Gson;
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

	//이메일인증
	@Autowired
    public JavaMailSender emailSender;
	
	@RequestMapping("/CheckMail")
	@ResponseBody
	public void SendMail(HttpServletResponse response, @RequestParam String mail) throws Exception {
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
	 
}
