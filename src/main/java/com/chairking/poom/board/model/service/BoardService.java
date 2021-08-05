package com.chairking.poom.board.model.service;

import java.util.List;
import java.util.Map;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

public interface BoardService {

	int insertBoard(Board b);

	//메인피드 글 등록하기
	int insertFeed(Map param);
	//메인 피드 리스트 가져오기
	List<Map<String, Object>> feedList(Map param);
	//좋아요 테이블 가져오기
	String[] likeTable(String no);
	
	// 게시글 전체 가져오기
	List<Map<String, Object>> selectAllBoard(int cPage, int numPerpage);

	Map selectBoard(String boardNo);

	int insertBoardImg(BoardImage bi);

	int selectBoardNo(Board b);

	List<Map> selectCommentList(String boardNo);
	
	Map<String,Object> selectNotice(String noticeNo);
	
	int addLike(Map<String,String> map);
	
}
