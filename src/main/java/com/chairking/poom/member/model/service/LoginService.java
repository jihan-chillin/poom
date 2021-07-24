package com.chairking.poom.member.model.service;

import java.util.Map;

import com.chairking.poom.member.model.vo.Member;

public interface LoginService {

	Map<String,Object> duplCheck(String duplsql);
	int insertMember(Map m);
	int inesrtMemberKeyword(Map memberTag);
	Map<String,Object> selectMember(Map param);
	Map<String,Object> idFind(Map param);
	Map<String,Object> pwFind(Map param);
}
