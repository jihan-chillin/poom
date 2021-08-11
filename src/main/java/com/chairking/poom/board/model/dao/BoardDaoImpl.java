package com.chairking.poom.board.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.common.Pagination;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

@Repository
public class BoardDaoImpl implements BoardDao {

	@Override
	public int insertBoard(BoardMapper mapper, Map param, String imgName) {
		return mapper.insertBoard(param, imgName);
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
	public List<Map<String, Object>> feedList(BoardMapper mapper, String loc, int cPage, int numPerpage) {
		if(loc.equals("")) {
			return mapper.feedListAllAll(loc, (cPage-1)*numPerpage+1, (cPage*numPerpage));
		}else {
			return mapper.feedListLocAll(loc, (cPage-1)*numPerpage+1, (cPage*numPerpage));
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

	@Override
	public int allBoardCount(Object memberloc, BoardMapper mapper) {
		return mapper.allBoardCount(memberloc);
	}

	@Override
	public List<Map<String, Object>> allBoard(BoardMapper mapper, Pagination pagination, Object memberloc) {
		return mapper.allBoard(pagination,memberloc);
	}

	@Override
	public List<Map<String, Object>> selectAllBoardNotice(BoardMapper mapper) {
		return mapper.selectAllBoardNotice();
	}

	@Override
	public List<Map<String, Object>> boardTag(BoardMapper mapper) {
		return mapper.boardTag();
	}

	public List<Map<String, Object>> allCateBoard(BoardMapper mapper, Pagination pagination, String cate, Object memberloc) {
		return mapper.allCateBoard(pagination, cate, memberloc);
	}

	@Override
	public int allcateBoardCount(BoardMapper mapper, String cate, Object memberloc) {
		return mapper.allcateBoardCount(cate, memberloc);
	}

	@Override
	public List<Map<String, Object>> selectAllCateNotice(BoardMapper mapper, String cate) {
		return mapper.selectAllCateNotice(cate);
	}

	@Override
	public Map<String, Object> selectCateName(BoardMapper mapper, String cate) {
		return mapper.selectCateName(cate);
	}

	@Override
	public int boardTagFromform(BoardMapper mapper, String strBoardNo, String tagText) {
		return mapper.boardTagFromform(strBoardNo, tagText);
	}

	@Override
	public int TagFromform(BoardMapper mapper, String tagText) {
		return mapper.TagFromform(tagText);
	}

	@Override
	public String getBoardNoFromForm(BoardMapper mapper) {
		return mapper.getBoardNoFromForm();
	}

	@Override
	public List<String> boardTagList(BoardMapper mapper, String boardNo) {
		return mapper.boardTagList(boardNo);
	}

	@Override
	public List<Map<String, String>> selectAllBoardTag(BoardMapper mapper) {
		return mapper.selectAllBoardTag();
	}
	
	



	@Override
	public int commentWrite(BoardMapper mapper, Map<String, String> param) {
		return mapper.commentWrite(param);
	}

	@Override
	public int commentModify(BoardMapper mapper, Map<String, String> param) {
		return mapper.commentModify(param);
	}

	@Override
	public int commentCountUpdate(BoardMapper mapper, int count, String boardNo) {
		return mapper.commentCountUpdate(count, boardNo);
	}

	@Override
	public int commentDelete(BoardMapper mapper, String boardNo, String commentNo) {
		return mapper.commentDelete(boardNo, commentNo);
	}

	@Override
	public List<Map<String, Object>> searchBoardList(BoardMapper mapper, Pagination pagination, Object condition) {
		return mapper.searchBoardList(pagination, condition);
	}

	@Override
	public int searchBoardCount(BoardMapper mapper, Object condition) {
		return mapper.searchBoardCount(condition);
	}

	@Override
	public int boardDelete(BoardMapper mapper,String no) {
		return mapper.boardDelete(no);
	}

	@Override
	public String[] allComments(BoardMapper mapper) {
		return mapper.allComments();
	}

	@Override
	public int commentsDelete(BoardMapper mapper, String no) {
		return mapper.commentsDelete(no);
	}

	@Override
	public int boardTagDelete(BoardMapper mapper, String no) {
		return mapper.boardTagDelete(no);
	}

	@Override
	public int modifyBoard(BoardMapper mapper, Map param, String imgName) {
		return mapper.modifyBoard(param, imgName);
	}


	@Override
	public int allLocBoardCount(BoardMapper mapper){
		return mapper.allLocBoardCount();
	}

	@Override
	public List<Map<String, Object>> allLocBoard(BoardMapper mapper, Pagination pagination){
		return mapper.allLocBoard(pagination);
	}

	@Override
	public int allcateLocBoardCount(BoardMapper mapper, String cate) {
		return mapper.allcateLocBoardCount(cate);
	}

	@Override
	public List<Map<String, Object>> allCateLocBoard(BoardMapper mapper, Pagination pagination, String cate) {
		return mapper.allCateLocBoard(pagination, cate);
	}



}