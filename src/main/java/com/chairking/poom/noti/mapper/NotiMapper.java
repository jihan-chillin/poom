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

    @Select("select BOARD_TITLE,board_no from COMMENTS join BOARD B on B.BOARD_NO = COMMENTS.BOARD_NO where COMMENTS.COMMENT_NO=#{commentNo}")
    public Map<String,String> getBoardTitleFromCommentNo(String commentNo);

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

    @Update("update NOTIFICATION set NOT_CHECK = '1' where NOT_NO=#{notiNo}")
    public void changeNotifyType(String notiNo);
}
