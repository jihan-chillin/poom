package com.chairking.poom.mywrite.mapper;

import com.chairking.poom.board.model.vo.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MywriteMapper {

    @Select("SELECT COUNT(*) FROM BOARD WHERE MEMBER_ID = 'test'")
    public int countMyWrite();

    @Select("SELECT * FROM (SELECT ROWNUM AS RNUM, MEMBER_ID ,M.MEMBER_NICKNAME, B.BOARD_TITLE, B.BOARD_DATE, B.BOARD_CATE, C.CATEGORY_NAME FROM (SELECT * FROM BOARD  WHERE MEMBER_ID ='test' ORDER BY BOARD_DATE DESC)B JOIN MEMBER M USING(MEMBER_ID) JOIN CATEGORY C ON(B.BOARD_CATE = C.CATEGORY_NO))  WHERE RNUM BETWEEN #{cPage} AND #{numPerpage}")
    List<Map<String, Object>> MywriteMapper(int cPage, int numPerpage);

    @Select("SELECT COUNT(*) FROM COMMENTS WHERE COMMENT_WRITER = 'test'")
    public int countMyComment();

    // 쿼리 수정해야함.
    @Select("SESLECT * FROM (SELECT ROWNUM AS RNUM, C.* FROM (SELECT * FROM COMMENTS ORDER BY COMMENT_NO DESC)C) WHERE RNUM BETWEEN #{cPage} AND #{numPerpage} ")
    public List<Map<String, Object>> MyCommentList(int cPage, int numPerpage);
}
