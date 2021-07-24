package com.chairking.poom.message.model.dao;

import com.chairking.poom.message.mapper.MessageMapper;

import java.util.List;
import java.util.Map;

public interface MessageDao {

    List<Map<String, Object>> searchReceiver(MessageMapper mapper);

    List<Map<String, Object>> receiveMessage(MessageMapper mapper);

    List<Map<String, Object>> messageContent(MessageMapper mapper, String msgNo);
}
