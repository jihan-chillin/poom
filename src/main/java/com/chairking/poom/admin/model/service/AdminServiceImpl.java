package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.dao.AdminDao;
import com.chairking.poom.admin.model.vo.Notice;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper mapper;
	
	@Autowired
	private AdminDao dao;
	
	@Override
	public int countAllNotice() {
		return dao.countAllNotice(mapper);
	}
//	@Override
//	public List<Notice> allNotice() {
//		return dao.allNotice(mapper);
//	}

	@Override
	public List<Map<String,Object>> allNotice() {
		return dao.allNotice(mapper);
	}
	
	@Override
	public int insertNotice(Map map) {
		return dao.insertNotice(map,mapper);
	}

}
