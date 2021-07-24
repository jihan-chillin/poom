package com.chairking.poom.chatroom.model.dao;

import com.chairking.poom.chatroom.mapper.ChattingMapper;
import com.chairking.poom.chatroom.model.vo.ChatMessage;

import java.util.List;
import java.util.Map;

public interface ChattingDao {
    List<String> getMyChatroomNum(ChattingMapper cm,String memberId);
    List<Map> getMyChatList(ChattingMapper cm,String chatNo);
    List<Map> enteredMem(ChattingMapper cm,String chatNo);
    List<Map> messageContent(ChattingMapper cm,String chatNo,int ref);
    int saveMessage(ChattingMapper cm,ChatMessage chatMessage);
    List<Map<String,Object>> getChatList(ChattingMapper cm);
    Map getChatroomData(ChattingMapper cm,String chatNo);
    int insertChatroomData(ChattingMapper m, Map<String,Object> data);
    String getChatNo(ChattingMapper cm);
    int enterChatRoom(ChattingMapper cm,String id,String chatNo);
    int checkEnterChatroom(ChattingMapper cm,String id,String chatNo);
    int checkAlreadyChatroom(ChattingMapper cm,String chatNo, String memberId, String ref,String refId,String refNo);
    int likeChatroom(ChattingMapper cm,String chatNo,String memberId);
    int blameChatroom(ChattingMapper cm,String chatNo,String memberId);
}
