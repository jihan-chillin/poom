package com.chairking.poom.board.model.dao;

import java.util.List;
import java.util.Map;

import com.chairking.poom.common.Pagination;
import org.mybatis.spring.SqlSessionTemplate;

import com.chairking.poom.board.mapper.BoardMapper;
import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

public interface BoardDao {
	
	int insertBoard(BoardMapper mapper, Map param);
	
	//메인피드 글 등록하기
	int insertFeed(BoardMapper mapper, Map param);
	//메인 피드 리스트 가져오기
	List<Map<String, Object>> feedList(BoardMapper mapper, Map param);
	//좋아요 테이블 가져오기
	String[] likeTable(BoardMapper mapper, String id);
	
	List<Map<String, Object>> selectAllBoard(BoardMapper mapper, int cPage, int numPerpage);

	Map selectBoard(BoardMapper mapper, String boardNo);
	int insertBoardImg(BoardMapper mapper, BoardImage bi);
	int selectBoardNo(BoardMapper mapper, Board b);
	List<Map> selectCommentList(BoardMapper mapper, String boardNo);
	Map<String,Object> selectNotice(BoardMapper mapper, String noticeNo);

	// 보드 넘버 가져오기
	String getBoardNo(BoardMapper mapper);

	//좋아요+1
	int addLike(BoardMapper mapper, Map<String,String> map);
	int addLikeTable(BoardMapper mapper, Map<String,String> map);
	//좋아요-1
	int cancelLike(BoardMapper mapper, Map<String,String> map);
	int cancelLikeTable(BoardMapper mapper, Map<String,String> map);
	
	//마이태그 받아오기
	String[] myTag(BoardMapper mapper, Map param);
	//메인 마이태그글 받아오기
	List<Map<String, Object>> feedKeyList(SqlSessionTemplate session, Map map);
	
	//카테고리별 게시판 가져오기
	List<Map<String,Object>> selectBoardList(BoardMapper mapper, Map<String, String> cate, int cPage, int numPerpage);
	//카테고리별 공지사항 가져오기
	List<Map<String,Object>> selectBoardNotice(BoardMapper mapper, String cate);

	int insertBoardTag(BoardMapper mapper, String boardNo, String tagText);

	int insertTag(BoardMapper mapper, String tagText);

	List<Map<String, String>> dupleTagCheck(BoardMapper mapper, String tagText);

    int allBoardCount(BoardMapper mapper);

	List<Map<String, Object>> allBoard(BoardMapper mapper, Pagination pagination, Object memberloc);

	List<Map<String, Object>> selectAllBoardNotice(BoardMapper mapper);

	List<Map<String, Object>> allCateBoard(BoardMapper mapper, Pagination pagination, String cate, Object memberloc);

	int allcateBoardCount(BoardMapper mapper, String cate);

	List<Map<String, Object>> selectAllCateNotice(BoardMapper mapper, String cate);

	Map<String, Object> selectCateName(BoardMapper mapper, String cate);
}
