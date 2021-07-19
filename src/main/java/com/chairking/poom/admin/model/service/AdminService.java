package com.chairking.poom.admin.model.service;

import java.util.List;

import com.chairking.poom.admin.model.vo.Notice;

public interface AdminService {

	int countAllNotice();
	List<Notice> allNotice();
}
