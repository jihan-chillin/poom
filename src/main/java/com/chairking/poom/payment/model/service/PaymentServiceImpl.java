package com.chairking.poom.payment.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chairking.poom.payment.mapper.PaymentMapper;
import com.chairking.poom.payment.model.dao.PaymentDao;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentDao dao;
	
	@Autowired
	private PaymentMapper mapper;
	
	//이용권 목록 가져오는 서비스
	@Override
	public List<Map<String, String>> itemList(){
		return dao.itemList(mapper);
	}
	
	//이용권 구매 서비스
	@Override
	public int buyItem(String memberId, String itemNo) {
		return dao.buyItem(memberId, itemNo, mapper);
	}
	
	//이용권 결제상태 확인하는 서비스
	@Override
	public int changePayStatus(String memberId, String itemType) {
		return dao.changePayStatus(memberId, itemType, mapper);
	}

	
}
