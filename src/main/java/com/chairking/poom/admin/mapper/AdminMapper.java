package com.chairking.poom.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

	@Select("SELECT COUNT(*) FROM NOTICE ")
	public int countAllNotice();
	
//	@Select("select category_name as cate, notice_title as noticeTitle, notice_content as noticeContent, notice_date as noticeDate, notice_status as noticeStatus from notice join category using(category_no) order by 4 desc")
//	public List<Notice> allNotice();
	
	@Select("select * from notice join category using(category_no) order by notice_date desc")
	public List<Map<String,Object>> allNotice();
}
