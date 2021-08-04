package com.chairking.poom.popuprank.model.dao;

import com.chairking.poom.popuprank.mapper.PopupRankMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PopupRankDaoImpl implements PopupRankDao{
    @Override
    public List<Map> memberData(PopupRankMapper mapper) {
        return mapper.memberData();
    }
}
