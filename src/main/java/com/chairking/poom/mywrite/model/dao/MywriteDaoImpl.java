package com.chairking.poom.mywrite.model.dao;

import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.common.Pagination;
import com.chairking.poom.mywrite.mapper.MywriteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MywriteDaoImpl implements MywriteDao{

    @Override
    public int countMyWrite(MywriteMapper mapper) {
        return mapper.countMyWrite();
    }

    @Override
    public List<Map<String, Object>> MywriteList(MywriteMapper mapper, Pagination pagination, Object memberId) {
        return mapper.MywriteList(pagination, memberId);
    }

    @Override
    public int countMyComment(MywriteMapper mapper) {
        return mapper.countMyComment();
    }

    @Override
    public List<Map<String, Object>> MyCommentList(MywriteMapper mapper, Pagination pagination, Object memberId) {
        return mapper.MyCommentList(pagination, memberId);
    }

    @Override
    public List<Map<String, Object>> commentCount(MywriteMapper mapper, int cPage, int numPerpage) {
        return mapper.commentCount(cPage,numPerpage);
    }

    @Override
    public int countMyLike(MywriteMapper mapper) {
        return mapper.countMyLike();
    }

    @Override
    public List<Map<String, Object>> myLikeList(MywriteMapper mapper, Pagination pagination, Object memberId) {
        return mapper.MyLikeList(pagination, memberId);
    }
}
