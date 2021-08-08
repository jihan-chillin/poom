package com.chairking.poom.payment.model.service;

import java.util.List;
import java.util.Map;

public interface PaymentService {
	
	List<Map<String, String>> itemList();
	
	int buyItem(String memberId, String itemNo);
	
	int changePaylevel(String memberId, String itemNo);

}
