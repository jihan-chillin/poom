package com.chairking.poom.payment.model.service;

import java.util.List;
import java.util.Map;

public interface PaymentService {
	
	List<Map<String, String>> itemList();
	
	int buyItem(String memberId, String itemNo);

	int changePayStatus(String memberId, String itemType);
	
	int checkExpireDate();
	
}
