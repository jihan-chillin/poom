package com.chairking.poom.noti.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotiMapper {

    @Select("select BOARD_NO from BOARD where MEMBER_ID=#{memberId} ")
    public List getEnrolledMyBoardNo(String memberId);

    @Select("select * from likes where BOARD_NO=#{boardNo}")
    public String getEnrolledLikedNo(String boardNo);

    @Insert("insert into NOTIFICATION values (seq_notificationno.nextval,'2','like',default,default,'',#{boardNo},'')")
    public int insertLikesNotiData(String boardNo);
    @Insert("insert into NOTIFICATION values (seq_notificationno.nextval,'1','msg',default,default,'','',#{messageNo})")
    public int insertMessageNotiData(String messageNo);
    @Insert("insert into NOTIFICATION values (seq_notificationno.nextval,'0','cmt',default,default,#{commentNo},'','')")
    public int insertCommentNotiData(String commentNo);
}
