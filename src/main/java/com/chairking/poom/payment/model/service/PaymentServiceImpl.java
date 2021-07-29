package com.chairking.poom.payment.model.service;

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
	
	@Override
	public int buyItem(String memberId, String itemNo) {
		return dao.buyItem(memberId, itemNo, mapper);
	}
	
}
