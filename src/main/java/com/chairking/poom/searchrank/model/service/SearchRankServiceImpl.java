package com.chairking.poom.searchrank.model.service;

import com.chairking.poom.searchrank.mapper.SearchRankMapper;
import com.chairking.poom.searchrank.model.dao.SearchRankDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchRankServiceImpl implements SearchRankService{
    @Autowired
    private SearchRankDao dao;

    @Autowired
    private SearchRankMapper mapper;

    @Override
    public List<Map> getSearchRank() {
        return dao.getSearchRank(mapper);
    }
}
