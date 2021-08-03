package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import com.chairking.poom.common.Pagination;

public interface PayAdminService {
	List<Map<String,Object>> allPayment(Pagination pagination);
	int allPaymentCount();
	List<Map<String,Object>> sumAllPayment();
	
}
