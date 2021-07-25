package com.chairking.poom.payment.model.dao;

import org.springframework.stereotype.Repository;

import com.chairking.poom.payment.mapper.PaymentMapper;

@Repository
public class PaymentDaoImpl implements PaymentDao {
	
	@Override
	public int buyItem(String memberId, String itemNo, PaymentMapper mapper) {
		return mapper.buyItem(memberId, itemNo);
	}
	
}
