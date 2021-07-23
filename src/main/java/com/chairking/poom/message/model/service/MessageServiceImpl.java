package com.chairking.poom.message.model.service;

import com.chairking.poom.admin.mapper.AdminMapper;
import com.chairking.poom.member.model.vo.Member;
import com.chairking.poom.message.mapper.MessageMapper;
import com.chairking.poom.message.model.dao.MessageDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper mapper;

    @Autowired
    private MessageDao dao;

    @Autowired
    private SqlSessionTemplate session;

    @Override
    public List<Map<String,Object>> searchReceiver(){

        return dao.searchReceiver(mapper);
    };


    @Override
    public List<Map<String,Object>> receiveMessage(){
        return dao.receiveMessage(mapper);
    }

    @Override
    public List<Map<String,Object>> sendMessage(){
        return dao.receiveMessage(mapper);
    }
}
