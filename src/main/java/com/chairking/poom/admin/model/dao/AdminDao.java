package com.chairking.poom.admin.model.dao;

import java.util.List;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.vo.Notice;

public interface AdminDao {

	int countAllNotice(AdminMapper mapper);
	List<Notice> allNotice(AdminMapper mapper);
}
