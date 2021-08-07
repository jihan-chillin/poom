package com.chairking.poom.search.model.service;

import com.chairking.poom.common.Pagination;
import com.chairking.poom.search.mapper.SearchMapper;
import com.chairking.poom.search.model.dao.SearchDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service

public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper mapper;

    @Autowired
    private SearchDao dao;

    @Autowired
    private SqlSessionTemplate session;

    @Override
    public List<Map<String,Object>> searchList(String where, Pagination pagination){

        return dao.searchList(mapper,where, pagination);
    }

    @Override
    public int searchCount(String where) {
        return dao.searchCount(mapper, where);
    }

    ;

}
