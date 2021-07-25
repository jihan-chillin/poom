package com.chairking.poom.chatroom.mapper;

import com.chairking.poom.chatroom.model.vo.ChatMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChattingMapper {
//    수정해야댐. member
    @Select("select chat_no from CHATMEMBER where member_id=#{memberId}")
    public List<String> getMyChatroomNum(String memberId);

    @Select("select * from chat where chat_no=#{chatNo}")
    public List<Map> getMyChatList(String chatNo);

    @Select("select * from CHATMEMBER where CHAT_NO=#{chatNo}")
    public List<Map> enteredMem(String chatNo);

    @Select("select * from chatmessage where chat_no= #{chatNo} and message_date between sysdate-#{ref} and sysdate")
    public List<Map> messageContent(String chatNo,int ref);

//  채팅방 번호 수정해야함.
    @Insert("insert into chatmessage values (seq_chatmessage.nextval,#{messageContent},sysdate,#{memberId},#{chatNo})")
    public int saveMessage(ChatMessage chatMessage);

    @Select("select * from chat")
    public List<Map<String,Object>> getChatList();

    @Select("select * from chat where chat_no = #{chatNo}")
    public Map getChatroomData(String chatNo);

    @Insert("insert into chat values(seq_chatno.nextval,#{memberId},#{title},#{content},#{memCount},#{condition},'0',to_date(#{date},'YYYY-MM-DD'),#{category},default)")
    public int insertChatroomData(Map<String,Object> data);

    @Select("select a.CHAT_NO from( select CHAT_NO from chat order by ROWNUM desc) a where ROWNUM = 1")
    public String getChatNo();

    @Insert("insert into CHATMEMBER values(#{id},#{chatNo})")
    public int enterChatRoom(String id, String chatNo);

    @Select("select count(*) from CHATMEMBER where CHAT_NO=#{chatNo} and MEMBER_ID=#{memberId}")
    public int checkEnterChatroom(String memberId,String chatNo);

    @Select("select count(*) from LIKECHATROOM where CHAT_NO=#{chatNo} and MEMBER_ID=#{memberId}")
    public int checkAlreadyInterested(String chatNo, String memberId);
    @Select("select count(*) from chat_blame where CH_TARGET_CHAT=#{chatNo} and CH_AIM_ID=#{memberId}")
    public int checkAlreadyBlame(String chatNo, String memberId);

    @Insert("insert into LIKECHATROOM values(#{memberId},#{chatNo})")
    public int likeChatroom(String chatNo,String memberId);

    @Insert("insert into chat_blame values(seq_chat_blameno.nextval,sysdate,#{memberId},#{chatNo})")
    public int blameChatroom(String chatNo,String memberId);
}
