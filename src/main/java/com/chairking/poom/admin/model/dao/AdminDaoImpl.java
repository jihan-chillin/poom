package com.chairking.poom.admin.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.vo.Notice;
@Repository
public class AdminDaoImpl implements AdminDao {

	@Override
	public List<Notice> allNotice(AdminMapper mapper) {
		return mapper.allNotice();
	}

	@Override
	public int countAllNotice(AdminMapper mapper) {
		return mapper.countAllNotice();
	}

}
