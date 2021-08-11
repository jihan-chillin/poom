package com.chairking.poom.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.board.model.vo.BoardImage;
import com.chairking.poom.common.Pagination;

@Mapper
public interface BoardMapper {

	//게시글 등록 쿼리
//	@Insert("INSERT INTO BOARD VALUES(SEQ_BOARDNO.NEXTVAL, #{boardTitle}, "
//			+ "#{boardContent}, DEFAULT, DEFAULT, DEFAULT, #{boardLoc}, DEFAULT, #{boardCate}, #{memberId})")
//	public int insertBoard(Board b);

	// INSERT INTO BOARD VALUES(SEQ_BOARDNO.nextval, '되는지 TEST', '그냥 오라클로 작성하는 거임', DEFAULT, 0, SYSDATE, '서울', 0, '1', 'kimjihan77',0)
	//게시글 등록
	@Insert("INSERT INTO BOARD VALUES(SEQ_BOARDNO.nextval, #{param.board-title}, #{param.boardContent}, DEFAULT, 0, SYSDATE, #{param.memberLoc}, 0, #{param.boardCate}, #{param.memberId}, 0,0, #{imgName})")
	public int insertBoard(Map param, String imgName);
	
	//게시글 번호 가져오는 쿼리문
	@Select("SELECT BOARD_NO AS boardNo FROM BOARD WHERE BOARD_TITLE=#{boardTitle} AND MEMBER_ID=#{memberId}")	
	public int selectBoardNo(Board b);
	
	//게시글 사진 등록
	@Insert("INSERT INTO IMAGE VALUES(SEQ_IMAGENO.NEXTVAL, #{boardNo}, #{originImg}, #{renameImg})")
	public int insertBoardImg(BoardImage bi);
	
	//모든 게시글 조회
	@Select("SELECT * FROM (B.*, (SELECT COUNT(*) FROM COMMENTS A WHERE A.BOARD_NO = B.BOARD_NO) AS CNT FROM BOARD B WHERE DEL_STATUS=0 ORDER BY BOARD_DATE DESC) WHERE RONUM BETWEEN #{cPage} AND #{numPerpage}")
	public List<Map<String, Object>> selectAllBoard(int cPage, int numPerpage);
	
	//게시글 상세 조회
	@Select("SELECT B.*, (SELECT MEMBER_NICKNAME FROM MEMBER WHERE MEMBER_ID=B.MEMBER_ID) AS B_WRITER FROM BOARD B WHERE BOARD_NO=#{boardNo}")
	public Map selectBoard(String boardNo);
	
	//게시글 댓글 조회
	@Select("SELECT C.*, (SELECT MEMBER_NICKNAME FROM MEMBER WHERE MEMBER_ID=C.COMMENT_WRITER) AS C_NICKNAME, (SELECT MEMBER_IMG FROM MEMBER WHERE MEMBER_ID=C.COMMENT_WRITER) AS C_PROFILE FROM COMMENTS C WHERE BOARD_NO=#{boardNo} AND DEL_STATUS=0 ORDER BY COMMENT_DATE DESC")
	public List<Map> selectCommentList(String boardNo);
	
	//메인피드 등록
	@Insert("INSERT INTO BOARD VALUES(SEQ_BOARDNO.NEXTVAL, #{title}, #{content}, DEFAULT, DEFAULT, DEFAULT, "
			+ "#{loc}, DEFAULT, #{category}, #{id}, DEFAULT, DEFAULT, DEFAULT)")
	public int insertFeed(Map param);
	
	//메인피드 게시글(전국,전체)
	@Select("SELECT * FROM("
			+ "SELECT ROWNUM AS RNUM, A.*"
			+ "FROM (SELECT B.*, M.MEMBER_NICKNAME AS NICKNAME, C.CATEGORY_NAME AS CATEGORY "
			+ "FROM BOARD B JOIN MEMBER M ON (B.MEMBER_ID=M.MEMBER_ID)"
			+ "JOIN CATEGORY C ON (B.BOARD_CATE=C.CATEGORY_NO)"
			+ "WHERE DEL_STATUS=0 ORDER BY BOARD_DATE DESC) A) WHERE RNUM >= #{cPage} AND RNUM <= #{numPerpage}")
	public List<Map<String, Object>> feedListAllAll(String loc, int cPage, int numPerpage);
	
