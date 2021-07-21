package com.chairking.poom.member.model.service;

import com.chairking.poom.member.model.vo.Member;

public interface MemberSerivce {

    // 프로필 수정
    int updateProfile(Member m);

    // 개인정보 수정
    int updatePrivacy(Member m);
}
