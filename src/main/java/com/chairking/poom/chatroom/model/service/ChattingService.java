package com.chairking.poom.chatroom.model.service;

import com.chairking.poom.chatroom.model.vo.ChatMessage;

import java.util.List;
import java.util.Map;

public interface ChattingService {
    Map<String, List> getMyChatList();
    List<Map> enteredMem(String chatNo);
    List<Map> messageContent(String chatNo,int ref);
    int saveMessage(ChatMessage chatMessage);
}
