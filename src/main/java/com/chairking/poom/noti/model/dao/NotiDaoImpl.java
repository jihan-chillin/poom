package com.chairking.poom.noti.model.dao;

import com.chairking.poom.noti.mapper.NotiMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public int insertLikesNotiData(NotiMapper mapper, String num) {
        return mapper.insertLikesNotiData(num);
    }

    @Override
    public int insertMessageNotiData(NotiMapper mapper, String num) {
        return mapper.insertMessageNotiData(num);
    }

    @Override
    public int insertCommentNotiData(NotiMapper mapper, String num) {
        return mapper.insertCommentNotiData(num);
    }
}
