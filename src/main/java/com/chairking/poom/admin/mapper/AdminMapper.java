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

	@Select("SELECT COUNT(*) FROM NOTICE ")
	public int countAllNotice();
	
//	@Select("select category_name as cate, notice_title as noticeTitle, notice_content as noticeContent, notice_date as noticeDate, notice_status as noticeStatus from notice join category using(category_no) order by 4 desc")
//	public List<Notice> allNotice();
	
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
}
