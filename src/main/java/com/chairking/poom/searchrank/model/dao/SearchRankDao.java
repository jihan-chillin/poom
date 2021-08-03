package com.chairking.poom.searchrank.model.dao;

import com.chairking.poom.searchrank.mapper.SearchRankMapper;

import java.util.List;
import java.util.Map;

public interface SearchRankDao {
    List<Map> getSearchRank(SearchRankMapper mapper);
}
