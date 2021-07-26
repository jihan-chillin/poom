package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

public interface BlameService {
	//신고
	List<Map<String,Object>> allBlameList(String type,int cPage, int numPerpage);
	int insertBlame(Map<String,String> map);
}
