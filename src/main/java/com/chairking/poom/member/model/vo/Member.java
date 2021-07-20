package com.chairking.poom.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String memberId;
    private String memberPw;
    private String memberNickname;
    private String memberName;
    private String memberEmail;
    private Date memberBirth;
    private String memberLoc;
    private int denStatus; // "-1: 관리자, 1: 14일 정지, 2: 30일 정지, 3:  강퇴 또는 탈퇴"
    private String intro; // 자기소개
    private String memberImg;
    private String payLevel; //"1: 7일권, 2: 14일권, 3 : 30일권, 4 : 180일권"
    private String memberLevel; // 회원등급
}
