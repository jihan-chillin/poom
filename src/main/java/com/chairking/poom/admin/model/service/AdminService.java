package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import com.chairking.poom.admin.model.vo.Notice;

public interface AdminService {

	int countAllNotice();
	List<Map<String,Object>> allNotice(int cPage, int numPerpage);
	int insertNotice(Notice n);
	Map<String,Object> selectNotice(String no);
	int noticeDelete(String no);
	int realDelete(String no);
	int changeStatus(String no);
	
	List<Map<String,Object>> allBoardBlame(int cPage, int numPerpage);
}
