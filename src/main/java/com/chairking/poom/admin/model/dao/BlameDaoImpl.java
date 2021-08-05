package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.common.Pagination;

@Repository
public class BlameDaoImpl implements BlameDao {
	
	@Override
	public List<Map<String, Object>> allBoardBlame(AdminMapper mapper,Pagination pagination,String delStatus) {
		return mapper.allBoardBlame(pagination,delStatus);
	}
	
	@Override
	public List<Map<String, Object>> allCommentsBlame(AdminMapper mapper,Pagination pagination,String delStatus) {
		return mapper.allCommentsBlame(pagination,delStatus);
	}

	@Override
	public List<Map<String, Object>> allChatBlame(AdminMapper mapper, Pagination pagination,String delStatus) {
		return mapper.allChatBlame(pagination,delStatus);
	}

	@Override
	public List<Map<String, Object>> allMemberBlame(AdminMapper mapper, Pagination pagination,String delStatus) {
		return mapper.allMemberBlame(pagination,delStatus);
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
	public int selectBlameCount(SqlSessionTemplate session, Map<String, String> map) {
		return session.selectOne("adminMapper.selectBlameCount",map);
	}
	
	@Override
	public int changeDelStatus(SqlSessionTemplate session, Map<String, String> map) {
		return session.update("adminMapper.changeDelStatus",map);
	}

	@Override
	public List<Map<String, Object>> selectBlame(SqlSessionTemplate session, Map<String,Object> map) {
		return session.selectList("adminMapper.selectBlame",map);
	}

	@Override
	public int selectCountBlame1(SqlSessionTemplate session, Map<String, Object> map) {
		return session.selectOne("adminMapper.selectCountBlame1",map);
	}

	@Override
	public int selectCountBlame2(SqlSessionTemplate session, Map<String, Object> map) {
		return session.selectOne("adminMapper.selectCountBlame2",map);
	}

	@Override
	public int selectCountBlame3(SqlSessionTemplate session, Map<String, Object> map) {
		return session.selectOne("adminMapper.selectCountBlame3",map);
	}

	@Override
	public int selectCountBlame4(SqlSessionTemplate session, Map<String, Object> map) {
		return session.selectOne("adminMapper.selectCountBlame4",map);
	}

	@Override
	public int selectCountBlame5(SqlSessionTemplate session, Map<String, Object> map) {
		return session.selectOne("adminMapper.selectCountBlame5",map);
	}

	@Override
	public List<Map<String, String>> selectEctAll(SqlSessionTemplate session, Map<String, Object> map) {
		return session.selectList("adminMapper.selectEctAll",map);
	}

	@Override
	public int deleteBoardBlame(AdminMapper mapper, String no) {
		return mapper.deleteBoardBlame(no);
	}

	@Override
	public int deleteCommentsBlame(AdminMapper mapper, String no) {
		return mapper.deleteCommentsBlame(no);
	}

	@Override
	public int deleteChatBlame(AdminMapper mapper, String no) {
		return mapper.deleteChatBlame(no);
	}

	

	

	
	
}
