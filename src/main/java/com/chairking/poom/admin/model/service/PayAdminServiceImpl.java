package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.dao.PayAdminDao;
import com.chairking.poom.common.Pagination;

@Service
public class PayAdminServiceImpl implements PayAdminService {
	@Autowired
	private PayAdminDao dao;
	
	@Autowired
	private AdminMapper mapper;

	@Override
	public List<Map<String, Object>> allPayment(Pagination pagination) {
		return dao.allPayment(mapper,pagination);
	}
	
	@Override
	public int allPaymentCount() {
		return dao.allPaymentCount(mapper);
	}

	@Override
	public List<Map<String, Object>> sumAllPayment() {
		return dao.sumAllPayment(mapper);
	}

	@Override
	public List<Map<String, String>> selectPayDetail(Map<String,String> map) {
		return dao.selectPayDetail(mapper,map);
	}
	
	
}
