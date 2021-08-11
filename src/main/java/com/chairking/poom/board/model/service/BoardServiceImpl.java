package com.chairking.poom.board.model.service;

import java.util.List;
import java.util.Map;

import com.chairking.poom.common.Pagination;
import org.mybatis.spring.SqlSessionTemplate;
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

	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public int insertBoard(Map param, String imgName) {
		return dao.insertBoard(mapper, param, imgName);
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

	@Override
	public List<Map<String, Object>> feedList(String loc, int cPage, int numPerpage) {
		return dao.feedList(mapper, loc, cPage, numPerpage);
	}

	@Override
	public Map<String, Object> selectNotice(String noticeNo) {
		return dao.selectNotice(mapper, noticeNo);
	}

	@Override
	public String[] likeTable(String id) {
		return dao.likeTable(mapper, id);
	}

	@Override
	@Transactional
	public int changeLike(Map<String, String> map) {
		//map.get("like")가 누름 => 현재 이미 누른 상태 에서 한번더 click이니 좋아요-1로 바꾸기
		//안누름 => 현재 안누른상태에서 누른것 => 좋아요+1
		int result=0;
		if(map.get("like").equals("안누름")) {
			result=dao.addLike(mapper, map);
			if(result>0) {
				result=dao.addLikeTable(mapper,map);
			}
		}else {
			result=dao.cancelLike(mapper,map);
			if(result>0) {
				result=dao.cancelLikeTable(mapper,map);
			}
		}
		return result;
	}

	@Override
	public String[] myTag(Map param) {
		return dao.myTag(mapper,param);
	}

	@Override
	public List<Map<String, Object>> feedKeyList(Map map) {
		return dao.feedKeyList(session,map);
	}

	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, String> cate, int cPage, int numPerpage) {
		return dao.selectBoardList(mapper, cate, cPage, numPerpage);
	}

	@Override
	public List<Map<String, Object>> selectBoardNotice(String cate) {
		return dao.selectBoardNotice(mapper,cate);
	}

	@Override
	public String getBoardNo() {
		return dao.getBoardNo(mapper);
	}

	@Override
	public int insertBoardTag(String boardNo, String tagText) {
		return dao.insertBoardTag(mapper, boardNo, tagText);
	}

	@Override
	public int insertTag(String tagText) {
		return dao.insertTag(mapper, tagText);
	}

	@Override
	public List<Map<String, String>> dupleTagCheck(String tagText) {
		return dao.dupleTagCheck(mapper, tagText);
	}

	@Override
	public int allBoardCount(Object memberloc) {
		return dao.allBoardCount(memberloc,mapper);
	}

	@Override
	public List<Map<String, Object>> allBoard(Pagination pagination, Object memberloc) {
		return dao.allBoard(mapper, pagination, memberloc);
	}

	@Override
	public List<Map<String, Object>> boardTag() {
		return dao.boardTag(mapper);
	}

	@Override
	public List<Map<String, Object>> selectAllBoardNotice() {
		return dao.selectAllBoardNotice(mapper);
	}

	public List<Map<String, Object>> allCateBoard(Pagination pagination, String cate, Object memberloc) {
		return dao.allCateBoard(mapper, pagination, cate, memberloc);
	}

	@Override
	public int allcateBoardCount(String cate, Object memberloc) {
		return dao.allcateBoardCount(mapper, cate, memberloc);
	}

	@Override
	public List<Map<String, Object>> selectAllCateNotice(String cate) {
		return dao.selectAllCateNotice(mapper, cate);
	}

	@Override
	public Map<String, Object> selectCateName(String cate) {
		return dao.selectCateName(mapper, cate);
	}

	@Override
	public int boardTagFromform(String strBoardNo, String tagText) {
		return dao.boardTagFromform(mapper, strBoardNo, tagText);
	}

	@Override
	public int TagFromform(String tagText) {
		return dao.TagFromform(mapper, tagText);
	}

	@Override
	public String getBoardNoFromForm() {
		return dao.getBoardNoFromForm(mapper);
	}

	@Override
	public List<String> boardTagList(String boardNo) {
		return dao.boardTagList(mapper,boardNo);
	}

	@Override
	public List<Map<String, String>> selectAllBoardTag() {
		return dao.selectAllBoardTag(mapper);
	}



	@Override
	public int commentWrite(Map<String, String> param) {
		return dao.commentWrite(mapper, param);
	}

	@Override
	public int commentCountUpdate(int count, String boardNo) {
		return dao.commentCountUpdate(mapper, count, boardNo);
	}

	@Override
	public int commentDelete(String boardNo, String commentNo) {
		return dao.commentDelete(mapper, boardNo, commentNo);
	}

	@Override
	public int commentModify(Map<String, String> param) {
		return dao.commentModify(mapper, param);
	}
	
	


	//검색기능
	@Override
	public List<Map<String, Object>> searchBoardList(Pagination pagination, Object condition) {
		System.out.println("*********************************\n" + pagination + "\n" + condition);
		return dao.searchBoardList(mapper, pagination, condition);

	}
	
	//게시글 삭제
	@Override
	@Transactional
	public int boardDelete(String no) {
		int result=dao.boardDelete(mapper, no);
		//만약 댓글테이블에 해당 보드넘버가 있다면 댓글들도 del_status=1로 변경하기
		if(result>0) {
			String[] comments=dao.allComments(mapper);
			for(String s:comments) {
				if(s.equals(no)) {
					int apply=dao.commentsDelete(mapper,no);
				}
			}
			//보드태그에도 있으면 지우기
			List<Map<String,String>> tagList=dao.selectAllBoardTag(mapper);
			for(Map m:tagList) {
				if(m.get("BOARD_NO").equals((String)no)) {
					int apply=dao.boardTagDelete(mapper, no);
				}
			}
		}
		return result;
	}
	
	@Override
	public int searchBoardCount(Object condition) {
		return dao.searchBoardCount(mapper,condition);
	}

	@Override
	public int modifyBoard(Map param, String imgName) {
		return dao.modifyBoard(mapper, param, imgName);
	}

}
