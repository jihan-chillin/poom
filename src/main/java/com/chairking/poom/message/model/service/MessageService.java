package com.chairking.poom.message.model.service;

import com.chairking.poom.member.model.vo.Member;

import java.util.List;
import java.util.Map;

public interface MessageService {

    List<Map<String,Object>> searchReceiver();

    List<Map<String,Object>> getMessage(String condition);

    List<Map<String,Object>> messageContent(String msgNo);

    int deleteMessage(String msgNo);
}
