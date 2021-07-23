package com.chairking.poom.member.model.dao;

import java.util.Map;

import com.chairking.poom.member.mapper.LoginMapper;
import com.chairking.poom.member.model.vo.Member;

public interface LoginDao {
	Map<String,Object> duplCheck(LoginMapper mapper, String duplsql);
	int insertMember(LoginMapper mapper, Map m);
	int inesrtMemberKeyword(LoginMapper mapper, Map memberTag);
}
