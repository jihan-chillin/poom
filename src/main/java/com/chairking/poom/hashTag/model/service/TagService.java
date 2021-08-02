package com.chairking.poom.hashTag.model.service;

import java.util.List;
import java.util.Map;

public interface TagService {
    int insertTag(String tag);
    List getMyTagData(String loginId);
    int addTag(String keyword);
    int insertMemberTag(String loginId, String keyword);
    int deleteTag(String tagName);
    List<Map<String,String>> searchTag(String keyword);
    String getBoardNo();
    int insertBoardTag(String boardNo, String keyword);
    List<Map<String, Object>> getBoardNoFromTag(String tagName,int cPage, int numPerPage);
}
