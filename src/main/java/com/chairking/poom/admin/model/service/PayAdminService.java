package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

public interface PayAdminService {
	List<Map<String,Object>> allPayment();
	List<Map<String,Object>> sumAllPayment();
	
}
