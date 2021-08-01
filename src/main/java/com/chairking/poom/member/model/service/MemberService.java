package com.chairking.poom.member.model.service;

import java.util.Map;

import com.chairking.poom.member.model.vo.Member;

public interface MemberService {

    // 프로필 수정
    int updateProfile(Map param);

    // 개인정보 수정
    int updatePrivacy(Member m);

}
