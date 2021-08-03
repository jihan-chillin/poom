package com.chairking.poom.popuprank.model.service;

import com.chairking.poom.popuprank.mapper.PopupRankMapper;
import com.chairking.poom.popuprank.model.dao.PopupRankDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PopupRankServiceImpl implements PopupRankService{
    @Autowired
    private PopupRankDao dao;
    @Autowired
    private PopupRankMapper mapper;

    @Override
    public List<Map> memberData() {
        return dao.memberData(mapper);
    }
}
