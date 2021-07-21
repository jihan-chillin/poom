package com.chairking.poom.member.mapper;

import com.chairking.poom.member.model.vo.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {
    // 프로필 수정
    // UPDATE [테이블] SET [열] = '변경할값' WHERE [조건]

    // 개인정보 수정
//   @Update("update member set member_pw=#{memberPw}, member_email=#{memberEmail}, member_loc=#{memberLoc} where member_id='test'")
//   public


}
