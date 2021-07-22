package com.chairking.poom.member.model.service;

import com.chairking.poom.member.model.dao.MemberDao;
import com.chairking.poom.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao dao;

    @Override
    public int updateProfile(Member m) {
        return 0;
    }

    @Override
    public int updatePrivacy(Member m){
      return dao.updatePrivacy(m);
    };
}
