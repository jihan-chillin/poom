package com.chairking.poom.member.model.dao;

import org.springframework.stereotype.Repository;

import com.chairking.poom.member.mapper.LoginMapper;
import com.chairking.poom.member.model.vo.Member;

@Repository
public class LoginDaoImpl implements LoginDao {

	@Override
	public Member idDuplCheck(LoginMapper mapper, String id) {
		return mapper.idDuplCheck(id);
	}

}
