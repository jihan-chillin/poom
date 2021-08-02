package com.chairking.poom.hashTag.model.dao;

import com.chairking.poom.hashTag.mapper.TagMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TagDaoImpl implements TagDao{
    @Override
    public int insertTag(TagMapper mapper, String tag) {
        return mapper.insertTag(tag);
    }

    @Override
    public List getMyTagData(TagMapper mapper, String loginId) {
        return mapper.getMyTagData(loginId);
    }

    @Override
    public int addTag(TagMapper mapper, String keyword) {
        return mapper.addTag(keyword);
    }

    @Override
    public int insertMemberTag(TagMapper mapper, String loginId, String keyword) {
        return mapper.insertMemberTag(loginId,keyword);
    }

    @Override
    public int deleteTag(TagMapper mapper, String tagName) {
        return mapper.deleteTag(tagName);
    }

    @Override
    public List<Map<String, String>> searchTag(TagMapper mapper, String keyword) {
        return mapper.searchTag(keyword);
    }

    @Override
    public String getBoardNo(TagMapper mapper) {
        return mapper.getBoardNo();
    }

    @Override
    public int insertBoardTag(TagMapper mapper, String boardNo, String keyword) {
        return mapper.insertBoardTag(boardNo,keyword);
    }
}
