package com.chairking.poom.admin.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.common.Pagination;

public interface BlameDao {
	//List가져오기
	List<Map<String,Object>> allBoardBlame(AdminMapper mapper, Pagination pagination,String delStatus);
	List<Map<String,Object>> allCommentsBlame(AdminMapper mapper, Pagination pagination,String delStatus);
	List<Map<String,Object>> allChatBlame(AdminMapper mapper, Pagination pagination,String delStatus);
	List<Map<String,Object>> allMemberBlame(AdminMapper mapper, Pagination pagination,String delStatus);
	//총 데이터세기
	int allBoardBlameCount(AdminMapper mapper);
	int allCommentsBlameCount(AdminMapper mapper);
	int allChatBlameCount(AdminMapper mapper);
	int allMemberBlameCount(AdminMapper mapper);
	//신고하기팝업 & 각 테이블 blame_count +1
	int insertBoardBlame(AdminMapper mapper, Map<String,String> map);
	int updateBrdBlameCount(AdminMapper mapper, String no);
	int insertCommentsBlame(AdminMapper mapper, Map<String,String> map);
	int updateCommentsBlameCount(AdminMapper mapper, String no);
	int insertChatBlame(AdminMapper mapper, Map<String,String> map);
	int updateChatBlameCount(AdminMapper mapper, String no);
	
	//누적신고수 팝업=>select
	List<Map<String,Object>> selectBoardBlame(AdminMapper mapper,String no);
	List<Map<String,Object>> selectCommentsBlame(AdminMapper mapper,String no);
	List<Map<String,Object>> selectChatBlame(AdminMapper mapper,String no);
	List<Map<String,Object>> selectMemberBlame(AdminMapper mapper,String no);
	
	//동적쿼리테스트
	List<Map<String,Object>> selectBlame(SqlSessionTemplate session,Map<String,Object> map);
	int selectCountBlame1(SqlSessionTemplate session, Map<String,Object> map);
	int selectCountBlame2(SqlSessionTemplate session, Map<String,Object> map);
	int selectCountBlame3(SqlSessionTemplate session, Map<String,Object> map);
	int selectCountBlame4(SqlSessionTemplate session, Map<String,Object> map);
	int selectCountBlame5(SqlSessionTemplate session, Map<String,Object> map);
	
	List<Map<String,String>> selectEctAll(SqlSessionTemplate session, Map<String,Object> map);
	//Stirng[] selectCountBlame5(SqlSessionTemplate session, Map<String,Object> map);
}
