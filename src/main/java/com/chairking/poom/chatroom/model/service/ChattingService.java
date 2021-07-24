package com.chairking.poom.chatroom.model.service;

import com.chairking.poom.chatroom.model.vo.ChatMessage;

import java.util.List;
import java.util.Map;

public interface ChattingService {
    List<String> getMyChatroomNum(String memberId);
    List<Map> getMyChatList(String chatNo);
    int enteredMem(String chatNo);
    List<Map> messageContent(String chatNo,int ref);
    int saveMessage(ChatMessage chatMessage);
    List<Map<String,Object>> getChatList();
    Map getChatroomData(String chatNo);
    int insertChatroomData(Map<String,Object> data);
    String getChatNo();
    int enterChatRoom(String id,String chatNo);
    int checkEnterChatroom(String id,String chatNo);
    int checkAlreadyChatroom(String chatNo, String memberId, String ref,String refId,String refNo);
    int likeChatroom(String chatNo,String memberId);
    int blameChatroom(String chatNo,String memberId);

}
