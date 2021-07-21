package com.chairking.poom.member.model.service;

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
	public Member idDuplCheck(String id) {
		return dao.idDuplCheck(mapper,id);
	}

}
