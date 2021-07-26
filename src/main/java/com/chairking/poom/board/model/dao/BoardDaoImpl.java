package com.chairking.poom.board.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Override
	public int insertBoard(BoardMapper mapper, Board b) {
		return mapper.insertBoard(b);
	}

	@Override
	public int insertBoardImg(BoardMapper mapper, BoardImage bi) {
		return mapper.insertBoardImg(bi);
	}

	@Override
	public List<Map<String, Object>> selectAllBoard(BoardMapper mapper, int cPage, int numPerpage) {
		return mapper.selectAllBoard(cPage, numPerpage);
	}

	@Override
	public Map selectBoard(BoardMapper mapper, String boardNo) {
		return mapper.selectBoard(boardNo);
	}

	@Override
	public int selectBoardNo(BoardMapper mapper, Board b) {
		return mapper.selectBoardNo(b);
	}

	@Override
	public List<Map> selectCommentList(BoardMapper mapper, String boardNo) {
		return mapper.selectCommentList(boardNo);
	}




}
