package com.chairking.poom.payment.controller;

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
	
	@GetMapping("/pay")
	public String pay() {
		return "pay/pay";
	}
	
	@RequestMapping(value="/pay/payment",method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView payment(String memberId, String itemNo, ModelAndView mv) {
//		int result=service.buyItem(memberId, itemNo);
		System.out.println("===========결제=========");
		System.out.println("memberId : "+memberId);
		System.out.println("itemNo : "+itemNo);
		System.out.println("======================");
		return mv;
	}
	
	
}
