package com.chairking.poom.chatroom.model.dao;

import com.chairking.poom.chatroom.mapper.ChattingMapper;
import com.chairking.poom.chatroom.model.vo.ChatMessage;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class ChattingDaoImpl implements ChattingDao{

    @Override
    public List<Map> getMyChatList(ChattingMapper cm) {
        return cm.getMyChatList();
    }

    @Override
    public List<Map> enteredMem(ChattingMapper cm, String chatNo) {
        return cm.enteredMem(chatNo);
    }

    @Override
    public List<Map> messageContent(ChattingMapper cm, String chatNo,int ref) {
        return cm.messageContent(chatNo,ref);
    }

    @Override
    public int saveMessage(ChattingMapper cm,ChatMessage chatMessage) {
        return cm.saveMessage(chatMessage);
    }

    @Override
    public List<Map<String,Object>> getChatList(ChattingMapper cm) {
        return cm.getChatList();
    }

    @Override
    public Map getChatroomData(ChattingMapper cm, String chatNo) {
        return cm.getChatroomData(chatNo);
    }

    @Override
    public int insertChatroomData(ChattingMapper cm, Map<String, Object> data) {
        return cm.insertChatroomData(data);
    }

    @Override
    public String getChatNo(ChattingMapper cm) {
        return cm.getChatNo();
    }

    @Override
    public int enterChatRoom(ChattingMapper cm, String id, String chatNo) {
        return cm.enterChatRoom(id,chatNo);
    }


}
