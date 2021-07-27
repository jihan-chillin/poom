package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.admin.mapper.AdminMapper;

@Repository
public class BlameDaoImpl implements BlameDao {
	
	@Override
	public List<Map<String, Object>> allBoardBlame(AdminMapper mapper,int cPage, int numPerpage) {
		return mapper.allBoardBlame(cPage,numPerpage);
	}
	
	@Override
	public List<Map<String, Object>> allCommentsBlame(AdminMapper mapper,int cPage, int numPerpage) {
		return mapper.allCommentsBlame(cPage,numPerpage);
	}

	@Override
	public List<Map<String, Object>> allChatBlame(AdminMapper mapper, int cPage, int numPerpage) {
		return mapper.allChatBlame(cPage,numPerpage);
	}

	@Override
	public List<Map<String, Object>> allMemberBlame(AdminMapper mapper, int cPage, int numPerpage) {
		return mapper.allMemberBlame(cPage,numPerpage);
	}
	
	@Override
	public int insertBoardBlame(AdminMapper mapper, Map<String, String> map) {
		return mapper.insertBoardBlame(map);
	}
	
	@Override
	public int updateBrdBlameCount(AdminMapper mapper, String no) {
		return mapper.updateBrdBlameCount(no);
	}

	@Override
	public int insertCommentsBlame(AdminMapper mapper, Map<String, String> map) {
		return mapper.insertCommentsBlame(map);
	}
	
	@Override
	public int updateCommentsBlameCount(AdminMapper mapper, String no) {
		return mapper.updateCommentsBlameCount(no);
	}

	@Override
	public int insertChatBlame(AdminMapper mapper, Map<String, String> map) {
		return mapper.insertChatBlame(map);
	}

	@Override
	public int updateChatBlameCount(AdminMapper mapper, String no) {
		return mapper.updateChatBlameCount(no);
	}

	
}
