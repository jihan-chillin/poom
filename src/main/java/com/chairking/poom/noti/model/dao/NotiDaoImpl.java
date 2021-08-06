package com.chairking.poom.noti.model.dao;

import com.chairking.poom.noti.mapper.NotiMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class NotiDaoImpl implements NotiDao{
    @Override
    public List getEnrolledMyBoardNo(NotiMapper mapper,String memberId) {
        return mapper.getEnrolledMyBoardNo(memberId);
    }

    @Override
    public String getEnrolledLikedNo(NotiMapper mapper, String boardNo) {
        return mapper.getEnrolledLikedNo(boardNo);
    }

    @Override
    public int insertLikesNotiData(NotiMapper mapper, String num,String loginId) {
        return mapper.insertLikesNotiData(num,loginId);
    }

    @Override
    public int insertMessageNotiData(NotiMapper mapper, String num,String loginId) {
        return mapper.insertMessageNotiData(num,loginId);
    }

    @Override
    public int insertCommentNotiData(NotiMapper mapper, String num,String loginId) {
        return mapper.insertCommentNotiData(num,loginId);
    }

    @Override
    public String getBoardWriter(NotiMapper mapper, String boardNo) {
        return mapper.getBoardWriter(boardNo);
    }

    @Override
    public List<Map<String, String>> getMyNotiData(NotiMapper mapper, String loginId) {
        return mapper.getMyNotiData(loginId);
    }

    @Override
    public String getBoardTitleFromBoardNo(NotiMapper mapper, String boardNo) {
        return mapper.getBoardTitleFromBoardNo(boardNo);
    }

    @Override
    public String getBoardTitleFromCommentNo(NotiMapper mapper, String commentNo) {
        return mapper.getBoardTitleFromCommentNo(commentNo);
    }

    @Override
    public String getMsgContentFromMsgNo(NotiMapper mapper, String msgNo) {
        return mapper.getMsgContentFromMsgNo(msgNo);
    }

    @Override
    public int deleteNotiBoardDelStatus(NotiMapper mapper, String boardNo) {
        return mapper.deleteNotiBoardDelStatus(boardNo);
    }
}