	//메인피드 게시글(지역,전체)
	@Select("SELECT * FROM("
			+ "SELECT ROWNUM AS RNUM, A.*"
			+ "FROM (SELECT B.*, M.MEMBER_NICKNAME AS NICKNAME, C.CATEGORY_NAME AS CATEGORY "
			+ "FROM BOARD B JOIN MEMBER M ON (B.MEMBER_ID=M.MEMBER_ID)"
			+ "JOIN CATEGORY C ON (B.BOARD_CATE=C.CATEGORY_NO)"
			+ "WHERE DEL_STATUS=0 AND BOARD_LOC=#{loc} ORDER BY BOARD_DATE DESC) A) WHERE RNUM >= #{cPage} AND RNUM <= #{numPerpage}")
	public List<Map<String, Object>> feedListLocAll(@Param("loc")String loc, int cPage, int numPerpage);
	
	//게시판에서 공지사항클릭
	@Select("SELECT * FROM NOTICE WHERE NOTICE_NO=#{no}")
	public Map<String,Object> selectNotice(String no);
	
	//좋아요 테이블 가져오기
	@Select("SELECT BOARD_NO FROM LIKES WHERE PUSH_LIKES=#{id}")
	public String[] likeTable(String id);

	//보드테이블에 좋아요 카운트 +1하기
	@Update("UPDATE BOARD SET LIKE_COUNT=LIKE_COUNT+1 WHERE BOARD_NO=#{no}")
	public int addLike(Map<String,String> map);
	//좋아요테이블에 좋아요한 내용 insert하기
	@Insert("INSERT INTO LIKES VALUES(SEQ_LIKESNO.NEXTVAL,SYSDATE,#{no},#{id})")
	public int addLikeTable(Map<String,String> map);
	
	//나의 태그 가져오기
	@Select("SELECT TAG_NAME FROM MEMBERTAG WHERE MEMBER_ID=#{id}")
	public String[] myTag(Map param);
	
	//보드테이블에 좋아요 카운트 -1하기
	@Update("UPDATE BOARD SET LIKE_COUNT=LIKE_COUNT-1 WHERE BOARD_NO=#{no}")
	public int cancelLike(Map<String,String> map);
	//좋아요테이블에 좋아요한 내용 삭제하기
	@Delete("DELETE FROM LIKES WHERE BOARD_NO=#{no} AND PUSH_LIKES=#{id}")
	public int cancelLikeTable(Map<String,String> map);
	//보드태그 테이블 가져오기
	@Select("SELECT * FROM BOARDTAG")
	public List<Map<String,Object>> boardTag();
	
	//카테고리별 게시글 리스트 가져오기
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, B.* FROM (SELECT * FROM BOARD JOIN CATEGORY ON (BOARD_CATE = CATEGORY_NO) WHERE DEL_STATUS=0 AND CATEGORY_NO=#{cate} ORDER BY BOARD_DATE)B) WHERE RNUM BETWEEN #{cPage} and #{numPerpage}")
	public List<Map<String,Object>> selectBoardList(Map<String, String> cate, int cPage, int numPerpage);
	//카테고리별 공지사항 가져오기
	@Select("SELECT NOTICE_TITLE, NOTICE_DATE FROM NOTICE JOIN CATEGORY USING(CATEGORY_NO) WHERE CATEGORY_NO=#{cate} AND NOTICE_STATUS=0 ORDER BY NOTICE_DATE DESC")
	public List<Map<String,Object>> selectBoardNotice(String cate);

	// BOARDTAG 테이블 안에 집어넣기
	@Insert("INSERT INTO BOARDTAG VALUES(SEQ_BOARDTAGNO.NEXTVAL, #{#{boardNo}}, #{tagText})")
	int insertBoardTag(String boardNo, String tagText);

	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, B.* FROM BOARD B ORDER BY BOARD_DATE DESC) WHERE RNUM = 1")
	String getBoardNo();

	@Insert("INSERT INTO TAG VALUES(#{tagText})")
	int insertTag(String tagText);

	@Select("SELECT TAG_NAME FROM TAG WHERE TAG_NAME like '%${tagText}%' ")
	List<Map<String, String>> dupleTagCheck(String tagText);

	@Select("SELECT COUNT(*) FROM BOARD WHERE BOARD_LOC = #{memberloc}")
    int allBoardCount(Object memberloc);

