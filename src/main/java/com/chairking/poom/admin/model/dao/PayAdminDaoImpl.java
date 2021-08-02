package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.common.Pagination;

@Repository
public class PayAdminDaoImpl implements PayAdminDao {

	@Override
	public List<Map<String, Object>> allPayment(AdminMapper mapper, Pagination pagination) {
		return mapper.allPayment();
	}

	
	@Override
	public int allPaymentCount(AdminMapper mapper) {
		return mapper.allPaymentCount();
	}


	@Override
	public List<Map<String, Object>> sumAllPayment(AdminMapper mapper) {
		return mapper.sumAllPayment();
	}
	
}
