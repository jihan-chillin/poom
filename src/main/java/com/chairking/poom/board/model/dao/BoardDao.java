package com.chairking.poom.board.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

public interface BoardDao {
	
	int insertBoard(BoardMapper mapper, Board b);
	
	int insertFeed(BoardMapper mapper, Map param);
	
	List<Map<String, Object>> feedList(BoardMapper mapper, Map param);

	List<Map<String, Object>> selectAllBoard(BoardMapper mapper, int cPage, int numPerpage);

	Map selectBoard(BoardMapper mapper, String boardNo);
	int insertBoardImg(BoardMapper mapper, BoardImage bi);
	int selectBoardNo(BoardMapper mapper, Board b);
	List<Map> selectCommentList(BoardMapper mapper, String boardNo);
	Map<String,Object> selectNotice(BoardMapper mapper, String noticeNo);
}
