package com.chairking.poom.chatroom.model.service;

import com.chairking.poom.chatroom.mapper.ChattingMapper;
import com.chairking.poom.chatroom.model.dao.ChattingDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChattingServiceImpl implements ChattingService{
    @Autowired
    private ChattingDao dao;
    @Autowired
    private ChattingMapper cm;

    @Override
    public Map<String, List> getMyChatList() {
        return dao.getMyChatList(cm);
    }
}
