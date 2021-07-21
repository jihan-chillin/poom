package com.chairking.poom.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.chairking.poom.member.model.vo.Member;

@Mapper
public interface LoginMapper {

	@Select("SELECT * FROM MEMBER WHERE MEMBER_ID=#{id}")
	public Member idDuplCheck(String id);
}
