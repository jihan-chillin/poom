package com.chairking.poom.searchrank.model.dao;

import com.chairking.poom.searchrank.mapper.SearchMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SearchRankDaoImpl implements SearchRankDao {
    @Override
    public List<Map> getSearchRank(SearchMapper mapper) {
        return mapper.getSearchRank();
    }
}
