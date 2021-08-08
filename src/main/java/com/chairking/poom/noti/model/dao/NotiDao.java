package com.chairking.poom.noti.model.dao;

import com.chairking.poom.noti.mapper.NotiMapper;

import java.util.List;
import java.util.Map;

public interface NotiDao {
    List getEnrolledMyBoardNo(NotiMapper mapper,String memberId);
    String getEnrolledLikedNo(NotiMapper mapper,String boardNo);
    void insertLikesNotiData(NotiMapper mapper,String num,String loginId);
    int insertMessageNotiData(NotiMapper mapper,String num,String loginId);
    int insertCommentNotiData(NotiMapper mapper,String num,String loginId);
    String getBoardWriter(NotiMapper mapper,String boardNo);
    List<Map<String,String>> getMyNotiData(NotiMapper mapper,String loginId);
    Map<String,String> getBoardTitleFromBoardNo(NotiMapper mapper,String boardNo);
    Map<String,String> getBoardTitleFromCommentNo(NotiMapper mapper,String commentNo);
    Map<String,String> getMsgContentFromMsgNo(NotiMapper mapper,String msgNo);
    void deleteNotiBoardDelStatus(NotiMapper mapper,String boardNo);
    void deleteNotifyComment(NotiMapper mapper,String no);
    void deleteNotifyMessage(NotiMapper mapper,String no);
    void deleteNotifyLikes(NotiMapper mapper,String no);
    void updateNotifyComment(NotiMapper mapper,String no);
    void updateNotifyMessage(NotiMapper mapper,String no);
    void updateNotifyLikes(NotiMapper mapper,String no);
}
