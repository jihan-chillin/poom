package com.chairking.poom.payment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chairking.poom.payment.model.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PaymentJsonController {
	// JSON 전송용 Controller
	@Autowired
	private PaymentService service;
	@Autowired
	private PaymentController controller;
	
	//결제화면에 이용권 목록 보여줌
	@GetMapping("/pay/itemList")
	public List itemList(){
		return service.itemList();
	}
	
}
