package com.chairking.poom.member.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chairking.poom.member.mapper.LoginMapper;
import com.chairking.poom.member.model.dao.LoginDao;
import com.chairking.poom.member.model.vo.Member;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao dao;
	
	@Autowired
	LoginMapper mapper;
	
	@Override
	public Map<String,Object> duplCheck(String duplsql) {
		return dao.duplCheck(mapper,duplsql);
	}

	@Override
	public int insertMember(Map m) {
		return dao.insertMember(mapper,m);
	}

	@Override
	public int inesrtMemberKeyword(Map memberTag) {
		return dao.inesrtMemberKeyword(mapper,memberTag);
	}
	
	

}