	@Select("SELECT * FROM ( SELECT ROWNUM AS RNUM,a.* FROM (SELECT D.CATEGORY_NAME,B.PREVIEW_IMG,B.BOARD_DATE, B.BOARD_NO, B.BOARD_TITLE, B.BOARD_CONTENT,B.COMMENTS_COUNT AS CNT, B.LIKE_COUNT FROM BOARD B JOIN CATEGORY D ON ( B.BOARD_CATE = D.CATEGORY_NO ) WHERE B.DEL_STATUS=0 AND B.BOARD_LOC =#{memberloc} ORDER BY B.BOARD_DATE DESC )A) WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
	List<Map<String, Object>> allBoard(Pagination pagination, Object memberloc);

	@Select("SELECT N.NOTICE_NO, C.CATEGORY_NO, N.NOTICE_TITLE, N.NOTICE_CONTENT, N.NOTICE_DATE, N.NOTICE_STATUS, C.CATEGORY_NAME FROM NOTICE N JOIN CATEGORY C on (C.CATEGORY_NO = N.CATEGORY_NO) ORDER BY C.CATEGORY_NO")
	List<Map<String, Object>> selectAllBoardNotice();

//	@Select("SELECT * FROM ( SELECT ROWNUM AS RNUM, C.CATEGORY_NAME,B.PREVIEW_IMG, B.BOARD_NO,B.BOARD_TITLE, B.BOARD_CONTENT,(SELECT COUNT(*) AS CNT FROM COMMENTS C WHERE C.BOARD_NO = B.BOARD_NO) AS CNT, B.LIKE_COUNT FROM BOARD B JOIN CATEGORY C ON ( B.BOARD_CATE = C.CATEGORY_NO ) WHERE B.DEL_STATUS=0 AND B.BOARD_CATE=#{cate} AND B.BOARD_LOC = #{memberloc}ORDER BY BOARD_DATE DESC ) WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
	@Select("SELECT * FROM (  SELECT ROWNUM AS RNUM,a.* FROM (SELECT C.CATEGORY_NAME,B.PREVIEW_IMG,B.BOARD_DATE, B.BOARD_NO, B.BOARD_TITLE, B.BOARD_CONTENT,B.COMMENTS_COUNT AS CNT, B.LIKE_COUNT FROM BOARD B JOIN CATEGORY C ON ( B.BOARD_CATE = C.CATEGORY_NO ) WHERE B.DEL_STATUS=0 AND B.BOARD_CATE=#{cate} AND B.BOARD_LOC =#{memberloc} ORDER BY B.BOARD_DATE DESC )A) WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
	List<Map<String, Object>> allCateBoard(Pagination pagination, String cate, Object memberloc);

	@Select("SELECT COUNT(*) FROM BOARD WHERE BOARD_CATE = #{cate} AND BOARD_LOC = #{memberloc}")
	int allcateBoardCount(String cate, Object memberloc);

	@Select("SELECT N.NOTICE_NO,C.CATEGORY_NO, N.NOTICE_TITLE, N.NOTICE_CONTENT, N.NOTICE_DATE, N." +
			"NOTICE_STATUS, C.CATEGORY_NAME FROM NOTICE N JOIN CATEGORY C on (C.CATEGORY_NO = N.CATEGORY_NO) where c.category_no = #{cate} order by n.notice_date desc")
	List<Map<String, Object>> selectAllCateNotice(String cate);

	@Select("SELECT CATEGORY_NAME FROM CATEGORY WHERE CATEGORY_NO = #{cate}")
	Map<String, Object> selectCateName(String cate);

	//boardTAG 안에 집어넣기
//	strBoardNo, tagText
	@Insert("INSERT INTO BOARDTAG VALUES(SEQ_BOARDTAGNO.NEXTVAL,#{strBoardNo}, #{tagText})")
	int boardTagFromform(String strBoardNo, String tagText);

	@Insert("INSERT INTO TAG VALUES (#{tagText})")
	int TagFromform(String tagText);

	//댓글등록 메소드
	@Insert("INSERT INTO COMMENTS VALUES(SEQ_COMMENTNO.NEXTVAL, #{boardNo}, #{commentContent}, SYSDATE, DEFAULT, DEFAULT, DEFAULT, #{commentWriter}, DEFAULT)")
	int commentWrite(Map<String, String> param);

	//게시글의 댓글수 변경
	@Update("UPDATE BOARD SET COMMENTS_COUNT=COMMENTS_COUNT+#{count} WHERE BOARD_NO=#{boardNo}")
	int commentCountUpdate(int count, String boardNo);

	//게시글 댓글 삭제
	@Update("UPDATE COMMENTS SET DEL_STATUS=1 WHERE BOARD_NO=#{boardNo} AND COMMENT_NO=#{commentNo}")
	int commentDelete(String boardNo, String commentNo);


	@Select("SELECT * FROM ( SELECT BOARD_NO, BOARD_DATE, BOARD_TITLE FROM BOARD ORDER BY  BOARD_DATE DESC ) WHERE ROWNUM = 1")
    String getBoardNoFromForm();

	//보드뷰에 해시태그 넣기
	@Select("SELECT TAG_NAME FROM BOARDTAG WHERE BOARD_NO=#{boardNo}")
	List<String> boardTagList(String boardNo);

	//전체글 리스트에 해시태그 넣기
	@Select("SELECT BOARD_NO, TAG_NAME FROM BOARDTAG")
	List<Map<String,String>> selectAllBoardTag();
	//게시글 삭제
	@Update("UPDATE BOARD SET DEL_STATUS=1 WHERE BOARD_NO=#{no}")
	int boardDelete(String no);
	//댓글테이블의 boardno가져오기
	@Select("SELECT BOARD_NO FROM COMMENTS")
	String[] allComments();
	//해당 보드번호의 댓글row들 del_status=1로 수정하기
	@Update("UPDATE COMMENTS SET DEL_STATUS=1 WHERE BOARD_NO=#{no}")
	int commentsDelete(String no);
	//해당 보드번호의 태그 row들 삭제하기
	@Delete("DELETE FROM BOARDTAG WHERE BOARD_NO=#{no}")
	int boardTagDelete(String no);


	//String query = "T D.CATEGORY_NAME,B.PREVIEW_IMG,B.BOARD_DATE, B.BOARD_NO, B.BOARD_TITLE, B.BOARD_CONTENT,B.COMMENTS_COUNT AS CNT, B.LIKE_COUNT FROM BOARD B JOIN CATEGORY D ON ( B.BOARD_CATE = D.CATEGORY_NO ) WHERE B.DEL_STATUS=0 AND B.BOARD_LOC =#{memberloc} ORDER BY B.BOARD_DATE DESC )A) WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}\")"
	@Select("SELECT * FROM (SELECT ROWNUM as rnum, C.CATEGORY_NAME,B.PREVIEW_IMG,B.BOARD_DATE, B.BOARD_NO, B.BOARD_TITLE, B.BOARD_CONTENT, B.COMMENTS_COUNT AS CNT, B.LIKE_COUNT  FROM BOARD B JOIN CATEGORY C ON ( B.BOARD_CATE = C.CATEGORY_NO ) ${condition}) WHERE RNUM BETWEEN ${pagination.firstRecordIndex} and ${pagination.lastRecordIndex} ORDER BY BOARD_DATE DESC")
	List<Map<String,Object>> searchBoardList(Pagination pagination, Object condition);

	@Select("SELECT COUNT(*) FROM (SELECT ROWNUM as rnum, C.CATEGORY_NAME,B.PREVIEW_IMG,B.BOARD_DATE, B.BOARD_NO, B.BOARD_TITLE, B.BOARD_CONTENT, B.COMMENTS_COUNT AS CNT, B.LIKE_COUNT  FROM BOARD B JOIN CATEGORY C ON ( B.BOARD_CATE = C.CATEGORY_NO ) ${condition})")
	int searchBoardCount(Object condition);

	// 게시글 수정
	@Update("UPDATE BOARD SET BOARD_TITLE = #{param.board-title}, BOARD_CONTENT =#{param.boardContent}, PREVIEW_IMG=#{imgName} WHERE BOARD_NO = #{param.boardNo}")
	int modifyBoard(Map param, String imgName);


	//댓글 수정
	@Update("UPDATE COMMENTS SET COMMENT_CONTENT=#{commentContent} WHERE BOARD_NO=#{boardNo} AND COMMENT_NO=#{commentNo}")
	int commentModify(Map<String, String> param);

	//모든 지역 게시글 갯수
	@Select("SELECT COUNT(*) FROM BOARD")
	int allLocBoardCount();

	//모든 지역 게시글 리스트
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, B.* FROM BOARD WHERE DEL_STATUS=0 ORDER BY BOARD_DATE)B) WHERE RNUM BETWEEN #{firstRecordIndex} and #{lastRecordIndex}")
	List<Map<String, Object>> allLocBoard(Pagination pagination);

	//모든지역 / 카테고리별 리스트 갯수
	@Select("SELECT COUNT(*) FROM BOARD WHERE CATEGORY_NO=#{cate}")
	int allcateLocBoardCount(String cate);

	@Select("SELECT * FROM (  SELECT ROWNUM AS RNUM,a.* FROM (SELECT C.CATEGORY_NAME,B.PREVIEW_IMG,B.BOARD_DATE, B.BOARD_NO, B.BOARD_TITLE, B.BOARD_CONTENT,B.COMMENTS_COUNT AS CNT, B.LIKE_COUNT FROM BOARD B JOIN CATEGORY C ON ( B.BOARD_CATE = C.CATEGORY_NO ) WHERE B.DEL_STATUS=0 AND B.BOARD_CATE=#{cate} ORDER BY B.BOARD_DATE DESC )A) WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
	List<Map<String, Object>> allCateLocBoard(Pagination pagination, String cate);

}

