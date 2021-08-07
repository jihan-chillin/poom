package com.chairking.poom.member.model.dao;

import java.util.Map;

import com.chairking.poom.member.mapper.MemberMapper;
import com.chairking.poom.member.model.vo.Member;

public interface MemberDao {

    // 프로필 수정
    int updateProfile(MemberMapper mapper, Map param);

    // 개인정보 수정
    int updatePrivacy(MemberMapper mapper, Map param);
}
