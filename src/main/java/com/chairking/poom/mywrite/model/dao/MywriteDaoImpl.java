package com.chairking.poom.mywrite.model.dao;

import com.chairking.poom.board.model.vo.Board;
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
    public List<Map<String, Object>> MywriteList(MywriteMapper mapper, int cPage, int numPerpage) {
        return mapper.MywriteMapper(cPage, numPerpage);
    }

    @Override
    public int countMyComment(MywriteMapper mapper) {
        return mapper.countMyComment();
    }

    @Override
    public List<Map<String, Object>> MyCommentList(MywriteMapper mapper, int cPage, int numPerpage) {
        return mapper.MyCommentList(cPage, numPerpage);
    }
}
