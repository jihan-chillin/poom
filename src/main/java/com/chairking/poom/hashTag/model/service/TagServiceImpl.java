package com.chairking.poom.hashTag.model.service;

import com.chairking.poom.hashTag.mapper.TagMapper;
import com.chairking.poom.hashTag.model.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagDao dao;
    @Autowired
    private TagMapper mapper;

    @Override
    public int insertTag(String tag) {
        return dao.insertTag(mapper,tag);
    }

    @Override
    public List getMyTagData(String loginId) {
        return dao.getMyTagData(mapper,loginId);
    }

    @Override
    public int deleteMyTag(String tagName) {
        return dao.deleteMyTag(mapper,tagName);
    }

    @Override
    public List<Map<String, String>> searchTag(String keyword) {
        return dao.searchTag(mapper,keyword);
    }
}
