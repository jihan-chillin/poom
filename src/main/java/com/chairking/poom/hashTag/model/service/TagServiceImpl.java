package com.chairking.poom.hashTag.model.service;

import com.chairking.poom.hashTag.mapper.TagMapper;
import com.chairking.poom.hashTag.model.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagDao dao;
    @Autowired
    private TagMapper mapper;

    @Override
    @Transactional
    public int insertTag(String tag) {
        return dao.insertTag(mapper,tag);
    }

    @Override
    public List getMyTagData(String loginId) {
        return dao.getMyTagData(mapper,loginId);
    }

    @Override
    public int addTag(String keyword) {
        return dao.addTag(mapper,keyword);
    }

    @Override
    public int insertMemberTag(String loginId, String keyword) {
        return dao.insertMemberTag(mapper,loginId,keyword);
    }

    @Override
    public int deleteTag(String tagName) {
        return dao.deleteTag(mapper,tagName);
    }

    @Override
    public List<Map<String, String>> searchTag(String keyword) {
        return dao.searchTag(mapper,keyword);
    }

    @Override
    public String getBoardNo() {
        return dao.getBoardNo(mapper);
    }

    @Override
    public int insertBoardTag(String boardNo, String keyword) {
        return dao.insertBoardTag(mapper,boardNo,keyword);
    }
}
