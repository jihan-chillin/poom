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
//   @Update("update member set member_pw=#{memberPw}, member_email=#{memberEmail}, member_loc=#{memberLoc} where member_id='test'")
//   public


}
