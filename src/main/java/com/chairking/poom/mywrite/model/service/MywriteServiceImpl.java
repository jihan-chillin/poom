package com.chairking.poom.mywrite.model.service;

import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.common.Pagination;
import com.chairking.poom.mywrite.mapper.MywriteMapper;
import com.chairking.poom.mywrite.model.dao.MywriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MywriteServiceImpl implements MywriteService{

    @Autowired
    private MywriteDao dao;

    @Autowired
    private MywriteMapper mapper;

    @Override
    public int countMyWrite() {
        return dao.countMyWrite(mapper);
    }

    @Override
    public List<Map<String, Object>> MywriteList(Pagination pagination, Object memberId) {
        return dao.MywriteList(mapper, pagination, memberId);
    }

    @Override
    public int countMyComment() {
        return dao.countMyComment(mapper);
    }

    @Override
    public List<Map<String, Object>> MyCommentList(int cPage, int numPerpage) {

        return dao.MyCommentList(mapper, cPage, numPerpage);
    }

    @Override
    public  List<Map<String, Object>> commentCount(int cPage, int numPerpage) {
        return dao.commentCount(mapper,cPage, numPerpage);
    }

    @Override
    public int countMyLike() {
        return dao.countMyLike(mapper);
    }

    @Override
    public List<Map<String, Object>> MyLikeList(int cPage, int numPerpage, Object memberId) {
        return dao.myLikeList(mapper, cPage, numPerpage, memberId);
    }
}
