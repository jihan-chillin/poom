package com.chairking.poom.chatroom.model.service;

import com.chairking.poom.chatroom.model.vo.ChatMessage;

import java.util.List;
import java.util.Map;

public interface ChattingService {
    List<String> getMyChatroomNum(String memberId);
    List<String> getInterestedChatNo(String memberId);
    List<Map> getMyChatList(String chatNo);
    List<Map> enteredMem(String chatNo);
    List<Map> messageContent(String chatNo,int ref);
    void saveMessage(ChatMessage chatMessage);
    List<Map<String,Object>> getChatList(int cPage, int numPerPage);
    List<Map<String,Object>> getChatListSort(int cPage, int numPerPage,String chatType);
    Map getChatroomData(String chatNo);
    void insertChatroomData(Map<String,Object> data);
    String getChatNo();
    int enterChatRoom(String id,String chatNo);
    void quitChatroom(String chatNo,String memberId);
    void deleteChatroom(String chatNo);
    void deleteChatContent(String chatNo);
    int checkEnterChatroom(String id,String chatNo);
    int checkAlreadyInterested(String chatNo, String memberId);
    int checkAlreadyBlame(String chatNo, String memberId);
    int likeChatroom(String chatNo,String memberId);
    int unlikeChatroom(String chatNo,String memberId);
    int blameChatroom(String chatNo,String memberId);
    int chatTypeChange(String chatNo);

}
