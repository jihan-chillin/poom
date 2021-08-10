package com.chairking.poom.board.model.service;

import java.util.List;
import java.util.Map;

import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;
import com.chairking.poom.common.Pagination;

public interface BoardService {

	int insertBoard(Map param, String imgName);

	//메인피드 글 등록하기
	int insertFeed(Map param);
	//메인 피드 리스트 가져오기
	List<Map<String, Object>> feedList(String loc, int cPage, int numPerpage);
	//좋아요 테이블 가져오기
	String[] likeTable(String no);
	//보드태그 테이블 가져오기
	List<Map<String, Object>> boardTag();
	
	// 게시글 전체 가져오기
	List<Map<String, Object>> selectAllBoard(int cPage, int numPerpage);

	Map selectBoard(String boardNo);

	int insertBoardImg(BoardImage bi);

	int selectBoardNo(Board b);

	List<Map> selectCommentList(String boardNo);
	
	Map<String,Object> selectNotice(String noticeNo);
	//좋아요+1 & 좋아요-1
	int changeLike(Map<String,String> map);
	
	//마이태그 받아오기
	String[] myTag(Map param);
	
	//메인 마이태그글 받아오기
	List<Map<String, Object>> feedKeyList(Map map);
	
	//카테고리별 게시글 가져오기
	List<Map<String,Object>> selectBoardList(Map<String, String> cate, int cPage, int numPerpage);
	
	//카테고리별 공지사항 가져오기
	List<Map<String,Object>> selectBoardNotice(String cate);

    String getBoardNo();

	int insertBoardTag(String boardNo, String tagText);

	int insertTag(String tagText);

	List<Map<String, String>> dupleTagCheck(String tagText);

    int allBoardCount(Object memberloc);

	List<Map<String, Object>> allBoard(Pagination pagination, Object memberloc);

	List<Map<String, Object>> selectAllBoardNotice();

    List<Map<String, Object>> allCateBoard(Pagination pagination, String cate, Object memberloc);

	int allcateBoardCount(String cate, Object memberloc);

	List<Map<String, Object>> selectAllCateNotice(String cate);

	Map<String, Object> selectCateName(String cate);

	int boardTagFromform(String strBoardNo, String tagText);

	int TagFromform(String tagText);

    String getBoardNoFromForm();

    //보드뷰에서 태그들 가져오기
    List<String> boardTagList(String boardNo);
    //전체글리스트 태그 가져오기
    List<Map<String,String>> selectAllBoardTag ();

	//게시글 댓글 입력
	int commentWrite(Map<String, String> param);

	//게시글 댓글 수 변경
	int commentCountUpdate(int count, String boardNo);

	//댓글 삭제
	int commentDelete(String boardNo, String commentNo);


    List<Map<String, Object>> searchBoardList(String condition);
}
