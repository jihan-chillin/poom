package com.chairking.poom.message.model.service;

import com.chairking.poom.message.model.dao.MessageDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao dao;

    @Autowired
    private SqlSessionTemplate session;
}
