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
    public void insertLikesNotiData(NotiMapper mapper, String num,String loginId) {
        mapper.insertLikesNotiData(num,loginId);
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
    public Map<String,String> getBoardTitleFromBoardNo(NotiMapper mapper, String boardNo) {
        return mapper.getBoardTitleFromBoardNo(boardNo);
    }

    @Override
    public Map<String,String> getBoardTitleFromCommentNo(NotiMapper mapper, String commentNo) {
        return mapper.getBoardTitleFromCommentNo(commentNo);
    }

    @Override
    public Map<String,String> getMsgContentFromMsgNo(NotiMapper mapper, String msgNo) {
        return mapper.getMsgContentFromMsgNo(msgNo);
    }

    @Override
    public void deleteNotiBoardDelStatus(NotiMapper mapper, String boardNo) {
        mapper.deleteNotiBoardDelStatus(boardNo);
    }

    @Override
    public void deleteNotifyComment(NotiMapper mapper, String no) {
        mapper.deleteNotifyComment(no);
    }

    @Override
    public void deleteNotifyMessage(NotiMapper mapper, String no) {
        mapper.deleteNotifyMessage(no);
    }

    @Override
    public void deleteNotifyLikes(NotiMapper mapper, String no) {
        mapper.deleteNotifyLikes(no);
    }

}
