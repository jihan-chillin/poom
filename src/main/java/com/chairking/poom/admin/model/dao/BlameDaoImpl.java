package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.common.Pagination;

@Repository
public class BlameDaoImpl implements BlameDao {
	
	@Override
	public List<Map<String, Object>> allBoardBlame(AdminMapper mapper,Pagination pagination) {
		return mapper.allBoardBlame(pagination);
	}
	
	@Override
	public List<Map<String, Object>> allCommentsBlame(AdminMapper mapper,Pagination pagination) {
		return mapper.allCommentsBlame(pagination);
	}

	@Override
	public List<Map<String, Object>> allChatBlame(AdminMapper mapper, Pagination pagination) {
		return mapper.allChatBlame(pagination);
	}

	@Override
	public List<Map<String, Object>> allMemberBlame(AdminMapper mapper, Pagination pagination) {
		return mapper.allMemberBlame(pagination);
	}
	
	@Override
	public int allBoardBlameCount(AdminMapper mapper) {
		return mapper.allBoardBlameCount();
	}

	@Override
	public int allCommentsBlameCount(AdminMapper mapper) {
		return mapper.allCommentsBlameCount();
	}

	@Override
	public int allChatBlameCount(AdminMapper mapper) {
		return mapper.allChatBlameCount();
	}

	@Override
	public int allMemberBlameCount(AdminMapper mapper) {
		return mapper.allMemberBlameCount();
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

	@Override
	public List<Map<String, Object>> selectBoardBlame(AdminMapper mapper, String no) {
		return mapper.selectBoardBlame(no);
	}

	@Override
	public List<Map<String, Object>> selectCommentsBlame(AdminMapper mapper, String no) {
		return mapper.selectCommentsBlame(no);
	}

	@Override
	public List<Map<String, Object>> selectChatBlame(AdminMapper mapper, String no) {
		return mapper.selectChatBlame(no);
	}

	@Override
	public List<Map<String, Object>> selectMemberBlame(AdminMapper mapper, String no) {
		return mapper.selectMemberBlame(no);
	}

	
}
