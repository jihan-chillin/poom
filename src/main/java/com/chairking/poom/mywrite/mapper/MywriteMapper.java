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

    @Select("SELECT * FROM (SELECT ROWNUM AS RNUM, MEMBER_ID ,M.MEMBER_NICKNAME, B.BOARD_TITLE, B.BOARD_DATE, B.BOARD_CATE, C.CATEGORY_NAME, (SELECT COUNT(*) FROM COMMENTS A WHERE A.BOARD_NO = B.BOARD_NO) AS CNT  FROM (SELECT * FROM BOARD  WHERE MEMBER_ID ='test' AND board.del_status = 0 ORDER BY BOARD_DATE DESC)B JOIN MEMBER M USING(MEMBER_ID) JOIN CATEGORY C ON(B.BOARD_CATE = C.CATEGORY_NO))  WHERE RNUM BETWEEN #{cPage} AND #{numPerpage}")
    List<Map<String, Object>> MywriteMapper(int cPage, int numPerpage);

    @Select("SELECT COUNT(*) FROM COMMENTS WHERE COMMENT_WRITER = 'test'")
    public int countMyComment();

    @Select("SELECT * FROM ( SELECT ROWNUM AS RNUM,  M.MEMBER_NICKNAME, B.BOARD_TITLE, C.COMMENT_CONTENT, C.COMMENT_DATE FROM (SELECT * FROM COMMENTS WHERE COMMENT_WRITER ='test' ORDER BY COMMENT_NO DESC)C JOIN BOARD B USING(BOARD_NO) JOIN MEMBER M ON (C.COMMENT_WRITER = M.MEMBER_ID)) WHERE RNUM BETWEEN 1 AND 100")
    public List<Map<String, Object>> MyCommentList(int cPage, int numPerpage);

    @Select("SELECT * FROM ( SELECT ROWNUM AS RNUM, B.BOARD_TITLE, (SELECT COUNT(*) FROM COMMENTS C WHERE C.BOARD_NO = B.BOARD_NO) FROM BOARD B ORDER BY BOARD_NO DESC) WHERE RNUM BETWEEN #{cPage} AND #{numPerpage}")
    public  List<Map<String, Object>>commentCount(int cPage, int numPerpage);
}
