package com.chairking.poom.search.model.dao;

//import com.chairking.poom.common.Pagination;
import com.chairking.poom.common.Pagination;
import com.chairking.poom.search.mapper.SearchMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SearchDaoImpl implements SearchDao {

    @Override
    public List<Map<String, Object>> searchList(SearchMapper mapper, String where, Pagination pagination){

        return mapper.searchList(where, pagination);
    }

    @Override
    public int searchCount(SearchMapper mapper, String where) {
        return mapper.searchCount(where);
    }
}
