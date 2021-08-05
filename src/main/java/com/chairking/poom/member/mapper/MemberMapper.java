package com.chairking.poom.member.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {
    // 프로필 수정
	@Update("UPDATE MEMBER SET INTRO=#{intro}, MEMBER_IMG=#{memberImg} WHERE MEMBER_ID=#{id}")
	public int updateProfile(Map param);

    // 개인정보 수정
	@Update("UPDATE MEMBER SET MEMBER_PW=#{pw}, MEMBER_EMAIL=#{memberEmail}, MEMBER_LOC=#{memberLoc} WHERE MEMBER_ID=#{id}")
	public int updatePrivacy(Map param);


}
