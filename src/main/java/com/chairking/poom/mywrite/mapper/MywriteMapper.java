package com.chairking.poom.mywrite.mapper;

import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.common.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MywriteMapper {

    @Select("SELECT COUNT(*) FROM BOARD WHERE DEL_STATUS = 0")
    int countMyWrite();

    @Select("SELECT * FROM (SELECT ROWNUM AS RNUM,B.BOARD_NO, B.BOARD_TITLE, B.COMMENTS_COUNT, B.BOARD_CONTENT, B.BOARD_DATE, C.CATEGORY_NAME FROM BOARD B JOIN CATEGORY C ON (C.CATEGORY_NO = B.BOARD_CATE) WHERE MEMBER_ID = #{memberId} order by b.board_date desc) where RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
    List<Map<String, Object>> MywriteList(Pagination pagination,Object memberId);

    @Select("SELECT COUNT(*) FROM COMMENTS WHERE COMMENT_WRITER = 'test'")
    public int countMyComment();

    @Select("SELECT * FROM (SELECT ROWNUM AS RNUM, C.COMMENT_CONTENT, C.COMMENT_DATE, B.BOARD_TITLE,B.BOARD_NO, M.MEMBER_NICKNAME FROM COMMENTS C JOIN BOARD B ON(C.BOARD_NO = B.BOARD_NO) JOIN MEMBER M ON (M.MEMBER_ID = C.COMMENT_WRITER) ORDER BY COMMENT_NO) WHERE RNUM BETWEEN #{cPage} AND #{numPerpage}")
    public List<Map<String, Object>> MyCommentList(int cPage, int numPerpage);

    @Select("SELECT * FROM ( SELECT ROWNUM AS RNUM, B.BOARD_TITLE, (SELECT COUNT(*) FROM COMMENTS C WHERE C.BOARD_NO = B.BOARD_NO) FROM BOARD B ORDER BY BOARD_DATE DESC ) WHERE RNUM BETWEEN #{cPage} AND #{numPerpage}")
    public  List<Map<String, Object>>commentCount(int cPage, int numPerpage);

    // 수정해야함.
    @Select("SELECT COUNT(*) FROM BOARD WHERE MEMBER_ID = 'kimjihan77'")
    int countMyLike();


    // 내가 찜함글
    @Select("SELECT * FROM (SELECT ROWNUM AS RNUM, M.MEMBER_NICKNAME, C.CATEGORY_NAME,B.BOARD_NO, B.BOARD_TITLE,B.BOARD_DATE,(SELECT COUNT(*) FROM COMMENTS A WHERE A.BOARD_NO = B.BOARD_NO) AS CNT FROM (SELECT * FROM BOARD JOIN LIKES USING (BOARD_NO) WHERE MEMBER_ID=#{memberId} AND DEL_STATUS = 0 ORDER BY BOARD_DATE) B JOIN CATEGORY C ON(C.CATEGORY_NO = B.BOARD_CATE) JOIN MEMBER M USING(MEMBER_ID)) WHERE RNUM BETWEEN #{cPage} AND #{numPerpage}")
    List<Map<String, Object>> MyLikeList(int cPage, int numPerpage, Object memberId);

}
