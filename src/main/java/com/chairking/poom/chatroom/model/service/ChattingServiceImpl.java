package com.chairking.poom.chatroom.model.service;

import com.chairking.poom.chatroom.model.dao.ChattingDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChattingServiceImpl implements ChattingService{
    @Autowired
    private ChattingDao dao;
    @Autowired
    private SqlSessionTemplate session;

}
