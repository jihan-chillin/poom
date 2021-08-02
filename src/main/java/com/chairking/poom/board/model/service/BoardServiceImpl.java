package com.chairking.poom.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.dao.BoardDao;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao dao;
	
	@Autowired
	private BoardMapper mapper;
	
	@Override
	public int insertBoard(Board b) {
		//게시물 등록
		int result=dao.insertBoard(mapper, b);
		
		//게시물 첨부파일 등록
		if(b.getImages()!=null && result!=0) {
			//게시글 번호
			int boardNo=selectBoardNo(b);
//			System.out.println(boardNo);
			
			for(BoardImage bi:b.getImages()) {
				if(result!=0 && bi!=null) {
					bi.setBoardNo(boardNo);
					result=insertBoardImg(bi);
				}
//				System.out.println(bi);
			}
		}
		return result;
	}
	
	@Override
	public int insertBoardImg(BoardImage bi) {
		return dao.insertBoardImg(mapper, bi);
	}

	@Override
	public List<Map<String, Object>> selectAllBoard(int cPage, int numPerpage) {
		return dao.selectAllBoard(mapper, cPage, numPerpage);
	}

	@Override
	public Map selectBoard(String boardNo) {
		return dao.selectBoard(mapper, boardNo);
	}

	@Override
	public int selectBoardNo(Board b) {
		return dao.selectBoardNo(mapper, b);
	}

	@Override
	public List<Map> selectCommentList(String boardNo) {
		return dao.selectCommentList(mapper, boardNo);
	}

	@Override
	public int insertFeed(Map param) {
		return dao.insertFeed(mapper, param);
	}

}
