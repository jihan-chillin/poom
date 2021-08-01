package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.admin.mapper.AdminMapper;

public interface PayAdminDao {

	List<Map<String,Object>> allPayment(AdminMapper mapper);
	List<Map<String,Object>> sumAllPayment(AdminMapper mapper);
	
}
