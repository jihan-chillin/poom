package com.chairking.poom.chatroom.model.dao;

import com.chairking.poom.chatroom.mapper.ChattingMapper;
import com.chairking.poom.chatroom.model.vo.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ChattingDaoImpl implements ChattingDao{

    @Override
    public List<String> getMyChatroomNum(ChattingMapper cm, String memberId) {
        return cm.getMyChatroomNum(memberId);
    }

    @Override
    public List<Map> getMyChatList(ChattingMapper cm,String  chatNo) {
        return cm.getMyChatList(chatNo);
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
    public void saveMessage(ChattingMapper cm,ChatMessage chatMessage) {
        cm.saveMessage(chatMessage);
    }

    // 페이징 처리
    @Override
    public List<Map<String,Object>> getChatList(ChattingMapper cm,int cPage, int numPerPage) {
        return cm.getChatList((cPage-1)*numPerPage+1,numPerPage*cPage);
    }

    @Override
    public List<Map<String, Object>> getChatListSort(ChattingMapper cm, int cPage, int numPerPage, String chatType) {
        return cm.getChatListSort((cPage-1)*numPerPage+1,numPerPage*cPage,chatType);
    }

    @Override
    public Map getChatroomData(ChattingMapper cm, String chatNo) {
        return cm.getChatroomData(chatNo);
    }

    @Override
    public void insertChatroomData(ChattingMapper cm, Map<String, Object> data) {
        cm.insertChatroomData(data);
    }

    @Override
    public String getChatNo(ChattingMapper cm) {
        return cm.getChatNo();
    }

    @Override
    public int enterChatRoom(ChattingMapper cm, String id, String chatNo) {
        return cm.enterChatRoom(id,chatNo);
    }

    @Override
    public void quitChatroom(ChattingMapper cm, String chatNo, String memberId) {
        cm.quitChatroom(chatNo,memberId);
    }

    @Override
    public void deleteChatroom(ChattingMapper cm, String chatNo) {
        cm.deleteChatroom(chatNo);
    }

    @Override
    public void deleteChatContent(ChattingMapper cm, String chatNo) {
        cm.deleteChatContent(chatNo);
    }

    @Override
    public int checkEnterChatroom(ChattingMapper cm, String id, String chatNo) {
        return cm.checkEnterChatroom(id,chatNo);
    }

    @Override
    public int checkAlreadyInterested(ChattingMapper cm, String chatNo, String id) {
        return cm.checkAlreadyInterested(chatNo, id);
    }

    @Override
    public int checkAlreadyBlame(ChattingMapper cm, String chatNo, String id) {
        return cm.checkAlreadyBlame(chatNo, id);
    }


    @Override
    public int likeChatroom(ChattingMapper cm, String chatNo, String memberId) {
        return cm.likeChatroom(chatNo,memberId);
    }

    @Override
    public int blameChatroom(ChattingMapper cm, String chatNo, String memberId) {
        return cm.blameChatroom(chatNo,memberId);
    }

    @Override
    public int chatTypeChange(ChattingMapper cm, String chatNo) {
        return cm.chatTypeChange(chatNo);
    }


}
