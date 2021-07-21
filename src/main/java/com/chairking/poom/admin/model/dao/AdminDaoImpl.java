package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.admin.mapper.AdminMapper;
@Repository
public class AdminDaoImpl implements AdminDao {

//	@Override
//	public List<Notice> allNotice(AdminMapper mapper) {
//		return mapper.allNotice();
//	}

	@Override
	public List<Map<String,Object>> allNotice(AdminMapper mapper) {
		return mapper.allNotice();
	}
	
	@Override
	public int countAllNotice(AdminMapper mapper) {
		return mapper.countAllNotice();
	}

	@Override
	public int insertNotice(Map map,AdminMapper mapper) {
		return mapper.insertNotice(map);
	}


}
