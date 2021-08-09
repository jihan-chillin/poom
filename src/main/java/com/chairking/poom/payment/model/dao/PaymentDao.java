package com.chairking.poom.payment.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.payment.mapper.PaymentMapper;

public interface PaymentDao {
	
	List<Map<String, String>> itemList(PaymentMapper mapper);
	
	int buyItem(String memberId, String itemNo, PaymentMapper mapper);
	
	int changePayStatus(String memberId, String itemType, PaymentMapper mapper);
	
}
