package com.chairking.poom.noti.model.dao;

import com.chairking.poom.noti.mapper.NotiMapper;

import java.util.List;
import java.util.Map;

public interface NotiDao {
    List getEnrolledMyBoardNo(NotiMapper mapper,String memberId);
    String getEnrolledLikedNo(NotiMapper mapper,String boardNo);
    int insertLikesNotiData(NotiMapper mapper,String num,String loginId);
    int insertMessageNotiData(NotiMapper mapper,String num,String loginId);
    int insertCommentNotiData(NotiMapper mapper,String num,String loginId);
    List<Map<String,String>> getMyNotiData(NotiMapper mapper,String loginId);
    String getBoardTitleFromBoardNo(NotiMapper mapper,String boardNo);
    String getBoardTitleFromCommentNo(NotiMapper mapper,String commentNo);
    String getMsgContentFromMsgNo(NotiMapper mapper,String msgNo);
    int deleteNotiBoardDelStatus(NotiMapper mapper,String boardNo);

}
