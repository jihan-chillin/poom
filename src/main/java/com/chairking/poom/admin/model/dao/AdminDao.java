package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.admin.mapper.AdminMapper;

public interface AdminDao {

	int countAllNotice(AdminMapper mapper);
	//List<Notice> allNotice(AdminMapper mapper);
	List<Map<String,Object>> allNotice(AdminMapper mapper);
}
