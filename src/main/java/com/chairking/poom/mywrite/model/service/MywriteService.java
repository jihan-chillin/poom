package com.chairking.poom.mywrite.model.service;

import com.chairking.poom.common.Pagination;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

public interface MywriteService {


//    List selectMywriteList(int cPage, int numPerpage);

    // 내가 쓴 글 갯수
    int countMyWrite();
    // 내가 쓴 글 리스트
    List<Map<String, Object>> MywriteList(Pagination pagination, Object memberId);

    // 내가 쓴 댓글 갯수
    int countMyComment();
    // 내가 쓴 댓글 리스트
    List<Map<String, Object>> MyCommentList(int cPage, int numPerpage);

    // 해당 글에 있는 댓글 가져오기
    List<Map<String, Object>> commentCount(int cPage, int numPerpage);

    int countMyLike();

    List<Map<String, Object>> MyLikeList(int cPage, int numPerpage, Object memberId);
}
