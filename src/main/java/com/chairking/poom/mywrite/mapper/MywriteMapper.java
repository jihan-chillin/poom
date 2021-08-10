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

    @Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM (SELECT B.BOARD_NO, B.BOARD_TITLE, B.COMMENTS_COUNT, B.BOARD_CONTENT, B.BOARD_DATE, C.CATEGORY_NAME FROM BOARD B JOIN CATEGORY C ON (C.CATEGORY_NO = B.BOARD_CATE) WHERE MEMBER_ID = #{memberId} order by b.board_date desc)A) WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
    List<Map<String, Object>> MywriteList(Pagination pagination,Object memberId);

    @Select("SELECT COUNT(*) FROM COMMENTS WHERE DEL_STATUS = 0")
    public int countMyComment();

    // 내 댓글 가져오기
   @Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM (SELECT C.CATEGORY_NAME, B.BOARD_NO, B.BOARD_TITLE, B.COMMENTS_COUNT, D.COMMENT_CONTENT, D.COMMENT_DATE, D.COMMENT_WRITER FROM BOARD B JOIN CATEGORY C ON(C.CATEGORY_NO = B.BOARD_CATE) JOIN COMMENTS D ON (B.BOARD_NO = D.BOARD_NO) WHERE D.COMMENT_WRITER=#{memberId} ORDER BY D.COMMENT_DATE DESC)A) WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
    public List<Map<String, Object>> MyCommentList(Pagination pagination, Object memberId);

    @Select("SELECT * FROM ( SELECT ROWNUM AS RNUM, B.BOARD_TITLE, (SELECT COUNT(*) FROM COMMENTS C WHERE C.BOARD_NO = B.BOARD_NO) FROM BOARD B ORDER BY BOARD_DATE DESC ) WHERE RNUM BETWEEN #{cPage} AND #{numPerpage}")
    public  List<Map<String, Object>>commentCount(int cPage, int numPerpage);

    // 수정해야함.
    @Select("SELECT COUNT(*) FROM BOARD WHERE DEL_STATUS = 0")
    int countMyLike();


    // 내가 찜함글
    @Select("SELECT * FROM ( SELECT ROWNUM AS RNUM, A.* FROM (SELECT L.BOARD_NO,B.LIKE_COUNT,C.CATEGORY_NAME, B.BOARD_TITLE, B.BOARD_DATE, B.COMMENTS_COUNT, L.LIKES_TIME FROM BOARD B JOIN CATEGORY C ON (C.CATEGORY_NO = B.BOARD_CATE) JOIN LIKES L ON(B.BOARD_NO = L.BOARD_NO) WHERE  l.push_likes = #{memberId} ORDER BY LIKES_TIME DESC)A) WHERE RNUM BETWEEN #{pagination.firstRecordIndex} and #{pagination.lastRecordIndex}")
    List<Map<String, Object>> MyLikeList(Pagination pagination, Object memberId);

}
