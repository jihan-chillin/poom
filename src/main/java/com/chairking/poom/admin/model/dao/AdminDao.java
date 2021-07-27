package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.admin.model.vo.Notice1;
import com.chairking.poom.admin.mapper.AdminMapper;

public interface AdminDao {

	int countAllNotice(AdminMapper mapper);
	List<Map<String,Object>> allNotice(AdminMapper mapper, int cPage, int numPerpage);
	int insertNotice(Notice1 n,AdminMapper mapper);
	Map<String,Object> selectNotice(AdminMapper mapper, String no);
	int noticeDelete(AdminMapper mapper, String no);
	int realDelete(AdminMapper mapper, String no);
	int changeStatus(AdminMapper mapper, String no);
}