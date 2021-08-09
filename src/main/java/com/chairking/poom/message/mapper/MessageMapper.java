package com.chairking.poom.message.mapper;

import com.chairking.poom.common.Pagination;
import com.chairking.poom.member.model.vo.Member;
import com.chairking.poom.message.model.vo.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {

    @Select("SELECT * FROM MEMBER")
    public List<Map<String,Object>> searchReceiver();

    String query = "SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM(SELECT MSG_NO, MEMBER_ID, RECV_MEMBER, MSG_DATE, MSG_TYPE, SUBSTR(MSG_CONTENT, 0, 15) || '...' AS MSG_CONTENT, "
            + "READ_CHECK FROM MESSAGE "
            + "WHERE 1=1  ${condition} ";
    @Select(query)
    public List<Map<String,Object>> getMessage(String condition);


    @Delete("DELETE FROM MESSAGE WHERE MSG_NO = #{msgNo}")
    int deleteMessage(String msgNo);

    //휴지통 옮기기
    @Update("UPDATE MESSAGE SET MSG_TYPE = CASE WHEN MSG_TYPE = 1 THEN 3 WHEN MSG_TYPE = 2 THEN 4 END WHERE MSG_NO = #{msgNo}")
    int moveBlock(String msgNo);

    //읽음처리
    @Update("UPDATE MESSAGE SET READ_CHECK=sysdate WHERE MSG_NO = #{msgNo} OR MSG_NO = #{msgNo} - 1 ")
    int setMsgRead(String msgNo);


    //발송취소
    @Delete("DELETE FROM MESSAGE WHERE MSG_NO = #{msgNo} OR MSG_NO = #{msgNo} + 1 ")
    int cancelMsg(String msgNo);

    //메세지 팝업창
    @Select("SELECT * FROM MESSAGE WHERE MSG_NO = #{msgNo}")
    List<Map<String, Object>> messageContent(String msgNo);

    @Select("SELECT * FROM MEMBER ${condition}")
    List<Map<String, Object>> searchReceiverCondition(String condition);

    //message 보내기
    @Insert("INSERT INTO MESSAGE VALUES (MSG_NO.NEXTVAL, '${memberId}', '${recvMember}', sysdate, '${type}', '${msgContent}',null)")
    int sendMsg(Message msgNo);

    @Select("SELECT * FROM MESSAGE WHERE MSG_TYPE = 1")
    List<Map<String, Object>> getsendMessage(String msgNo);

    @Delete("DELETE FROM MESSAGE WHERE MSG_TYPE = 3 OR MSG_TYPE = 4")
    int emptyBlock();

    @Delete("DELETE FROM MESSAGE WHERE MSG_NO = #{msgNo}")
    int selectBlock(String msgNo);

    @Select("SELECT COUNT (*) FROM MESSAGE WHERE 1=1 ${condition}")
    int messageCount(String condition);

    @Select("select * from (select MSG_NO from MESSAGE order by to_number(MSG_NO) desc) where ROWNUM=1")
    String getRecentMessageNo();
}
