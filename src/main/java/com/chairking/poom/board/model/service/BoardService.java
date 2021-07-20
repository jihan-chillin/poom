package com.chairking.poom.board.model.service;

import java.util.List;

import com.chairking.poom.board.model.vo.Board;

public interface BoardService {

	int insertBoard(Board b);
	List<Board> selectAllBoard();
	
}
