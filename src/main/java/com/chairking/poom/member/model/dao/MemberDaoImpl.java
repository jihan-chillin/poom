package com.chairking.poom.member.model.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.member.mapper.MemberMapper;
import com.chairking.poom.member.model.vo.Member;

@Repository
public class MemberDaoImpl implements MemberDao{
	
    @Override
    public int updateProfile(MemberMapper mapper, Map param) {
        return mapper.updateProfile(param);
    }

    @Override
    public int updatePrivacy(MemberMapper mapper, Map param) {
        return mapper.updatePrivacy(param);
    }
}
