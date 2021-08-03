package com.chairking.poom.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;

@Mapper
public interface BoardMapper {

	//게시글 등록 쿼리
	@Insert("INSERT INTO BOARD VALUES(SEQ_BOARDNO.NEXTVAL, #{boardTitle}, "
			+ "#{boardContent}, DEFAULT, DEFAULT, DEFAULT, #{boardLoc}, DEFAULT, #{boardCate}, #{memberId})")
	public int insertBoard(Board b);
	
	//게시글 번호 가져오는 쿼리문
	@Select("SELECT BOARD_NO AS boardNo FROM BOARD WHERE BOARD_TITLE=#{boardTitle} AND MEMBER_ID=#{memberId}")	
	public int selectBoardNo(Board b);
	
	//게시글 사진 등록
	@Insert("INSERT INTO IMAGE VALUES(SEQ_IMAGENO.NEXTVAL, #{boardNo}, #{originImg}, #{renameImg})")
	public int insertBoardImg(BoardImage bi);
	
	//모든 게시글 조회
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, B.*, (SELECT COUNT(*) FROM COMMENTS A WHERE A.BOARD_NO = B.BOARD_NO) AS CNT FROM (SELECT * FROM BOARD WHERE DEL_STATUS=0 ORDER BY BOARD_NO DESC)B) WHERE RNUM BETWEEN #{cPage} AND #{numPerpage}")
	public List<Map<String, Object>> selectAllBoard(int cPage, int numPerpage);
	
	//게시글 상세 조회
	@Select("SELECT B.*, (SELECT MEMBER_NICKNAME FROM MEMBER WHERE MEMBER_ID=B.MEMBER_ID) AS B_WRITER FROM BOARD B WHERE BOARD_NO=#{boardNo}")
	public Map selectBoard(String boardNo);
	
	//게시글 댓글 조회
	@Select("SELECT C.*, (SELECT MEMBER_NICKNAME FROM MEMBER WHERE MEMBER_ID=C.COMMENT_WRITER) AS C_NICKNAME FROM COMMENTS C WHERE BOARD_NO=${boardNo}")
	public List<Map> selectCommentList(String boardNo);
	
	//메인피드 등록
	@Insert("INSERT INTO BOARD VALUES(SEQ_BOARDNO.NEXTVAL, #{title}, #{content}, DEFAULT, DEFAULT, DEFAULT, "
			+ "#{loc}, DEFAULT, #{category}, #{id}, DEFAULT)")
	public int insertFeed(Map param);
	
	//메인피드 게시글(전국,전체)
	@Select("SELECT *"
			+ "FROM (SELECT B.*, C.CATEGORY_NAME AS CATEGORY, I.RENAME_IMG AS IMG FROM BOARD B JOIN CATEGORY C ON (BOARD_CATE=CATEGORY_NO)"
			+ "LEFT JOIN IMAGE I ON (B.BOARD_NO=I.BOARD_NO))"
			+ "ORDER BY BOARD_DATE DESC")
	public List<Map<String, Object>> feedListAllAll(Map param);
	
	//메인피드 게시글(전국,키워드)
	@Select("SELECT * FROM BOARD ORDER BY BOARD_DATE DESC")
	public List<Map<String, Object>> feedListAllKey(Map param);
	
	//메인피드 게시글(지역,전체)
	@Select("SELECT *"
			+ "FROM (SELECT B.*, C.CATEGORY_NAME AS CATEGORY, I.RENAME_IMG AS IMG FROM BOARD B JOIN CATEGORY C ON (BOARD_CATE=CATEGORY_NO)"
			+ "LEFT JOIN IMAGE I ON (B.BOARD_NO=I.BOARD_NO))"
			+ "WHERE BOARD_LOC=#{loc} ORDER BY BOARD_DATE DESC")
	public List<Map<String, Object>> feedListLocAll(Map param);
		
	//메인피드 게시글(지역,키워드)
	@Select("SELECT * FROM BOARD ORDER BY BOARD_DATE DESC")
	public List<Map<String, Object>> feedListLocKey(Map param);
}
