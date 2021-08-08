package com.chairking.poom.noti.model.service;

import java.util.List;
import java.util.Map;

public interface NotiService {
    List getEnrolledMyBoardNo(String memberId);
    String getEnrolledLikedNo(String boardNo);
    void insertLikesNotiData(String num,String loginId);
    int insertMessageNotiData(String num,String loginId);
    int insertCommentNotiData(String num,String loginId);
    String getBoardWriter(String boardNo);
    List<Map<String,String>> getMyNotiData(String loginId);
    Map<String,String> getBoardTitleFromBoardNo(String boardNo);
    Map<String,String> getBoardTitleFromCommentNo(String commentNo);
    Map<String,String> getMsgContentFromMsgNo(String msgNo);
    void deleteNotiBoardDelStatus(String boardNo);
    void deleteNotifyComment(String no);
    void deleteNotifyMessage(String no);
    void deleteNotifyLikes(String no);
    void updateNotifyComment(String no);
    void updateNotifyMessage(String no);
    void updateNotifyLikes(String no);

}
