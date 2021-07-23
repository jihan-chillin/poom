package com.chairking.poom.message.model.service;

import com.chairking.poom.member.model.vo.Member;

import java.util.List;
import java.util.Map;

public interface MessageService {

    List<Map<String,Object>> searchReceiver();

    List<Map<String,Object>> receiveMessage();

    List<Map<String,Object>> sendMessage();
}
