package com.chairking.poom.board.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Override
	public int insertBoard(BoardMapper mapper, Map param) {
		return mapper.insertBoard(param);
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

	@Override
	public int insertFeed(BoardMapper mapper, Map param) {
		return mapper.insertFeed(param);
	}

	@Override
	public List<Map<String, Object>> feedList(BoardMapper mapper, Map param) {
		if(param.get("loc").equals("")) {
				return mapper.feedListAllAll(param);
		}else {
				return mapper.feedListLocAll(param);
		}
	}

	@Override
	public Map<String, Object> selectNotice(BoardMapper mapper, String noticeNo) {
		return mapper.selectNotice(noticeNo);
	}

	@Override
	public String[] likeTable(BoardMapper mapper, String id) {
		return mapper.likeTable(id);
	}

	@Override
	public int addLike(BoardMapper mapper, Map<String, String> map) {
		return mapper.addLike(map);
	}

	@Override
	public int addLikeTable(BoardMapper mapper, Map<String, String> map) {
		return mapper.addLikeTable(map);
	}

	@Override
	public String[] myTag(BoardMapper mapper, Map param) {
		return mapper.myTag(param);
	}

	@Override
	public List<Map<String, Object>> feedKeyList(SqlSessionTemplate session, Map map) {
		return session.selectList("boardMapper.feedKeyList", map);
	}

	@Override
	public int cancelLike(BoardMapper mapper, Map<String, String> map) {
		return mapper.cancelLike(map);
	}

	@Override
	public int cancelLikeTable(BoardMapper mapper, Map<String, String> map) {
		return mapper.cancelLikeTable(map);
	}

	@Override
	public List<Map<String, Object>> selectBoardList(BoardMapper mapper, Map<String, String> cate, int cPage, int numPerpage) {
		return mapper.selectBoardList(cate,cPage,numPerpage);
	}

	@Override
	public List<Map<String, Object>> selectBoardNotice(BoardMapper mapper, String cate) {
		return mapper.selectBoardNotice(cate);
	}

	@Override
	public String getBoardNo(BoardMapper mapper) {
		return mapper.getBoardNo();
	}

	@Override
	public int insertBoardTag(BoardMapper mapper, String boardNo, String tagText) {
		return mapper.insertBoardTag(boardNo, tagText);
	}

	@Override
	public int insertTag(BoardMapper mapper, String tagText) {
		return mapper.insertTag(tagText);
	}

	@Override
	public List<Map<String, String>> dupleTagCheck(BoardMapper mapper, String tagText) {
		return mapper.dupleTagCheck(tagText);
	}

}
