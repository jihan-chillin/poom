package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.dao.PayAdminDao;

@Service
public class PayAdminServiceImpl implements PayAdminService {
	@Autowired
	private PayAdminDao dao;
	
	@Autowired
	private AdminMapper mapper;

	@Override
	public List<Map<String, Object>> allPayment() {
		return dao.allPayment(mapper);
	}

	@Override
	public List<Map<String, Object>> sumAllPayment() {
		return dao.sumAllPayment(mapper);
	}
	
	
}
