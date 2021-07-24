package com.chairking.poom.board.model.service;

import java.util.List;
import java.util.Map;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

public interface BoardService {

	int insertBoard(Board b);
	List<Map> selectAllBoard();
	Map selectBoard(String boardNo);
	int insertBoardImg(BoardImage bi);
	int selectBoardNo(Board b);
	List<Map> selectCommentList(String boardNo);
}
