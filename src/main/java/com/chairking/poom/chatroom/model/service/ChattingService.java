package com.chairking.poom.chatroom.model.service;

import com.chairking.poom.chatroom.model.vo.ChatMessage;

import java.util.List;
import java.util.Map;

public interface ChattingService {
    List<Map> getMyChatList();
    List<Map> enteredMem(String chatNo);
    List<Map> messageContent(String chatNo,int ref);
    int saveMessage(ChatMessage chatMessage);
    List<Map<String,Object>> getChatList();
    Map getChatroomData(String chatNo);
    int insertChatroomData(Map<String,Object> data);
    String getChatNo();
    int enterChatRoom(String id,String chatNo);
    int checkEnterChatroom(String id,String chatNo);
}
