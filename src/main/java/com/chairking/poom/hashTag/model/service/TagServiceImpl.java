package com.chairking.poom.hashTag.model.service;

import com.chairking.poom.hashTag.mapper.TagMapper;
import com.chairking.poom.hashTag.model.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagDao dao;
    @Autowired
    private TagMapper mapper;

    @Override
    public int insertTag(Map tag) {
        return dao.insertTag(mapper,tag);
    }
}
