package com.chairking.poom.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chairking.poom.admin.model.vo.Notice1;
import com.chairking.poom.common.Pagination;

@Mapper
public interface AdminMapper {
	//공지
	@Select("SELECT COUNT(*) FROM NOTICE ")
	public int countAllNotice();
	
	@Select("select * from (select rownum as rnum, a.* from (select * from notice join category using(category_no) order by notice_date desc) a ) where rnum between #{cPage} and #{numPerpage}")
	public List<Map<String,Object>> allNotice(int cPage, int numPerpage);
	
	@Insert("INSERT INTO NOTICE VALUES(SEQ_NOTICENO.NEXTVAL,#{cate},#{noticeTitle},#{noticeContent},sysdate,default)")
	public int insertNotice(Notice1 n);
	
	@Select("SELECT * FROM NOTICE WHERE NOTICE_NO=#{no}")
	public Map<String,Object> selectNotice(String no);
	
	@Update("UPDATE NOTICE SET NOTICE_STATUS=1 WHERE NOTICE_NO=#{no}")
	public int noticeDelete(String no);
	
	@Delete("DELETE FROM NOTICE WHERE NOTICE_NO=#{no}")
	public int realDelete(String no);
	
	@Update("UPDATE NOTICE SET NOTICE_STATUS=0 WHERE NOTICE_NO=#{no}")
	public int changeStatus(String no);
	
	//신고
	//리스트불러오기
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM(select * from board where blame_count != 0 order by ${delStatus})A)WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
	public List<Map<String,Object>> allBoardBlame(Pagination pagination,String delStatus);
	
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM(SELECT * FROM COMMENTS WHERE BLAME_COUNT!=0 ORDER BY ${delStatus})A)WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
	public List<Map<String,Object>> allCommentsBlame(Pagination pagination,String delStatus);
	
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM(SELECT * FROM CHAT WHERE BLAME_COUNT!=0 ORDER BY ${delStatus})A)WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
	public List<Map<String,Object>> allChatBlame(Pagination pagination,String delStatus);
	
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM(SELECT * FROM MEMBER WHERE BLAME_COUNT!=0 ORDER BY ${delStatus})A)WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
	public List<Map<String,Object>> allMemberBlame(Pagination pagination,String delStatus);
	
	//카운트세기
	@Select("SELECT COUNT(*) FROM BOARD WHERE BLAME_COUNT !=0")
	public int allBoardBlameCount();
	@Select("SELECT COUNT(*) FROM COMMENTS WHERE BLAME_COUNT !=0")
	public int allCommentsBlameCount();
	@Select("SELECT COUNT(*) FROM CHAT WHERE BLAME_COUNT !=0")
	public int allChatBlameCount();
	@Select("SELECT COUNT(*) FROM MEMBER WHERE BLAME_COUNT !=0")
	public int allMemberBlameCount();
	
	
	@Insert("INSERT INTO BOARD_BLAME VALUES(SEQ_BRD_BLAME_NO.NEXTVAL,#{no},#{target_mem},sysdate,#{blame_reason})")
	public int insertBoardBlame(Map<String,String> map);
	@Update("UPDATE BOARD SET BLAME_COUNT=BLAME_COUNT+1 WHERE BOARD_NO=#{no}")
	public int updateBrdBlameCount(String no);
	
	@Insert("INSERT INTO COMMENTS_BLAME VALUES(SEQ_BC_BLAME_NO.NEXTVAL,#{target_mem},sysdate,'테스트',#{no},#{blame_reason})")
	public int insertCommentsBlame(Map<String,String> map);
	@Update("UPDATE COMMENTS SET BLAME_COUNT=BLAME_COUNT+1 WHERE COMMENT_NO=#{no}")
	public int updateCommentsBlameCount(String no);
	
	@Insert("INSERT INTO CHAT_BLAME VALUES(SEQ_CHAT_BLAMENO.NEXTVAL,sysdate,#{target_mem},#{no},#{blame_reason})")
	public int insertChatBlame(Map<String,String> map);
	@Update("UPDATE CHAT SET BLAME_COUNT=BLAME_COUNT+1 WHERE CHAT_NO=#{no}")
	public int updateChatBlameCount(String no);
	
	
	@Select("select * from board_blame join board on b_target_board_no=board_no where board_no=#{no}")
	public List<Map<String,Object>> selectBoardBlame(String no);
	@Select("SELECT * FROM BOARD_BLAME WHERE B_TARGET_BOARD_NO=#{no}")
	public List<Map<String,Object>> selectCommentsBlame(String no);
	@Select("SELECT * FROM BOARD_BLAME WHERE B_TARGET_BOARD_NO=#{no}")
	public List<Map<String,Object>> selectChatBlame(String no);
	@Select("SELECT * FROM BOARD_BLAME WHERE B_TARGET_BOARD_NO=#{no}")
	public List<Map<String,Object>> selectMemberBlame(String no);
	
	@Select("SELECT * FROM BOARD_BLAME WHERE B_TARGET_BOARD_NO=4 AND BLAME_REASON LIKE '기타%'")
	public List<Map<String,String>> selectEctAll(Map<String,Object> map);
	
	//삭제 시 del_status 1로 업데이트
	@Update("UPDATE BOARD SET DEL_STATUS=1 WHERE BOARD_NO=#{no}")
	public int deleteBoardBlame(String no);
	@Update("UPDATE COMMENTS SET DEL_STATUS=1 WHERE COMMENT_NO=#{no}")
	public int deleteCommentsBlame(String no);
	@Update("UPDATE CHAT SET DEL_STATUS=1 WHERE CHAT_NO=#{no}")
	public int deleteChatBlame(String no);
	
	
	//결제관리
	//리스트
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM (SELECT PAY_DATE,MEMBER_ID, MEMBER_NICKNAME, ITEM_TYPE FROM PAYMENT JOIN ITEMS USING(ITEM_NO) join MEMBER USING(MEMBER_ID) ORDER BY PAY_DATE DESC)A)WHERE RNUM BETWEEN #{firstRecordIndex} and #{lastRecordIndex} ")
	public List<Map<String,Object>> allPayment(Pagination pagination);
	//카운트
	@Select("SELECT COUNT(*) FROM PAYMENT ORDER BY PAY_DATE DESC")
	public int allPaymentCount();
	//각 날짜별, 아이템별 합계금액
	@Select("SELECT PAY_DATE,ITEM_TYPE, SUM(ITEM_PRICE) AS S FROM PAYMENT JOIN ITEMS USING(ITEM_NO) WHERE PAY_DATE BETWEEN SYSDATE-8 AND SYSDATE-1 GROUP BY ROLLUP(PAY_DATE,ITEM_TYPE)")
	public List<Map<String,Object>> sumAllPayment();
}
