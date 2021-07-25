package com.chairking.poom.member.model.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.member.mapper.LoginMapper;
import com.chairking.poom.member.model.vo.Member;

@Repository
public class LoginDaoImpl implements LoginDao {

	@Override
	public Map<String,Object> duplCheck(LoginMapper mapper, String duplsql) {
		return mapper.duplCheck(duplsql);
	}

	@Override
	public int insertMember(LoginMapper mapper, Map m) {
		return mapper.insertMember(m);
	}

	@Override
	public int inesrtMemberKeyword(LoginMapper mapper, Map memberTag) {
		return mapper.inesrtMemberKeyword(memberTag);
	}

	@Override
	public Map<String,Object> selectMember(LoginMapper mapper, Map param) {
		return mapper.selectMember(param);
	}

	@Override
	public Map<String, Object> idFind(LoginMapper mapper, Map param) {
		return mapper.idFind(param);
	}

	@Override
	public Map<String, Object> pwFind(LoginMapper mapper, Map param) {
		return null;
	}
	
	
	
	

}
