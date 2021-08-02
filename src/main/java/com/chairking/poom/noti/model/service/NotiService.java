package com.chairking.poom.noti.model.service;

import java.util.List;
import java.util.Map;

public interface NotiService {
    List getEnrolledMyBoardNo(String memberId);
    String getEnrolledLikedNo(String boardNo);
    int insertLikesNotiData(String num,String loginId);
    int insertMessageNotiData(String num,String loginId);
    int insertCommentNotiData(String num,String loginId);
    List<Map<String,String>> getMyNotiData(String loginId);
    String getBoardTitleFromBoardNo(String boardNo);
    String getBoardTitleFromCommentNo(String commentNo);
    String getMsgContentFromMsgNo(String msgNo);
    int deleteNotiBoardDelStatus(String boardNo);
}
