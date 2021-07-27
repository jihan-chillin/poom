package com.chairking.poom.admin.model.service;

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
}
