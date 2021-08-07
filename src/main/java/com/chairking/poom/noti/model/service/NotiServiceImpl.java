package com.chairking.poom.noti.model.service;

import com.chairking.poom.noti.mapper.NotiMapper;
import com.chairking.poom.noti.model.dao.NotiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotiServiceImpl implements NotiService {
    @Autowired
    private NotiMapper mapper;

    @Autowired
    private NotiDao dao;

    @Override
    public List getEnrolledMyBoardNo(String memberId) {
        return dao.getEnrolledMyBoardNo(mapper,memberId);
    }

    @Override
    public String getEnrolledLikedNo(String boardNo) {
        return dao.getEnrolledLikedNo(mapper,boardNo);
    }

    @Override
    public void insertLikesNotiData(String num,String loginId) {
         dao.insertLikesNotiData(mapper,num,loginId);
    }

    @Override
    public int insertMessageNotiData(String num,String loginId) {
        return dao.insertMessageNotiData(mapper,num,loginId);
    }

    @Override
    public int insertCommentNotiData(String num,String loginId) {
        return dao.insertCommentNotiData(mapper,num,loginId);
    }

    @Override
    public String getBoardWriter(String boardNo) {
        return dao.getBoardWriter(mapper,boardNo);
    }

    @Override
    public List<Map<String, String>> getMyNotiData(String loginId) {
        return dao.getMyNotiData(mapper,loginId);
    }

    @Override
    public Map<String,String> getBoardTitleFromBoardNo(String boardNo) {
        return dao.getBoardTitleFromBoardNo(mapper,boardNo);
    }

    @Override
    public Map<String,String> getBoardTitleFromCommentNo(String commentNo) {
        return dao.getBoardTitleFromCommentNo(mapper,commentNo);
    }

    @Override
    public Map<String,String> getMsgContentFromMsgNo(String msgNo) {
        return dao.getMsgContentFromMsgNo(mapper,msgNo);
    }

    @Override
    public void deleteNotiBoardDelStatus(String boardNo) {
        dao.deleteNotiBoardDelStatus(mapper,boardNo);
    }

    @Override
    public void deleteNotifyComment(String no) {
        dao.deleteNotifyComment(mapper,no);
    }

    @Override
    public void deleteNotifyMessage(String no) {
        dao.deleteNotifyMessage(mapper,no);
    }

    @Override
    public void deleteNotifyLikes(String no) {
        dao.deleteNotifyLikes(mapper,no);
    }

    @Override
    public void changeNotifyType(String no) {
        dao.changeNotifyType(mapper,no);
    }

}
