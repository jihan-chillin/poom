package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import com.chairking.poom.common.Pagination;

public interface BlameService {
	//신고
	List<Map<String,Object>> allBlameList(String type,Pagination pagination, String delStatus);
	int blameCount(String type);
	int insertBlame(Map<String,String> map);
	int hiddenCheck(Map<String,String> map);
	List<Map<String,Object>> selectBlame(Map<String,Object> map);
	Map<String,Object> selectCountBlame(Map<String,Object> map);
	List<Map<String,String>> selectEctAll(Map<String,Object> map);
	int deleteBlame(Map<String,Object> map);
}
