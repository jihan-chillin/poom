package com.chairking.poom.payment.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.payment.mapper.PaymentMapper;

@Repository
public class PaymentDaoImpl implements PaymentDao {

	@Override
	public List<Map<String, String>> itemList(PaymentMapper mapper){
		return mapper.itemList();
	}
	
	@Override
	public int buyItem(String memberId, String itemNo, PaymentMapper mapper) {
		return mapper.buyItem(memberId, itemNo);
	}

	@Override
	public int changePayStatus(String memberId, String itemType, PaymentMapper mapper) {
		return mapper.changePayStatus(memberId, itemType);
	}
	
	
}
