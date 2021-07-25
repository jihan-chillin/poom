package com.chairking.poom.payment.model.dao;

import com.chairking.poom.payment.mapper.PaymentMapper;

public interface PaymentDao {
	
	int buyItem(String memberId, String itemNo, PaymentMapper mapper);
}
