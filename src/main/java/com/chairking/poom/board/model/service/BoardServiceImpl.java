package com.chairking.poom.board.model.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.dao.BoardDao;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao dao;
	
	@Autowired
	private BoardMapper mapper;

	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public int insertBoard(Map param) {
		return dao.insertBoard(mapper, param);
	}

	@Override
	public int insertBoardImg(BoardImage bi) {
		return dao.insertBoardImg(mapper, bi);
	}

	@Override
	public List<Map<String, Object>> selectAllBoard(int cPage, int numPerpage) {
		return dao.selectAllBoard(mapper, cPage, numPerpage);
	}

	@Override
	public Map selectBoard(String boardNo) {
		return dao.selectBoard(mapper, boardNo);
	}

	@Override
	public int selectBoardNo(Board b) {
		return dao.selectBoardNo(mapper, b);
	}

	@Override
	public List<Map> selectCommentList(String boardNo) {
		return dao.selectCommentList(mapper, boardNo);
	}

	@Override
	public int insertFeed(Map param) {
		return dao.insertFeed(mapper, param);
	}

	@Override
	public List<Map<String, Object>> feedList(Map param) {
		return dao.feedList(mapper, param);
	}

	@Override
	public Map<String, Object> selectNotice(String noticeNo) {
		return dao.selectNotice(mapper, noticeNo);
	}

	@Override
	public String[] likeTable(String id) {
		return dao.likeTable(mapper, id);
	}

	@Override
	@Transactional
	public int changeLike(Map<String, String> map) {
		//map.get("like")가 누름 => 현재 이미 누른 상태 에서 한번더 click이니 좋아요-1로 바꾸기
		//안누름 => 현재 안누른상태에서 누른것 => 좋아요+1
		int result=0;
		if(map.get("like").equals("안누름")) {
			result=dao.addLike(mapper, map);
			if(result>0) {
				result=dao.addLikeTable(mapper,map);
			}
		}else {
			result=dao.cancelLike(mapper,map);
			if(result>0) {
				result=dao.cancelLikeTable(mapper,map);
			}
		}
		return result;
	}

	@Override
	public String[] myTag(Map param) {
		return dao.myTag(mapper,param);
	}

	@Override
	public List<Map<String, Object>> feedKeyList(Map map) {
		return dao.feedKeyList(session,map);
	}
	
	
	
	
}
