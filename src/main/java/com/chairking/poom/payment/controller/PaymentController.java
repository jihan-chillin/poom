package com.chairking.poom.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PaymentController {
	@GetMapping("/pay")
	public String pay() {
		return "pay/pay";
	}
}
