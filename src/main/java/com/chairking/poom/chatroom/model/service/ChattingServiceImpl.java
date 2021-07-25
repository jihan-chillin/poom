package com.chairking.poom.chatroom.model.service;

import com.chairking.poom.chatroom.mapper.ChattingMapper;
import com.chairking.poom.chatroom.model.dao.ChattingDao;
import com.chairking.poom.chatroom.model.vo.ChatMessage;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChattingServiceImpl implements ChattingService{
    @Autowired
    private ChattingDao dao;
    @Autowired
    private ChattingMapper cm;

    @Override
    public List<String> getMyChatroomNum(String memberId) {
        return dao.getMyChatroomNum(cm,memberId);
    }

    @Override
    public List<Map> getMyChatList(String chatNo) {
        return dao.getMyChatList(cm,chatNo);
    }

    @Override
    public List<Map> enteredMem(String chatNo) {
        return dao.enteredMem(cm,chatNo);
    }

    @Override
    public List<Map> messageContent(String chatNo,int ref) {
        return dao.messageContent(cm,chatNo,ref);
    }

    @Override
    public int saveMessage(ChatMessage chatMessage) {
        return dao.saveMessage(cm,chatMessage);
    }

    @Override
    public List<Map<String,Object>> getChatList() {
        return dao.getChatList(cm);
    }

    @Override
    public Map getChatroomData(String chatNo) {
        return dao.getChatroomData(cm,chatNo);
    }

    @Override
    public int insertChatroomData(Map<String, Object> data) {
        return dao.insertChatroomData(cm,data);
    }

    @Override
    public String getChatNo() {
        return dao.getChatNo(cm);
    }

    @Override
    public int enterChatRoom(String id, String chatNo) {
        return dao.enterChatRoom(cm,id,chatNo);
    }

    @Override
    public int checkEnterChatroom(String id, String chatNo) {
        return dao.checkEnterChatroom(cm,id,chatNo);
    }

    @Override
    public int checkAlreadyInterested(String chatNo, String memberId) {
        return dao.checkAlreadyInterested(cm,chatNo,memberId);
    }

    @Override
    public int checkAlreadyBlame(String chatNo, String memberId) {
        return dao.checkAlreadyBlame(cm,chatNo,memberId);
    }


    @Override
    public int likeChatroom(String chatNo, String memberId) {
        return dao.likeChatroom(cm,chatNo,memberId);
    }

    @Override
    public int blameChatroom(String chatNo, String memberId) {
        return dao.blameChatroom(cm,chatNo,memberId);
    }
}
