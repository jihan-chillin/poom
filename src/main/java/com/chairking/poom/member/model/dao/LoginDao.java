package com.chairking.poom.member.model.dao;

import com.chairking.poom.member.mapper.LoginMapper;
import com.chairking.poom.member.model.vo.Member;

public interface LoginDao {

	Member idDuplCheck(LoginMapper mapper, String id);
}
