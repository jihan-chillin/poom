package com.chairking.poom.noti.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface NotiMapper {

    @Select("select BOARD_NO from BOARD where MEMBER_ID=#{memberId} ")
    public List getEnrolledMyBoardNo(String memberId);

    @Select("select * from likes where BOARD_NO=#{boardNo}")
    public String getEnrolledLikedNo(String boardNo);

    @Insert("insert into NOTIFICATION values(seq_notificationno.nextval,'2','like',default,default,'0',#{boardNo},'0',#{loginId})")
    public void insertLikesNotiData(String boardNo,String loginId);

    @Insert("insert into NOTIFICATION values(seq_notificationno.nextval,'1','msg',default,default,'0','0',#{messageNo},#{loginId})")
    public int insertMessageNotiData(String messageNo,String loginId);

    @Insert("insert into NOTIFICATION values(seq_notificationno.nextval,'0','cmt',default,default,#{commentNo},'0','0',#{loginId})")
    public int insertCommentNotiData(String commentNo,String loginId);

    @Select("select member_id from board where BOARD_NO=#{boardNo}")
    public String getBoardWriter(String boardNo);

    @Select("select * from ( select * from notification where member_id=#{loginId} order by to_number(NOT_NO) desc) where ROWNUM between 1 and 10")
    public  List<Map<String,String>> getMyNotiData(String loginId);

    @Select("select b.BOARD_NO,b.BOARD_TITLE from board b where b.DEL_STATUS='0' and b.BOARD_NO=#{boardNo}")
    public Map<String,String> getBoardTitleFromBoardNo(String boardNo);

    @Select("select * from ( select B.BOARD_TITLE, B.board_no from COMMENTS c join BOARD B on B.BOARD_NO = c.BOARD_NO where b.BOARD_NO = #{boardNo} ) where ROWNUM = 1")
    public Map<String,String> getBoardTitleFromCommentNo(String boardNo);

    @Select("select msg_content,msg_no from message where msg_no=#{msgNo}")
    public Map<String,String> getMsgContentFromMsgNo(String msgNo);

    @Select("select deleteNotifyToDelStatus(#{boardNo}) from dual")
    public int deleteNotiBoardDelStatus(String boardNo);

    @Delete("delete from NOTIFICATION where comment_no=#{commentNo}")
    public void deleteNotifyComment(String commentNo);

    @Delete("delete from NOTIFICATION where msg_no=#{msg_no}")
    public void deleteNotifyMessage(String messageNo);

    @Delete("delete from NOTIFICATION where board_no=#{board_no}")
    public void deleteNotifyLikes(String boardNo);

    @Update("update NOTIFICATION set NOT_CHECK = '1' where not_no=#{notNo}")
    public void updateNotifyComment(String notNo);

    @Update("update NOTIFICATION set NOT_CHECK = '1' where not_no=#{notNo}")
    public void updateNotifyMessage(String notNo);

    @Update("update NOTIFICATION set NOT_CHECK = '1' where not_no=#{notNo}")
    public void updateNotifyLikes(String notNo);
}
