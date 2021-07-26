package com.chairking.poom.hashTag.model.dao;

import com.chairking.poom.hashTag.mapper.TagMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TagDaoImpl implements TagDao{
    @Override
    public int insertTag(TagMapper mapper, Map tag) {
        return mapper.insertTag(tag);
    }
}
