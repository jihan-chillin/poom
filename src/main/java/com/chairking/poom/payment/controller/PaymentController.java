package com.chairking.poom.payment.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@PostMapping("/pay/end")
	public ModelAndView payEnd(String itemNo, String itemType, HttpSession session, ModelAndView mv) {
		String memberId=((Map<String, String>)session.getAttribute("loginMember")).get("MEMBER_ID");
		System.out.println(itemNo);
		System.out.println(memberId);
		System.out.println(itemType);
		int result=service.buyItem(memberId, itemNo);
		if(result>0) {
			result=service.changePayStatus(memberId, itemType);
		}
//		mv.addObject("msg", result!=0?"결제를 성공했습니다.":"결제를 실패하였습니다.");
//		mv.addObject("loc", "board/board_alllist");
//		mv.setViewName("common/msg");
		mv.setViewName("board/board_alllist");
		return mv;
	}

}
