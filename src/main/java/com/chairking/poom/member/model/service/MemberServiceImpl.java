package com.chairking.poom.member.model.service;

import com.chairking.poom.member.model.dao.MemberDao;
import com.chairking.poom.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberServiceImpl implements MemberSerivce{

    @Autowired
    private MemberDao dao;

    @Override
    public int updateProfile(Member m) {
        return 0;
    }

    @Override
    public int updatePrivacy(Member m) {
        return 0;
    }
}
