package com.chairking.poom.chatroom.mapper;

import com.chairking.poom.chatroom.model.vo.ChatMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChattingMapper {
    @Select("select cm.chat_no from CHATMEMBER cm join chat c on cm.CHAT_NO = c.CHAT_NO where cm.member_id=#{memberId} and c.DEL_STATUS ='0'")
    public List<String> getMyChatroomNum(String memberId);

    @Select("select * from chat where chat_no=#{chatNo} and DEL_STATUS ='0'")
    public List<Map> getMyChatList(String chatNo);

    @Select("select * from CHATMEMBER where CHAT_NO=#{chatNo}")
    public List<Map> enteredMem(String chatNo);

    @Select("select * from chatmessage where chat_no= #{chatNo} and message_date between sysdate-#{ref} and sysdate")
    public List<Map> messageContent(String chatNo,int ref);

    @Insert("insert into chatmessage values (seq_chatmessage.nextval,#{messageContent},sysdate,#{memberId},#{chatNo})")
    public void saveMessage(ChatMessage chatMessage);

    @Select("select * from( select ROWNUM as rnum, a.* from( select * from CHAT where DEL_STATUS ='0') a) where rnum between #{cPage} and #{numPerPage}")
    public List<Map<String,Object>> getChatList(int cPage, int numPerPage);

    @Select("select * from( select ROWNUM as rnum, a.* from( select * from CHAT where DEL_STATUS ='0' and CHAT_TYPE=#{chatType}) a) where rnum between #{cPage} and #{numPerPage}")
    public List<Map<String,Object>> getChatListSort(int cPage, int numPerPage,String chatType);

    @Select("select * from chat where chat_no = #{chatNo} and del_status = '0'")
    public Map getChatroomData(String chatNo);

    @Insert("insert into chat values(seq_chatno.nextval,#{memberId},#{title},#{content},#{memCount},#{condition},'0',to_date(#{date},'YYYY-MM-DD'),#{category},default,default)")
    public void insertChatroomData(Map<String,Object> data);

    @Select("select a.CHAT_NO from( select CHAT_NO from chat where DEL_STATUS = '0' order by ROWNUM desc) a where ROWNUM = 1")
    public String getChatNo();

    @Insert("insert into CHATMEMBER values(#{id},#{chatNo})")
    public int enterChatRoom(String id, String chatNo);

    @Delete("delete from chatmember where member_id=#{memberId} and chat_no=#{chatNo}")
    public void quitChatroom(String chatNo,String memberId);

    @Delete("delete from chat where chat_no=#{chatNo}")
    public void deleteChatroom(String chatNo);

    @Delete("delete from chatmessage where chat_no=#{chatNo}")
    public void deleteChatContent(String chatNo);

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

    @Select("select chatTypeChange(#{chatNo}) from dual")
    public int chatTypeChange(String chatNo);

}
