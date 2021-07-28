package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

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
	private BlameDao dao;
	
	//게시글 신고 전체 리스트
	@Override
	public List<Map<String, Object>> allBlameList(String type,Pagination pagination) {
		List<Map<String,Object>> list = null;
		//type에 따라 dao다르게 실행하기
		switch(type) {
			case "blame": case "1" : list=dao.allBoardBlame(mapper,pagination);break;
			case "2" : list=dao.allCommentsBlame(mapper,pagination);break;
			case "3" : list=dao.allChatBlame(mapper,pagination);break;
			case "4" : list=dao.allMemberBlame(mapper,pagination);break;
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
}
