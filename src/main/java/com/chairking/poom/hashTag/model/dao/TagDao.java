package com.chairking.poom.hashTag.model.dao;

import com.chairking.poom.hashTag.mapper.TagMapper;

import java.util.Map;

public interface TagDao {
    int insertTag(TagMapper mapper, String tag);
}
