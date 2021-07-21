package com.chairking.poom.chatroom.model.dao;

import com.chairking.poom.chatroom.mapper.ChattingMapper;
import com.chairking.poom.chatroom.model.vo.ChatMessage;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface ChattingDao {
    Map<String, List> getMyChatList(ChattingMapper cm);
    List<Map> enteredMem(ChattingMapper cm,String chatNo);
    List<Map> messageContent(ChattingMapper cm,String chatNo,int ref);
    int saveMessage(ChattingMapper cm,ChatMessage chatMessage);
    List<Map<String,Object>> getChatList(ChattingMapper cm);
    Map getChatroomData(ChattingMapper cm,String chatNo);
}
