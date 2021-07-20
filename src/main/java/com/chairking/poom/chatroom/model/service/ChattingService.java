package com.chairking.poom.chatroom.model.service;

import java.util.List;
import java.util.Map;

public interface ChattingService {
    Map<String, List> getMyChatList();
    List<Map> enteredMem(String chatNo);
    List<Map> messageContent(String chatNo,int ref);
}
