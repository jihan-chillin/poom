package com.chairking.poom.member.model.service;

import com.chairking.poom.member.model.vo.Member;

public interface LoginService {

	Member idDuplCheck(String id);
}
