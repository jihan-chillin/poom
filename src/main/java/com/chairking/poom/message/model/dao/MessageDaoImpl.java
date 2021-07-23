package com.chairking.poom.message.model.dao;

import com.chairking.poom.message.mapper.MessageMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MessageDaoImpl implements MessageDao{

    @Override
    public List<Map<String, Object>> searchReceiver(MessageMapper mapper){

        return mapper.searchReceiver();
    }

    @Override
    public List<Map<String, Object>> receivedMessage(MessageMapper mapper){

        return mapper.receivedMessage();
    }

}
