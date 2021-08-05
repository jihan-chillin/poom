package com.chairking.poom.member.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chairking.poom.member.mapper.MemberMapper;
import com.chairking.poom.member.model.dao.MemberDao;
import com.chairking.poom.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao dao;
    
    @Autowired
    private MemberMapper mapper;

    @Override
	public int updateProfile(Map param) {
		return dao.updateProfile(mapper, param);
	}

	@Override
    public int updatePrivacy(Map param){
      return dao.updatePrivacy(mapper, param);
    };
}
