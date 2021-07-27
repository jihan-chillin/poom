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
    public int deleteMyTag(TagMapper mapper, String tagName) {
        return mapper.deleteMyTag(tagName);
    }

    @Override
    public List<Map<String, String>> searchTag(TagMapper mapper, String keyword) {
        return mapper.searchTag(keyword);
    }
}
