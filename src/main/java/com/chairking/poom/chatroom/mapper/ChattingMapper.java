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

    @Select("select * from chat where chat_no = #{chatNo}")
    public Map getChatroomData(String chatNo);

    @Insert("insert into chat values(seq_chatno.nextval,#{memberId},#{title},#{content},#{memCount},#{condition},'0',to_date(#{date},'YYYY-MM-DD'),#{category})")
    public int insertChatroomData(Map<String,Object> data);

    @Select("select a.CHAT_NO from( select CHAT_NO from chat order by GROUP_DATE ) a where ROWNUM = 1")
    public String getChatNo();

    @Insert("insert into CHATMEMBER values(#{id},#{chatNo})")
    public int enterChatRoom(String id, String chatNo);
}
