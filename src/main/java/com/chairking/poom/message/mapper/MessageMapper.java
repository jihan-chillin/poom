package com.chairking.poom.message.mapper;

import com.chairking.poom.member.model.vo.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {

    @Select("SELECT * FROM MEMBER")
    public List<Map<String,Object>> searchReceiver();


    @Select("SELECT MSG_NO, MEMBER_ID, RECV_MEMBER, MSG_DATE, MSG_TYPE, SUBSTR(MSG_CONTENT, 0, 15) || '...' AS MSG_CONTENT, nvl(READ_CHECK, TO_DATE('0001/01/01', 'yyyy/mm/dd')) AS READ_CHECK FROM MESSAGE WHERE 1=1  ${condition}")
    public List<Map<String,Object>> getMessage(String condition);

    @Select("SELECT * FROM MESSAGE WHERE MSG_NO = #{msgNo}")
    List<Map<String, Object>> messageContent(String msgNo);

    @Delete("DELETE FROM MESSAGE WHERE MSG_NO = #{msgNo}")
    int deleteMessage(String msgNo);

    @Update("UPDATE MESSAGE SET MSG_TYPE=3 WHERE MSG_NO = #{msgNo}")
    int moveBlock(String msgNo);

    @Update("UPDATE MESSAGE SET READ_CHECK=sysdate WHERE MSG_NO = #{msgNo}")
    int setMsgRead(String msgNo);

    @Delete("DELETE FROM MESSAGE WHERE MSG_NO = #{msgNo}")
    int cancelMsg(String msgNo);

    @Select("SELECT * FROM MEMBER ${condition}")
    List<Map<String, Object>> searchReceiverCondition(String condition);
}
