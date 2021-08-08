package com.chairking.poom.payment.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.chairking.poom.payment.model.service.PaymentService;

@Controller
@RequestMapping("/")
public class PaymentController {
	@Autowired
	private PaymentService service;
	
	//결제화면 연결
	@GetMapping("/pay")
	public ModelAndView pay(ModelAndView mv) {
		mv.setViewName("pay/pay");
		return mv;
	}
	
	//결제 후 데이터 저장
	@RequestMapping(value="/pay/end", method= {RequestMethod.GET, RequestMethod.POST})
	public String payEnd(String itemNo, HttpSession session) {
		String memberId=((Map<String, String>)session.getAttribute("loginMember")).get("MEMBER_ID");
		System.out.println(itemNo);
		System.out.println(memberId);
		int result=service.buyItem(memberId, itemNo);
		return "index";
	}
	
}
