package com.chairking.poom.search.model.dao;


import com.chairking.poom.common.Pagination;
import com.chairking.poom.search.mapper.SearchMapper;

import java.util.List;
import java.util.Map;

public interface SearchDao {


    List<Map<String, Object>> searchList(SearchMapper mapper, String where, Pagination pagination);

    int searchCount(SearchMapper mapper, String where);
}
