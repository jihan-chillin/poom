package com.chairking.poom.admin.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.dao.BlameDao;
import com.chairking.poom.common.Pagination;

@Service
public class BlameServiceImpl implements BlameService {
	
	@Autowired
	private AdminMapper mapper;
	
	@Autowired
	private SqlSessionTemplate session;
	
	@Autowired
	private BlameDao dao;
	
	//게시글 신고 전체 리스트
	@Override
	public List<Map<String, Object>> allBlameList(String type,Pagination pagination, String delStatus) {
		List<Map<String,Object>> list = null;
		//type에 따라 dao다르게 실행하기
		switch(type) {
			case "blame": case "1" : list=dao.allBoardBlame(mapper,pagination,delStatus);break;
			case "2" : list=dao.allCommentsBlame(mapper,pagination,delStatus);break;
			case "3" : list=dao.allChatBlame(mapper,pagination,delStatus);break;
			case "4" : list=dao.allMemberBlame(mapper,pagination,delStatus);break;
		}
		return list;
	}
	//게시글 갯수 가져오기
	@Override
	public int blameCount(String type) {
		int result=0;
		switch(type) {
			case "blame": case "1" : result= dao.allBoardBlameCount(mapper);break;
			case "2" : result= dao.allCommentsBlameCount(mapper);break;
			case "3" : result= dao.allChatBlameCount(mapper);break;
			case "4" : result= dao.allMemberBlameCount(mapper);break;
		}
		return result;
	}

	//신고하기 팝업 / 신고하기=> insert 각 신고테이블 & 게시글/댓글/채팅 테이블 컬럼 count+1하기
	@Override
	@Transactional
	public int insertBlame(Map<String, String> map) {
		int result=0;
		switch(map.get("type")) {
			case "b" : 
				result=dao.insertBoardBlame(mapper,map);
				//각 신고테이블에 들어갔으면 게시글 테이블 컬럼 count+1하기
				if(result>0) {
					result=dao.updateBrdBlameCount(mapper,map.get("no"));
				}
				break;
			case "bc" : 
				result=dao.insertCommentsBlame(mapper,map);
				if(result>0) {
					result=dao.updateCommentsBlameCount(mapper,map.get("no"));
				}
				break;
			case "ch" : 
				result=dao.insertChatBlame(mapper,map);
				if(result>0) {
					result=dao.updateChatBlameCount(mapper,map.get("no"));
				}
				break;
		}
		return result;
	}
	
	@Override
	public String hiddenCheck(Map<String, String> map) {
		String result="";
		//insert+update 후에 function으로 체크하기
		switch(map.get("type")) {
//		case "b" : 
//			result=dao.hiddenBoard(mapper,map);
//			break;
//		case "bc" : 
//			result=dao.hiddenComments(mapper,map);
//			break;
		case "ch" : 
			result=dao.hiddenChat(mapper,map.get("no"));
			break;
	}
		return result;
	}
	//type,no로 신고글 가져오기
	@Override
	public List<Map<String, Object>> selectBlame(Map<String, Object> map) {
		List<Map<String,Object>> param=null;
//		switch((String)map.get("type")) {
//			case "blame": case "1" : param= dao.selectBoardBlame(mapper,(String)map.get("no"));break;
//			case "2" : param= dao.selectCommentsBlame(mapper,(String)map.get("no"));break;
//			case "3" : param= dao.selectChatBlame(mapper,(String)map.get("no"));break;
//			case "4" : param= dao.selectMemberBlame(mapper,(String)map.get("no"));break;
//		}
		param=dao.selectBlame(session, map);
		return param;
	}
	
	//각 신고 사유별 count세기
	@Override
	public Map<String, Object> selectCountBlame(Map<String, Object> map) {
		Map<String, Object> countMap=new HashMap();
		countMap.put("1", dao.selectCountBlame1(session, map));
		countMap.put("2", dao.selectCountBlame2(session, map));
		countMap.put("3", dao.selectCountBlame3(session, map));
		countMap.put("4", dao.selectCountBlame4(session, map));
		countMap.put("5", dao.selectCountBlame5(session,map));
		return countMap;
	}
	
	//기타사유 리스트 가져오기
	@Override
	public List<Map<String, String>> selectEctAll(Map<String, Object> map) {
		return dao.selectEctAll(session,map);
	}
	
	//체크박스=>삭제 시 del_status 업데이트 하기
	@Override
	@Transactional
	public int deleteBlame(Map<String,Object> map) throws RuntimeException{
		int result=0;
		List<String> list = (List)map.get("arr");
		for(String s :list) {
			switch((String)map.get("type")) {
				case "게시글": result= dao.deleteBoardBlame(mapper,s);break;
				case "댓글" : result= dao.deleteCommentsBlame(mapper,s);break;
				case "채팅" : result= dao.deleteChatBlame(mapper,s);break;
				//case "회원" : result= dao.deleteMemberBlame(mapper,(String)map.get("no"));break;
			}
		}
		if(result!=1) {
			throw new RuntimeException("실패");
		}
		return result;
	}
	
	
	
}
