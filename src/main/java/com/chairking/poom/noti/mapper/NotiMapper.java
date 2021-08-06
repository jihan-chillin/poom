package com.chairking.poom.noti.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface NotiMapper {

    @Select("select BOARD_NO from BOARD where MEMBER_ID=#{memberId} ")
    public List getEnrolledMyBoardNo(String memberId);

    @Select("select * from likes where BOARD_NO=#{boardNo}")
    public String getEnrolledLikedNo(String boardNo);

    @Insert("insert into NOTIFICATION values(seq_notificationno.nextval,'2','like',default,default,'0',#{boardNo},'0',#{loginId})")
    public int insertLikesNotiData(String boardNo,String loginId);
    @Insert("insert into NOTIFICATION values(seq_notificationno.nextval,'1','msg',default,default,'0','0',#{messageNo},#{loginId})")
    public int insertMessageNotiData(String messageNo,String loginId);
    @Insert("insert into NOTIFICATION values(seq_notificationno.nextval,'0','cmt',default,default,#{commentNo},'0','0',#{loginId})")
    public int insertCommentNotiData(String commentNo,String loginId);
    @Select("select member_id from board where BOARD_NO=#{boardNo}")
    public String getBoardWriter(String boardNo);
    @Select("select * from notification where member_id=#{loginId} and NOT_DEL ='0' and NOT_CHECK ='0'")
    public List<Map<String,String>> getMyNotiData(String loginId);
    @Select("select board_title from board where board_no=#{boardNo} and del_status='0'")
    public String getBoardTitleFromBoardNo(String boardNo);
    @Select("select BOARD_TITLE from COMMENTS join BOARD B on B.BOARD_NO = COMMENTS.BOARD_NO where COMMENTS.COMMENT_NO=#{commentNo]")
    public String getBoardTitleFromCommentNo(String commentNo);
    @Select("select msg_content from message where msg_no=#{msgNo}")
    public String getMsgContentFromMsgNo(String msgNo);
    @Select("select deleteNotifyToDelStatus(#{boardNo}) from dual")
    public int deleteNotiBoardDelStatus(String boardNo);
}
