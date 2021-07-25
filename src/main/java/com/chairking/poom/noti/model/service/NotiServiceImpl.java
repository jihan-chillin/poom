package com.chairking.poom.noti.model.service;

import com.chairking.poom.noti.mapper.NotiMapper;
import com.chairking.poom.noti.model.dao.NotiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int insertLikesNotiData(String num) {
        return dao.insertLikesNotiData(mapper,num);
    }

    @Override
    public int insertMessageNotiData(String num) {
        return dao.insertMessageNotiData(mapper,num);
    }

    @Override
    public int insertCommentNotiData(String num) {
        return dao.insertCommentNotiData(mapper,num);
    }
}
