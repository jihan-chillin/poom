package com.chairking.poom.chatroom.mapper;

import com.chairking.poom.chatroom.model.vo.ChatMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChattingMapper {
//    수정해야댐. member id
    @Select("select * from chat where member_id='test'")
    public Map<String,List> getMyChatList();

    @Select("select * from CHATMEMBER where CHAT_NO=#{chatNo}")
    public List<Map> enteredMem(String chatNo);

    @Select("select * from chatmessage where chat_no= #{chatNo} and message_date between sysdate-#{ref} and sysdate")
    public List<Map> messageContent(String chatNo,int ref);

//  채팅방 번호 수정해야함.
    @Insert("insert into chatmessage values (seq_chatmessage.nextval,#{messageContent},sysdate,#{memberId},'5')")
    public int saveMessage(ChatMessage chatMessage);

    @Select("select * from chat")
    public List<Map<String,Object>> getChatList();
}
