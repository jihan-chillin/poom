package com.chairking.poom.board.model.dao;

import java.util.List;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;

public interface BoardDao {
	
	int insertBoard(BoardMapper mapper, Board b);
	List<Board> selectAllBoard(BoardMapper mapper);
}
