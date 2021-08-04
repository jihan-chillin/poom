package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.common.Pagination;

public interface PayAdminDao {

	List<Map<String,Object>> allPayment(AdminMapper mapper,Pagination pagination);
	int allPaymentCount(AdminMapper mapper);
	List<Map<String,Object>> sumAllPayment(AdminMapper mapper);
	
}
