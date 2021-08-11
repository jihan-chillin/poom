package com.chairking.poom.popuprank.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PopupRankMapper {
    @Select("select count(l.BOARD_NO) likecount,l.BOARD_NO,b.BOARD_TITLE,m.MEMBER_NICKNAME,m.MEMBER_IMG from LIKES l join BOARD B on l.BOARD_NO = B.BOARD_NO join MEMBER m on m.MEMBER_ID = b.MEMBER_ID where (l.LIKES_TIME between sysdate-7 and sysdate) and (b.DEL_STATUS='0') group by l.BOARD_NO,b.BOARD_TITLE,m.MEMBER_NICKNAME,m.MEMBER_IMG order by likecount desc")
    public List<Map> memberData();
}
