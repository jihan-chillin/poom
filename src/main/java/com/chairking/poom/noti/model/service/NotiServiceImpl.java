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
    public int insertLikesNotiData(String num,String loginId) {
        return dao.insertLikesNotiData(mapper,num,loginId);
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
    public List<Map<String, String>> getMyNotiData(String loginId) {
        return dao.getMyNotiData(mapper,loginId);
    }

    @Override
    public String getBoardTitleFromBoardNo(String boardNo) {
        return dao.getBoardTitleFromBoardNo(mapper,boardNo);
    }

    @Override
    public String getBoardTitleFromCommentNo(String commentNo) {
        return dao.getBoardTitleFromCommentNo(mapper,commentNo);
    }

    @Override
    public String getMsgContentFromMsgNo(String msgNo) {
        return dao.getMsgContentFromMsgNo(mapper,msgNo);
    }
}
