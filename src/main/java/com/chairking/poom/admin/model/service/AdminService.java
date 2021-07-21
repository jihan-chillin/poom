package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import com.chairking.poom.admin.model.vo.Notice;

public interface AdminService {

	int countAllNotice();
	//List<Notice> allNotice();
	List<Map<String,Object>> allNotice(int cPage, int numPerpage);
	int insertNotice(Notice n);
}
