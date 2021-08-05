package com.chairking.poom.mywrite.model.dao;

import com.chairking.poom.board.model.vo.Board;
import com.chairking.poom.mywrite.mapper.MywriteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface MywriteDao {

    int countMyWrite(MywriteMapper mapper);

    List<Map<String, Object>> MywriteList(MywriteMapper mapper, int cPage, int numPerpage, Object memberId);

    int countMyComment(MywriteMapper mapper);

    List<Map<String, Object>> MyCommentList(MywriteMapper mapper, int cPage, int numPerpage);

    List<Map<String, Object>> commentCount(MywriteMapper mapper, int cPage, int numPerpage);
}
