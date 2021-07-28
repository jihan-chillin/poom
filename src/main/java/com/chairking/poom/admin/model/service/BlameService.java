package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import com.chairking.poom.common.Pagination;

public interface BlameService {
	//신고
	List<Map<String,Object>> allBlameList(String type,Pagination pagination);
	int blameCount(String type);
	int insertBlame(Map<String,String> map);
}
