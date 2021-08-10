package com.chairking.poom.member.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chairking.poom.member.model.vo.Member;
@Mapper
public interface LoginMapper {

	@Select("SELECT * FROM MEMBER WHERE ${duplsql}")
	public Map<String,Object> duplCheck(String duplsql);
	
	@Insert("INSERT INTO MEMBER VALUES (#{memberId},#{memberPw},#{memberNickname},#{memberName},#{memberEmail},"
			+ "#{memberBirth},#{memberLoc},default,null,null,default,default,null)")
	public int insertMember(Map m);
	
	@Insert("INSERT INTO MEMBERTAG VALUES (SEQ_MTAGNO.NEXTVAL,#{id},#{keyword})")
	public int inesrtMemberKeyword(Map memberTag);
	
	@Select("SELECT * FROM MEMBER WHERE MEMBER_ID=#{id}")
	public Map<String,Object> selectMember(Map param);
	
	@Select("SELECT MEMBER_ID FROM MEMBER WHERE MEMBER_NAME=#{memberName} AND MEMBER_EMAIL=#{memberEmail}")
	public String idFind(Map param);
	
	@Select("SELECT * FROM MEMBER WHERE MEMBER_ID=#{id} AND MEMBER_NAME=#{name}")
	public Map<String,Object> pwFind(Map param);
	
	@Update("UPDATE MEMBER SET MEMBER_PW=#{memberPw} WHERE MEMBER_ID=#{memberId} AND MEMBER_NAME=#{memberName}")
	public int updatePw(Map param);
	
}
