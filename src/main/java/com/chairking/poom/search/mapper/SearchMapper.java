package com.chairking.poom.search.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
    String query = "SELECT rownum as rnum, A.*, C.category_name FROM "
            + "(SELECT notice_no as search_no, "
            + "'notice' as con_type, "
            + "category_no, "
            + "notice_title as title, "
            + "SUBSTR(notice_content, 0, 15) || '...' as content, "
            + "notice_date as write_date, "
            + "'관리자' as member_id from notice "
            + "UNION "
            + "SELECT board_no as search_no, "
            + "'board' as con_type, "
            + "board_cate as category_no, "
            + "board_title as title, "
            + "SUBSTR(board_content, 0, 15) || '...' as content, "
            + "board_date as write_date, "
            + "member_id from board) A join CATEGORY C on A.category_no = C.category_no "
            + "${where}";

    @Select(query)
    public List<Map<String,Object>> searchList(String where);


    //페이징 처리 카운트 세기
    String count = "SELECT COUNT (*) FROM ( "
            + "SELECT A.*, C.category_name FROM "
                    + "(SELECT notice_no as search_no, "
                    + "'notice' as con_type, "
                    + "category_no, "
                    + "notice_title as title, "
                    + "SUBSTR(notice_content, 0, 15) || '...' as content, "
                    + "notice_date as write_date, "
                    + "'관리자' as member_id from notice "
                    + "UNION "
                    + "SELECT board_no as search_no, "
                    + "'board' as con_type, "
                    + "board_cate as category_no, "
                    + "board_title as title, "
                    + "SUBSTR(board_content, 0, 15) || '...' as content, "
                    + "board_date as write_date, "
                    + "member_id from board) A join CATEGORY C on A.category_no = C.category_no )";
    @Select(count)
    public int searchCount();
}
