package com.chairking.poom.member.model.dao;

import com.chairking.poom.member.mapper.MemberMapper;
import com.chairking.poom.member.model.vo.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl implements MemberDao{
    @Override
    public int updateProfile(MemberMapper mapper, Member m) {
        return 0;
    }

    @Override
    public int updatePrivacy(MemberMapper mapper, Member m) {
        return 0;
    }
}
