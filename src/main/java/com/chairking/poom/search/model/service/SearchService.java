package com.chairking.poom.search.model.service;

import com.chairking.poom.common.Pagination;

import java.util.List;
import java.util.Map;

public interface SearchService {

    List<Map<String,Object>> searchList(String where, Pagination pagination);

    int searchCount();
}
