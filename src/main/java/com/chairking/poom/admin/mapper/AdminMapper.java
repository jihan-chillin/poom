package com.chairking.poom.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.chairking.poom.admin.model.vo.Notice;

@Mapper
public interface AdminMapper {
	//공지
	@Select("SELECT COUNT(*) FROM NOTICE ")
	public int countAllNotice();
	
	@Select("select * from (select rownum as rnum, a.* from (select * from notice join category using(category_no) order by notice_date desc) a ) where rnum between #{cPage} and #{numPerpage}")
	public List<Map<String,Object>> allNotice(int cPage, int numPerpage);
	
	@Insert("INSERT INTO NOTICE VALUES(SEQ_NOTICENO.NEXTVAL,#{cate},#{noticeTitle},#{noticeContent},sysdate,default)")
	public int insertNotice(Notice n);
	
	@Select("SELECT * FROM NOTICE WHERE NOTICE_NO=#{no}")
	public Map<String,Object> selectNotice(String no);
	
	@Update("UPDATE NOTICE SET NOTICE_STATUS=1 WHERE NOTICE_NO=#{no}")
	public int noticeDelete(String no);
	
	@Delete("DELETE FROM NOTICE WHERE NOTICE_NO=#{no}")
	public int realDelete(String no);
	
	@Update("UPDATE NOTICE SET NOTICE_STATUS=0 WHERE NOTICE_NO=#{no}")
	public int changeStatus(String no);
	
	//신고
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM(SELECT * FROM BOARD_BLAME JOIN BOARD ON B_TARGET_BOARD_NO = BOARD_NO ORDER BY B_BLAME_DATE DESC)A)WHERE RNUM BETWEEN #{cPage} and #{numPerpage}")
	public List<Map<String,Object>> allBoardBlame(int cPage, int numPerpage);
	
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM(SELECT * FROM COMMENTS_BLAME JOIN COMMENTS ON BC_TARGET_COMMENT = COMMENT_NO ORDER BY BC_BLAME_DATE DESC)A)WHERE RNUM BETWEEN #{cPage} and #{numPerpage}")
	public List<Map<String,Object>> allCommentsBlame(int cPage, int numPerpage);
	
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM(SELECT * FROM CHAT_BLAME JOIN CHAT ON CH_TARGET_CHAT = CHAT_NO ORDER BY CH_BLAME_DATE DESC)A)WHERE RNUM BETWEEN #{cPage} and #{numPerpage}")
	public List<Map<String,Object>> allChatBlame(int cPage, int numPerpage);
	
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM(SELECT * FROM MEMBER WHERE DEN_STATUS IN('1','2','3') ORDER BY B_BLAME_DATE DESC)A)WHERE RNUM BETWEEN #{cPage} and #{numPerpage}")
	public List<Map<String,Object>> allMemberBlame(int cPage, int numPerpage);
	
	@Insert("INSERT INTO BOARD_BLAME VALUES(SEQ_BRD_BLAME_NO.NEXTVAL,#{no},#{target_mem},sysdate,#{blame_reason})")
	public int insertBoardBlame(Map<String,String> map);
	@Update("UPDATE BOARD SET BLAME_COOUNT=BLAME_COUNT+1 WHERE BOARD_NO=#{no}")
	public int updateBrdBlameCount(String no);
	
	@Insert("INSERT INTO COMMENTS_BLAME VALUES(SEQ_BC_BLAME_NO.NEXTVAL,#{target_mem},sysdate,'테스트',#{no},#{blame_reason})")
	public int insertCommentsBlame(Map<String,String> map);
	@Update("UPDATE COMMENTS SET BC_BLAME_COUNT=BC_BLAME_COUNT+1 WHERE COMMENT_NO=#{no}")
	public int updateCommentsBlameCount(String no);
	
	@Insert("INSERT INTO CHAT_BLAME VALUES(SEQ_CHAT_BLAMENO.NEXTVAL,sysdate,#{target_mem},#{no},#{blame_reason})")
	public int insertChatBlame(Map<String,String> map);
	@Update("UPDATE CHAT SET CH_BLAME_COUNT=CH_BLAME_COUNT+1 WHERE CHAT_NO=#{no}")
	public int updateChatBlameCount(String no);
}
