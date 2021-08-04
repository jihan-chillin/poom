package com.chairking.poom.board.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

public interface BoardDao {
	
	int insertBoard(BoardMapper mapper, Map param);
	
	//메인피드 글 등록하기
	int insertFeed(BoardMapper mapper, Map param);
	//메인 피드 리스트 가져오기
	List<Map<String, Object>> feedList(BoardMapper mapper, Map param);
	//좋아요 테이블 가져오기
	List<Map<String, Object>> likeTable(BoardMapper mapper);
	
	List<Map<String, Object>> selectAllBoard(BoardMapper mapper, int cPage, int numPerpage);

	Map selectBoard(BoardMapper mapper, String boardNo);
	int insertBoardImg(BoardMapper mapper, BoardImage bi);
	int selectBoardNo(BoardMapper mapper, Board b);
	List<Map> selectCommentList(BoardMapper mapper, String boardNo);
	Map<String,Object> selectNotice(BoardMapper mapper, String noticeNo);
	
	//마이태그 받아오기
	String[] myTag(BoardMapper mapper, Map param);
	//메인 마이태그글 받아오기
	List<Map<String, Object>> feedKeyList(SqlSessionTemplate session, Map map);
	
}
