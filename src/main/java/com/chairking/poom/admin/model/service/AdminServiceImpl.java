package com.chairking.poom.admin.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.admin.model.dao.AdminDao;
import com.chairking.poom.admin.model.vo.Notice;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper mapper;
	
	@Autowired
	private AdminDao dao;
	
	//전체 공지사항 수 세기
	@Override
	public int countAllNotice() {
		return dao.countAllNotice(mapper);
	}

	//전체 공지사항 리스트 가져오기
	@Override
	public List<Map<String,Object>> allNotice(int cPage, int numPerpage) {
		return dao.allNotice(mapper, cPage, numPerpage);
	}
	
	//공지사항 등록
	@Override
	public int insertNotice(Notice n) {
		return dao.insertNotice(n,mapper);
	}

	//공지사항 view
	@Override
	public Map<String, Object> selectNotice(String no) {
		return dao.selectNotice(mapper, no);
	}

	//공지사항 삭제 => status:1로 바꾸기
	@Override
	public int noticeDelete(String no) {
		return dao.noticeDelete(mapper,no);
	}

	//공지사항 수정 => 기존꺼 삭제하고 재등록하는 것
	@Override
	public int realDelete(String no) {
		return dao.realDelete(mapper, no);
	}

	//공지사항 재게시 => status:1->0으로 바꾸기
	@Override
	public int changeStatus(String no) {
		return dao.changeStatus(mapper, no);
	}

	//게시글 신고 전체 리스트
	@Override
	public List<Map<String, Object>> allBoardBlame(int cPage, int numPerpage) {
		return dao.allBoardBlame(mapper,cPage,numPerpage);
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
