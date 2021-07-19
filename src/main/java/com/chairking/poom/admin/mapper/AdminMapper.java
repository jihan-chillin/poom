package com.chairking.poom.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.chairking.poom.admin.model.vo.Notice;

@Mapper
public interface AdminMapper {

	@Select("SELECT COUNT(*) FROM NOTICE ")
	public int countAllNotice();
	
	@Select("select category_name as cate, notice_title as noticeTitle, notice_content as noticeContent, notice_date as noticeDate, notice_status as noticeStatus from notice join category using(category_no) order by 4 desc")
	public List<Notice> allNotice();
}
