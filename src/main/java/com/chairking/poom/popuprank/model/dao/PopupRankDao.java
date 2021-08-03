package com.chairking.poom.popuprank.model.dao;

import com.chairking.poom.popuprank.mapper.PopupRankMapper;

import java.util.List;
import java.util.Map;

public interface PopupRankDao {
    List<Map> memberData(PopupRankMapper mapper);
}
