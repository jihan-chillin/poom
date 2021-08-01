package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.admin.mapper.AdminMapper;

@Repository
public class PayAdminDaoImpl implements PayAdminDao {

	@Override
	public List<Map<String, Object>> allPayment(AdminMapper mapper) {
		return mapper.allPayment();
	}

	@Override
	public List<Map<String, Object>> sumAllPayment(AdminMapper mapper) {
		return mapper.sumAllPayment();
	}
	
}
