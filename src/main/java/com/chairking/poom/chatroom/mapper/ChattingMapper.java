package com.chairking.poom.chatroom.mapper;

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

    @Select("select * from chatmessage where chat_no=#{chatNo}")
    public List<Map> messageContent(String chatNo);

}
