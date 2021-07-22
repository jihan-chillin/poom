package com.chairking.poom.board.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

public interface BoardDao {
	
	int insertBoard(BoardMapper mapper, Board b);
	List<Map> selectAllBoard(BoardMapper mapper);
	Map selectBoard(BoardMapper mapper, String boardNo);
	int insertBoardImg(BoardMapper mapper, BoardImage bi);
	int selectBoardNo(BoardMapper mapper, Board b);
}
