package com.chairking.poom.noti.model.dao;

import com.chairking.poom.noti.mapper.NotiMapper;

import java.util.List;

public interface NotiDao {
    List getEnrolledMyBoardNo(NotiMapper mapper,String memberId);
    String getEnrolledLikedNo(NotiMapper mapper,String boardNo);
    int insertLikesNotiData(NotiMapper mapper,String num);
    int insertMessageNotiData(NotiMapper mapper,String num);
    int insertCommentNotiData(NotiMapper mapper,String num);
}
