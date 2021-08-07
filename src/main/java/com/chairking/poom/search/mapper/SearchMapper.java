package com.chairking.poom.search.mapper;

import org.apache.ibatis.annotations.*;

import com.chairking.poom.common.Pagination;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
    String query = "SELECT * FROM ( "
            + "SELECT rownum as rnum, B.* FROM ( "
            + "SELECT A.*, C.category_name FROM ( "
            + "SELECT notice_no as search_no, "
            + "'notice' as con_type, "
            + "category_no, "
            + "notice_title as title, "
            + "SUBSTR(notice_content, 0, 15) || '...' as content, "
            + "notice_date as write_date, "
            + "'관리자' as member_id from notice where notice_status = 0 "
            + "UNION "
            + "SELECT board_no as search_no, "
            + "'board' as con_type, "
            + "board_cate as category_no, "
            + "board_title as title, "
            + "SUBSTR(board_content, 0, 15) || '...' as content, "
            + "board_date as write_date, "
            + "member_id from board) A join CATEGORY C on A.category_no = C.category_no "
            + "${where}"
            + ")B) WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}";


    @Select(query)
    public List<Map<String,Object>> searchList(String where, Pagination pagination);


    //페이징 처리 카운트 세기
    String count = "SELECT COUNT (*) FROM ( "
            + "SELECT A.*, C.category_name FROM "
                    + "(SELECT notice_no as search_no, "
                    + "'notice' as con_type, "
                    + "category_no, "
                    + "notice_title as title, "
                    + "SUBSTR(notice_content, 0, 15) || '...' as content, "
                    + "notice_date as write_date, "
                    + "'관리자' as member_id from notice where notice_status = 0 "
                    + "UNION "
                    + "SELECT board_no as search_no, "
                    + "'board' as con_type, "
                    + "board_cate as category_no, "
                    + "board_title as title, "
                    + "SUBSTR(board_content, 0, 15) || '...' as content, "
                    + "board_date as write_date, "
                    + "member_id from board) A join CATEGORY C on A.category_no = C.category_no ) ${where}";
    @Select(count)
    public int searchCount(String where);
}
