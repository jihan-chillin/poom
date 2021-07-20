package com.chairking.poom.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.dao.BoardDao;
import com.chairking.poom.board.model.vo.Board;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao dao;
	
	@Autowired
	private BoardMapper mapper;
	
	@Override
	public int insertBoard(Board b) {
		return dao.insertBoard(mapper, b);
	}
	
	@Override
	public List<Board> selectAllBoard(){
		return dao.selectAllBoard(mapper);
	}

}
