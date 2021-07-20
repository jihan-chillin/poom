package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

public interface AdminService {

	int countAllNotice();
	//List<Notice> allNotice();
	List<Map<String,Object>> allNotice();
}
