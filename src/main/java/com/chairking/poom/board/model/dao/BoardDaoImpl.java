package com.chairking.poom.board.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Override
	public int insertBoard(BoardMapper mapper, Board b) {
		return mapper.insertBoard(b);
	}
	
	@Override
	public List<Board> selectAllBoard(BoardMapper mapper){
		return mapper.selectAllBoard();
	}
	

}
