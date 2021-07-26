package com.chairking.poom.noti.model.service;

import java.util.List;

public interface NotiService {
    List getEnrolledMyBoardNo(String memberId);
    String getEnrolledLikedNo(String boardNo);
    int insertLikesNotiData(String num);
    int insertMessageNotiData(String num);
    int insertCommentNotiData(String num);
}
