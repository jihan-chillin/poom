package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.admin.mapper.AdminMapper;

public interface BlameDao {
	List<Map<String,Object>> allBoardBlame(AdminMapper mapper, int cPage, int numPerpage);
	int insertBoardBlame(AdminMapper mapper, Map<String,String> map);
	int updateBrdBlameCount(AdminMapper mapper, String no);
	int insertCommentsBlame(AdminMapper mapper, Map<String,String> map);
	int updateCommentsBlameCount(AdminMapper mapper, String no);
	int insertChatBlame(AdminMapper mapper, Map<String,String> map);
	int updateChatBlameCount(AdminMapper mapper, String no);
}
